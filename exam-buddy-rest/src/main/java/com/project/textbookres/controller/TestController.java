package com.project.textbookres.controller;

import com.project.textbookres.dto.TestDTO;
import com.project.textbookres.model.Exam;
import com.project.textbookres.model.Question;
import com.project.textbookres.model.Test;
import com.project.textbookres.respository.ExamRepository;
import com.project.textbookres.respository.QuestionRepository;
import com.project.textbookres.respository.TestRepository;
import com.project.textbookres.service.ExcelReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/tests")
public class TestController {

    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ExcelReaderService excelReaderService;

    @GetMapping
    public ResponseEntity<?> getAllTest() {
        List<Test> testList = testRepository.findAll();
        return ResponseEntity.ok(testList);
    }

    @PostMapping
    public ResponseEntity<?> createTest(@RequestBody Test reqBody) {
        Exam exam = examRepository.findById(reqBody.getExam().getId()).get();
        Test test = new Test();
        test.setExam(exam);
        test.setPyqTest(reqBody.isPyqTest());
        test.setSectionTest(reqBody.isSectionTest());
        test.setTitle(reqBody.getTitle());
        test.setTotalMarks(reqBody.getTotalMarks());
        test.setTotalTime(reqBody.getTotalTime());
        test = testRepository.save(test);
        return ResponseEntity.ok(test);
    }


    @GetMapping("/exam/{examId}")
    public ResponseEntity<?> getTestByExam(@PathVariable Long examId) {
        List<Test> testList = testRepository.findByExamId(examId);
        return ResponseEntity.ok(testList);
    }

    @GetMapping("/test/{testId}")
    public ResponseEntity<?> getTestById(@PathVariable Long testId) {
        Test test = testRepository.findById(testId).get();
        return ResponseEntity.ok(test);
    }

    @PostMapping("/upload")
    public List<Question> uploadExcel(@RequestParam("file") MultipartFile file, @RequestParam Long examId) {
        return excelReaderService.readExcelFile(file, examId);
    }
}
