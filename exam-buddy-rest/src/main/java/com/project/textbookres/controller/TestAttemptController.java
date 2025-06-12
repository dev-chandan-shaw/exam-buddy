package com.project.textbookres.controller;

import com.project.textbookres.dto.ActiveTestResponse;
import com.project.textbookres.dto.QuestionStatus;
import com.project.textbookres.dto.StartTestRequest;
import com.project.textbookres.dto.TestAttemptQuestionStateUpdateRequest;
import com.project.textbookres.model.test_analysis.TestAnalysis;
import com.project.textbookres.mapper.TestAttemptMapper;
import com.project.textbookres.model.*;
import com.project.textbookres.respository.*;
import com.project.textbookres.service.ActiveTestService;
import com.project.textbookres.service.AttemptInfoService;
import com.project.textbookres.service.TestAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/test-attempt")
public class TestAttemptController {

    @Autowired
    private TestAttemptSectionRepository testAttemptSectionRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestAttemptQuestionStateRepository testAttemptQuestionStateRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestAttemptRepository testAttemptRepository;

    @Autowired
    private ActiveTestService activeTestService;

    @Autowired
    private TestAttemptMapper testAttemptMapper;


    @Autowired
    private AttemptInfoService attemptInfoService;

    @Autowired
    private TestAnalysisService testAnalysisService;

    @Autowired
    private QuestionStatsRepository questionStatsRepository;


    @PostMapping("/start")
    public ResponseEntity<?> startTest(@RequestParam long testId, Principal principal) {

        long userId = userRepository.findByEmail(principal.getName()).orElseThrow(RuntimeException::new).getId();

        Optional<TestAttempt> optionalActiveTest = testAttemptRepository.findByUserIdAndCompleted(userId, false); // check if user has an active test
        if (optionalActiveTest.isPresent()) {
            return ResponseEntity.badRequest().body("User already has an active test");
        }

        Optional<Test> optionalTest = testRepository.findById(testId);
        if (optionalTest.isEmpty()) {
            return ResponseEntity.badRequest().body("Test not found with id " + testId);
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found with id " + userId);
        }

        Test test = optionalTest.get();
        User user = optionalUser.get();

        TestAttempt testAttempt = activeTestService.createTestAttempt(test, user);

        return ResponseEntity.ok(testAttempt);
    }

    @PutMapping("/save")
    public ResponseEntity<?> saveQuestionState(@RequestBody TestAttemptQuestionStateUpdateRequest request) {
        long testAttemptId = request.getTestAttemptId();
        Optional<TestAttempt> optionalTestAttempt = testAttemptRepository.findById(testAttemptId);
        if (optionalTestAttempt.isEmpty()) {
            return ResponseEntity.badRequest().body("Test attempt not found with id " + testAttemptId);
        }
        TestAttempt testAttempt = optionalTestAttempt.get();
        if (testAttempt.isCompleted()) {
            return ResponseEntity.badRequest().body("Test is already completed");
        }

        long testAttemptSectionId = request.getTestAttemptSectionId();
        Optional<TestAttemptSection> optionalTestAttemptSection = testAttemptSectionRepository.findById(testAttemptSectionId);
        if (optionalTestAttemptSection.isEmpty()) {
            return ResponseEntity.badRequest().body("Test attempt section not found with id " + testAttemptSectionId);
        }
        TestAttemptSection testAttemptSection = optionalTestAttemptSection.get();

        long testAttemptQuestionStateId = request.getTestAttemptQuestionStateId();
        Optional<TestAttemptQuestionState> optionalTestAttemptQuestionState = testAttemptQuestionStateRepository.findById(testAttemptQuestionStateId);
        if (optionalTestAttemptQuestionState.isEmpty()) {
            return ResponseEntity.badRequest().body("Test attempt question state not found with id " + testAttemptQuestionStateId);
        }

        TestAttemptQuestionState testAttemptQuestionState = optionalTestAttemptQuestionState.get();

        testAttemptSection.setTimeTakenSeconds(request.getSectionTimeTakenSeconds());
        testAttemptQuestionState.setStatus(request.getQuestionStatus());
        testAttemptQuestionState.setTimeTakenSeconds(request.getTimeTakenSeconds());
        testAttemptQuestionState.setSelectedOptionId(request.getSelectedOptionId());
        testAttemptSectionRepository.save(testAttemptSection);
        testAttemptQuestionStateRepository.save(testAttemptQuestionState);

        return ResponseEntity.ok(testAttemptQuestionState);
    }


    @GetMapping("/active")
    public ResponseEntity<?> getActiveTestByUserId(Principal principal) {
        long userId = userRepository.findByEmail(principal.getName()).orElseThrow(RuntimeException::new).getId();
        Optional<TestAttempt> optionalActiveTest = testAttemptRepository.findByUserIdAndCompleted(userId, false);
        if (optionalActiveTest.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User does not have an active test");
        }
        ActiveTestResponse activeTestResponse = testAttemptMapper.toActiveTestResponse(optionalActiveTest.get());
        return ResponseEntity.ok(activeTestResponse);
    }

    @GetMapping("/attempted")
    public TestAttempt getAttemptedTest(@RequestParam long testAttemptId) {
        return testAttemptRepository.findById(testAttemptId).get();
    }

    @GetMapping
    public List<TestAttempt> getAttemptedTests() {
        return testAttemptRepository.findAll();
    }

    @PutMapping("/finish")
    public ResponseEntity<?> finishTest(Principal principal) {
        long userId = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found")).getId();
        Optional<TestAttempt> optionalActiveTest = testAttemptRepository.findByUserIdAndCompleted(userId, false);
        if (optionalActiveTest.isEmpty()) {
            return ResponseEntity.badRequest().body("User does not have an active test");
        }
        TestAttempt testAttempt = optionalActiveTest.get();
        int marksPerQuestion = testAttempt.getTest().getExam().getMarksPerQuestion();
        double negativeMarking = testAttempt.getTest().getExam().getNegativeMark();
        double testTotalMarksObtained = 0.0;
        List<QuestionStats> questionStatsList = new ArrayList<>();
        for (TestAttemptSection testAttemptSection: testAttempt.getTestSections()) {
            int sectionTotalAttemptedQuestions = 0;
            int sectionTotalCorrectAnswers = 0;
            double sectionTotalMarksObtained = 0.0;
            for (TestAttemptQuestionState testAttemptQuestionState: testAttemptSection.getQuestions()) {
                QuestionStatus status = testAttemptQuestionState.getStatus();
                QuestionStats questionStats = testAttemptQuestionState.getQuestion().getQuestionStats();
                boolean isAttempted = status == QuestionStatus.ANSWERED || status == QuestionStatus.MARKED_AND_ANSWERED;
                boolean isCorrect = testAttemptQuestionState.getQuestion().getOptions().stream().anyMatch(option -> option.getId() == testAttemptQuestionState.getSelectedOptionId() && option.isCorrect());
                sectionTotalAttemptedQuestions += isAttempted ? 1 : 0;
                sectionTotalCorrectAnswers += isAttempted && isCorrect ? 1 : 0;
                if (isAttempted) {
                    sectionTotalMarksObtained += isCorrect ? marksPerQuestion : -negativeMarking;
                    questionStats.setTotalAttempts(questionStats.getTotalAttempts() + 1);
                }
                questionStats.setTotalCorrect(questionStats.getTotalCorrect() + (isCorrect ? 1 : 0));
                int avgTime = isCorrect ? (questionStats.getAvgTimeSeconds() * questionStats.getTotalAttempts() + testAttemptQuestionState.getTimeTakenSeconds()) / questionStats.getTotalAttempts() : questionStats.getAvgTimeSeconds();
                questionStats.setAvgTimeSeconds(avgTime);
                questionStatsList.add(questionStats);
            }
            testTotalMarksObtained += sectionTotalMarksObtained;
            int accuracy = sectionTotalAttemptedQuestions == 0 ? 0 : (sectionTotalCorrectAnswers * 100) / sectionTotalAttemptedQuestions;
            testAttemptSection.setAccuracy(accuracy);
            testAttemptSection.setTotalAttemptedQuestions(sectionTotalAttemptedQuestions);
            testAttemptSection.setMarksObtained(sectionTotalMarksObtained);
        }

        testAttempt.setCompleted(true);
        testAttempt.setMarksObtained(testTotalMarksObtained);
        testAttemptSectionRepository.saveAll(testAttempt.getTestSections());
        testAttemptRepository.save(testAttempt);
        attemptInfoService.saveAttemptInfo(testAttempt, userId);
        testAnalysisService.generateTestAnalysis(testAttempt);
        questionStatsRepository.saveAll(questionStatsList);
        return ResponseEntity.ok(testAttempt);
    }

    @GetMapping("/{testId}/attempted")
    public ResponseEntity<?> isTestAttempted(@PathVariable long testId, Principal principal) {
        long userId = userRepository.findByEmail(principal.getName()).orElseThrow(RuntimeException::new).getId();
        List<TestAttempt> attempts = testAttemptRepository.findByUserIdAndTestId(userId, testId);
        return ResponseEntity.ok(!attempts.isEmpty());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTestAttempt(@PathVariable long id) {
        Optional<TestAttempt> optionalTestAttempt = testAttemptRepository.findById(id);
        if (optionalTestAttempt.isEmpty()) {
            return ResponseEntity.badRequest().body("Test attempt not found with id " + id);
        }
        TestAttempt testAttempt = optionalTestAttempt.get();
        testAttemptRepository.delete(testAttempt);
        return ResponseEntity.ok("Test attempt deleted successfully");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllTestAttempts() {
        testAttemptRepository.deleteAll();
        return ResponseEntity.ok("All test attempts deleted successfully");
    }

}
