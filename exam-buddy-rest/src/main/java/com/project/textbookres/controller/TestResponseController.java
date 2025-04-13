package com.project.textbookres.controller;

import com.project.textbookres.dto.AnswerState;
import com.project.textbookres.dto.ApiResponse;
import com.project.textbookres.model.*;
import com.project.textbookres.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("api/test-responses")
public class TestResponseController {
    @Autowired
    private TestResponseRepository testResponseRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerResponseRepository answerResponseRepository;

    @GetMapping("/{testId}/{userId}")
    public ResponseEntity<?> getAllTestResponseByTestId(@PathVariable Long userId, @PathVariable Long testId) {
        List<TestResponse> testResponseList = testResponseRepository.findByUserIdAndTestId(userId, testId);
        return ResponseEntity.ok(new ApiResponse<List<TestResponse>>(testResponseList, "Test responses."));
    }

    @PostMapping("/start")
    public ResponseEntity<?> startTest(@RequestParam Long userId, @RequestParam Long testId) {
        Optional<TestResponse> onGoingTest = testResponseRepository.findByUserIdAndTestFinished(userId, false);
        if (onGoingTest.isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<TestResponse>(null, "You have an on going test."));

        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Test test = testRepository.findById(testId).orElseThrow(RuntimeException::new);
        TestResponse testResponse = new TestResponse(test, user, new Date());
        testResponseRepository.save(testResponse);

        List<Question> questionList = questionRepository.findByTestId(testId);
        List<AnswerResponse> answerResponseList = testResponse.getAnswerResponses();

        for (Question question : questionList) {
            AnswerResponse answerResponse = new AnswerResponse(question, -1, 0, AnswerState.NOT_VISITED);
            answerResponse.setTestResponse(testResponse);
            answerResponseRepository.save(answerResponse);
            answerResponseList.add(answerResponse);
        }
        testResponse.setAnswerResponses(answerResponseList);
        testResponseRepository.save(testResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(testResponse);
    }

    @GetMapping("/on-going/{userId}")
    public ResponseEntity<?> getOnGoingTest(@PathVariable Long userId) {
        Optional<TestResponse> testResponse = testResponseRepository.findByUserIdAndTestFinished(userId, false);
        if (testResponse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("No on going test.");
        }
        Date testStartTime = testResponse.get().getStartTime();
        long timeLeft = Duration.between(Instant.ofEpochMilli(testStartTime.getTime()), Instant.now()).toMinutes();
        System.out.println("Time left: " + timeLeft);
        System.out.println("Test time: " + testResponse.get().getTest().getTotalTime());
        if (timeLeft  < testResponse.get().getTest().getTotalTime()) {
            return ResponseEntity.ok(testResponse.get());
        }
        testResponse.get().setTestFinished(true);
        testResponseRepository.save(testResponse.get());
        return ResponseEntity.ok(testResponse.get());
    }



    @PutMapping("/save/{userId}")
    public ResponseEntity<?> saveTestResponse(@RequestParam Long userId, @RequestBody TestResponse testResponseBody) {
        Optional<TestResponse> testResponse = testResponseRepository.findByUserIdAndTestFinished(userId, false);
        if (testResponse.isEmpty()) return ResponseEntity.ok(new ApiResponse<TestResponse>(null, "No on going test."));
        if (testResponse.get().isTestFinished())
            return ResponseEntity.ok(new ApiResponse<TestResponse>(null, "Test already submitted."));
        List<AnswerResponse> answerResponseList = testResponse.get().getAnswerResponses();

        for (int i=0; i<testResponseBody.getAnswerResponses().size(); i++) {
            AnswerResponse answerResponse1 = testResponseBody.getAnswerResponses().get(i);
            AnswerResponse answerResponse2 = answerResponseList.get(i);
            answerResponse2.setSelectedOption(answerResponse1.getSelectedOption());
            answerResponse2.setTimeTaken(answerResponse1.getTimeTaken());
            answerResponse2.setCurrentState(answerResponse1.getCurrentState());
            answerResponseRepository.save(answerResponse2);
        }
        return ResponseEntity.status(HttpStatus.OK).body(testResponse.get());
    }

    @PutMapping("/submit")
    public ResponseEntity<?> submitTest(@RequestParam Long userId, @RequestBody TestResponse testResponseBody) {
        Optional<TestResponse> testResponse = testResponseRepository.findByUserIdAndTestFinished(userId, false);
        if (testResponse.isEmpty()) return ResponseEntity.ok("No on going test.");
        if (testResponse.get().isTestFinished()) return ResponseEntity.ok("Test already submitted.");
        List<AnswerResponse> answerResponseList = testResponse.get().getAnswerResponses();

        for (int i=0; i<testResponseBody.getAnswerResponses().size(); i++) {
            AnswerResponse answerResponse1 = testResponseBody.getAnswerResponses().get(i);
            AnswerResponse answerResponse2 = answerResponseList.get(i);
            answerResponse2.setSelectedOption(answerResponse1.getSelectedOption());
            answerResponse2.setTimeTaken(answerResponse1.getTimeTaken());
            answerResponse2.setCurrentState(answerResponse1.getCurrentState());
            answerResponseRepository.save(answerResponse2);
        }

        testResponse.get().setTestFinished(true);
        testResponseRepository.save(testResponse.get());
        return ResponseEntity.status(HttpStatus.OK).body(testResponse.get());
    }

    @PutMapping("/auto-submit/{userId}")
    public ResponseEntity<?> autoSubmitTest(@PathVariable Long userId) {
        Optional<TestResponse> testResponse = testResponseRepository.findByUserIdAndTestFinished(userId, false);
        if (testResponse.isEmpty()) return ResponseEntity.ok(new ApiResponse<TestResponse>(null, "No on going test."));
        if (testResponse.get().isTestFinished())
            return ResponseEntity.ok(new ApiResponse<TestResponse>(null, "Test already submitted."));
        testResponse.get().setTestFinished(true);
        TestResponse saved = testResponseRepository.save(testResponse.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<TestResponse>(saved, "Test submitted."));
    }

    @PutMapping("/save-response")
    public ResponseEntity<?> saveAnswerResponse(@RequestParam Long userId,@RequestBody AnswerResponse answerResponse) {
        Optional<TestResponse> testResponse = testResponseRepository.findByUserIdAndTestFinished(userId, false);
        if (testResponse.isEmpty()) return ResponseEntity.ok(new ApiResponse<TestResponse>(null, "No on going test."));
        answerResponse.setTestResponse(testResponse.get());
        answerResponseRepository.save(answerResponse);
        return ResponseEntity.ok(answerResponse);
    }

}
