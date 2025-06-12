package com.project.textbookres.controller;

import com.project.textbookres.dto.CreateTestRequest;
import com.project.textbookres.dto.TestDto;
import com.project.textbookres.model.*;
import com.project.textbookres.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @Autowired
    private TestAttemptRepository testAttemptRepository;

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/unpublished")
    public List<Test> getTestsByPublishStatus(Principal principal) {
        long userId = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found")).getId();
        return testRepository.findByUserIdAndPublished(userId, false);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getTestByExam(@RequestParam Long examId, Principal principal) {
        List<Test> tests = testRepository.findByExamId(examId);
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        List<TestDto> testDtos = tests.stream().map(test -> {
            List<TestAttempt> testAttempt = testAttemptRepository.findByTestIdAndUserId(test.getId(), user.getId());
            boolean isAttempted = !testAttempt.isEmpty();
            boolean isPaused = !testAttempt.isEmpty() && testAttempt.get(testAttempt.size() - 1).isPaused();
            int totalQuestions = test.getTestSections().stream().mapToInt(testSection -> testSection.getQuestions().size()).sum();
            return new TestDto(
                    test.getId(),
                    test.getTitle(),
                    totalQuestions,
                    test.getTotalMarks(),
                    test.getTotalTime(),
                    testAttempt.isEmpty() ? null : testAttempt.get(testAttempt.size() - 1).getStartTime(),
                    test.isPyqTest(),
                    test.isSectionTest(),
                    isAttempted,
                    isPaused
            );
        }).toList();

        return ResponseEntity.ok(testDtos);
    }

    @GetMapping
    public ResponseEntity<?> getAllTest() {
        List<Test> testList = testRepository.findAll();
        return ResponseEntity.ok(testList);
    }

    @GetMapping("/{testId}")
    public ResponseEntity<?> getTestById(@PathVariable long testId) {
        Optional<Test> test = testRepository.findById(testId);
        if (test.isEmpty()) return ResponseEntity.badRequest().body("Test not found with id " + testId);
        return ResponseEntity.ok(test.get());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTestById(@RequestParam long testId) {
        testRepository.deleteById(testId);
        return ResponseEntity.ok("Deleted Successfully");
    }

}
