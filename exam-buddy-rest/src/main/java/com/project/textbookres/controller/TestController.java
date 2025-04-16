package com.project.textbookres.controller;

import com.project.textbookres.dto.CreateTestRequest;
import com.project.textbookres.dto.TestDTO;
import com.project.textbookres.model.*;
import com.project.textbookres.respository.ExamRepository;
import com.project.textbookres.respository.QuestionRepository;
import com.project.textbookres.respository.TestRepository;
import com.project.textbookres.respository.TestSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private TestSectionRepository testSectionRepository;

    @PostMapping
    private ResponseEntity<?> createTest(@RequestBody CreateTestRequest reqBody) {
        Optional<Exam>  exam = examRepository.findById(reqBody.getExamId());
        if (exam.isEmpty()) {
            return ResponseEntity.badRequest().body("Exam not found with id " + reqBody.getExamId());
        }

        int marks = 0;
        int time = 0;
        List<TestSection> testSections = new ArrayList<>();
        for (ExamSection examSection : exam.get().getSections()) {
            TestSection testSection = new TestSection();
            testSection.setSubject(examSection.getSubject());
            testSection.setMarks(examSection.getMarks());
            testSection.setTime(examSection.getTime());
            testSections.add(testSection);
            marks += examSection.getMarks();
            time += examSection.getTime();
        }

        Test test = new Test();
        test.setExam(exam.get());
        test.setTitle(reqBody.getTitle());
        test.setTotalMarks(marks);
        test.setTotalTime(time);
        test.setTestSections(testSections);
        test.setPyqTest(reqBody.isPyqTest());
        System.out.println("Is Previous year test: " + test.isPyqTest());
        System.out.println("Is Previous year test: " + reqBody.isPyqTest());
        testSectionRepository.saveAll(testSections);
        testRepository.save(test);
        return ResponseEntity.ok(test);
    }





    @GetMapping
    public ResponseEntity<?> getAllTest() {
        List<Test> testList = testRepository.findAll();
        return ResponseEntity.ok(testList);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTestById(@RequestParam long testId) {
        testRepository.deleteById(testId);
        return ResponseEntity.ok("Deleted Successfully");
        
    }



//
//    @GetMapping("/exam/{examId}")
//    public ResponseEntity<?> getTestByExam(@PathVariable Long examId) {
//        List<Test> testList = testRepository.findByExamId(examId);
//        return ResponseEntity.ok(testList);
//    }
//
//    @GetMapping("/test/{testId}")
//    public ResponseEntity<?> getTestById(@PathVariable Long testId) {
//        Test test = testRepository.findById(testId).get();
//        return ResponseEntity.ok(test);
//    }
//
//    @PostMapping("/upload")
//    public List<Question> uploadExcel(@RequestParam("file") MultipartFile file, @RequestParam Long examId) {
//        return excelReaderService.readExcelFile(file, examId);
//    }
}
