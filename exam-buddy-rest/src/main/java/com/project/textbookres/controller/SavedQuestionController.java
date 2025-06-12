package com.project.textbookres.controller;

import com.project.textbookres.dto.SaveQuestionRequest;
import com.project.textbookres.model.Question;
import com.project.textbookres.model.SavedQuestion;
import com.project.textbookres.model.Test;
import com.project.textbookres.model.User;
import com.project.textbookres.respository.QuestionRepository;
import com.project.textbookres.respository.SavedQuestionRepository;
import com.project.textbookres.respository.TestRepository;
import com.project.textbookres.respository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/saved-question")
public class SavedQuestionController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SavedQuestionRepository savedQuestionRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping
    public ResponseEntity<?> saveQuestion(@RequestBody SaveQuestionRequest reqBody, Principal principal) {
        long testId = reqBody.getTestId();
        long questionId = reqBody.getQuestionId();
        Optional<Test> testOptional = testRepository.findById(testId);
        if (testOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Test does not exist.");
        }
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if (questionOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Question does not exist.");
        }

        User user = userRepository.findByEmail(principal.getName()).orElseThrow(RuntimeException::new);

        SavedQuestion savedQuestion = new SavedQuestion();
        savedQuestion.setTest(testOptional.get());
        savedQuestion.setQuestion(questionOptional.get());
        savedQuestion.setUser(user);
        savedQuestionRepository.save(savedQuestion);
        return ResponseEntity.ok("Question saved.");
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> deleteSavedQuestion(@RequestParam long questionId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(RuntimeException::new);
        savedQuestionRepository.deleteByQuestionIdAndUserId(questionId, user.getId());
        return ResponseEntity.ok("Question deleted.");
    }

    @GetMapping
    public ResponseEntity<?> getAllSavedQuestions(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(RuntimeException::new);
        return ResponseEntity.ok(savedQuestionRepository.findAllByUser(user));
    }
}
