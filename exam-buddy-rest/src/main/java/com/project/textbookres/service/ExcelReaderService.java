package com.project.textbookres.service;

import com.project.textbookres.model.Exam;
import com.project.textbookres.model.Question;
import com.project.textbookres.model.Subject;
import com.project.textbookres.model.Test;
import com.project.textbookres.respository.ExamRepository;
import com.project.textbookres.respository.QuestionRepository;
import com.project.textbookres.respository.TestRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelReaderService {
    private static final Logger logger = LoggerFactory.getLogger(ExcelReaderService.class);

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    @Autowired
    public ExcelReaderService(TestRepository testRepository,
                              QuestionRepository questionRepository,
                              ExamRepository examRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.examRepository = examRepository;
    }

    @Transactional
    public List<Question> readExcelFile(MultipartFile file, Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));

        // Create full test
        Test fullTest = createTest(exam, false);
        fullTest.setSubjects(new ArrayList<>(exam.getSubjects()));
        fullTest = testRepository.save(fullTest);
        fullTest.setTitle("Full Test - " + fullTest.getId());
        testRepository.save(fullTest);

        List<Question> fullTestQuestions = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            logger.info("Processing workbook with {} sheets", workbook.getNumberOfSheets());
            processSheets(workbook, exam, fullTest, fullTestQuestions);

            // Save full test questions in batch
            questionRepository.saveAll(fullTestQuestions);

            // Update full test stats
            fullTest.setTotalMarks(fullTestQuestions.size());
            fullTest.setTotalTime(fullTestQuestions.size());
            testRepository.save(fullTest);

            return fullTestQuestions;
        } catch (IOException e) {
            logger.error("Failed to read Excel file", e);
            throw new RuntimeException("Failed to read Excel file: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Failed to process Excel data", e);
            throw new RuntimeException("Failed to process Excel data: " + e.getMessage(), e);
        }
    }

    private void processSheets(Workbook workbook, Exam exam, Test fullTest, List<Question> fullTestQuestions) {
        int sheetCount = workbook.getNumberOfSheets();
        List<Subject> subjects = exam.getSubjects();

        for (int sheetNo = 0; sheetNo < Math.min(sheetCount, subjects.size()); sheetNo++) {
            Subject subject = subjects.get(sheetNo);
            Sheet sheet = workbook.getSheetAt(sheetNo);

            Test sectionTest = createSectionTest(exam, subject);
            List<Question> sectionQuestions = processSheet(sheet, subject, sectionTest, fullTest, fullTestQuestions);

            // Save section questions in batch
            questionRepository.saveAll(sectionQuestions);

            // Update section test stats
            sectionTest.setTotalMarks(sectionQuestions.size());
            sectionTest.setTotalTime(sectionQuestions.size());
            testRepository.save(sectionTest);
        }
    }

    private Test createTest(Exam exam, boolean isSectionTest) {
        Test test = new Test();
        test.setExam(exam);
        test.setSectionTest(isSectionTest);
        return test;
    }

    private Test createSectionTest(Exam exam, Subject subject) {
        Test sectionTest = createTest(exam, true);
        sectionTest.setSubjects(new ArrayList<>(List.of(subject)));
        sectionTest = testRepository.save(sectionTest);
        sectionTest.setTitle(subject.getName() + "-" + sectionTest.getId());
        return testRepository.save(sectionTest);
    }

    private List<Question> processSheet(Sheet sheet, Subject subject, Test sectionTest,
                                        Test fullTest, List<Question> fullTestQuestions) {
        List<Question> sectionQuestions = new ArrayList<>();

        // Skip header row
        DataFormatter formatter = new DataFormatter();
        int rowCount = 0;

        for (Row row : sheet) {
            if (rowCount++ == 0) continue; // Skip header

            if (isEmptyRow(row)) break;

            Question sectionQuestion = createQuestion(row, subject, sectionTest, formatter);
            sectionQuestions.add(sectionQuestion);

            // Clone question for full test
            Question fullTestQuestion = cloneQuestion(sectionQuestion, subject, fullTest);
            fullTestQuestions.add(fullTestQuestion);
        }

        return sectionQuestions;
    }

    private boolean isEmptyRow(Row row) {
        return row.getCell(0) == null ||
                row.getCell(0).getCellType() == CellType.BLANK ||
                row.getCell(0).getStringCellValue().trim().isEmpty();
    }

    private Question createQuestion(Row row, Subject subject, Test test, DataFormatter formatter) {
        Question question = new Question();
        question.setDescription(getCellValueSafely(row, 0, formatter));
        question.setOptionA(getCellValueSafely(row, 1, formatter));
        question.setOptionB(getCellValueSafely(row, 2, formatter));
        question.setOptionC(getCellValueSafely(row, 3, formatter));
        question.setOptionD(getCellValueSafely(row, 4, formatter));

        Cell correctOptionCell = row.getCell(5);
        if (correctOptionCell != null) {
            if (correctOptionCell.getCellType() == CellType.NUMERIC) {
                question.setCorrectOption((int) correctOptionCell.getNumericCellValue());
            } else {
                // Try to parse string as integer if cell is not numeric
                try {
                    question.setCorrectOption(Integer.parseInt(formatter.formatCellValue(correctOptionCell).trim()));
                } catch (NumberFormatException e) {
                    logger.warn("Invalid correct option format at row {}, defaulting to 0", row.getRowNum());
                    question.setCorrectOption(0);
                }
            }
        } else {
            question.setCorrectOption(0);
        }

        question.setSubject(subject);
        question.setTest(test);
        return question;
    }

    private String getCellValueSafely(Row row, int cellIndex, DataFormatter formatter) {
        Cell cell = row.getCell(cellIndex);
        return cell == null ? "" : formatter.formatCellValue(cell).trim();
    }

    private Question cloneQuestion(Question source, Subject subject, Test test) {
        Question clone = new Question();
        clone.setDescription(source.getDescription());
        clone.setOptionA(source.getOptionA());
        clone.setOptionB(source.getOptionB());
        clone.setOptionC(source.getOptionC());
        clone.setOptionD(source.getOptionD());
        clone.setCorrectOption(source.getCorrectOption());
        clone.setSubject(subject);
        clone.setTest(test);
        return clone;
    }
}