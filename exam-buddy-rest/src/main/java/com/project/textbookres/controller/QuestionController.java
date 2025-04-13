package com.project.textbookres.controller;

import com.project.textbookres.dto.OptionDTO;
import com.project.textbookres.dto.QuestionRequest;
import com.project.textbookres.model.*;
import com.project.textbookres.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/questions")
public class QuestionController {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ExamRepository examRepository;

    @GetMapping
    public ResponseEntity<?> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    public ResponseEntity<?> addQuestion(@RequestBody QuestionRequest reqBody) {
        Test test = testRepository.findById(reqBody.getTestId()).get();
        Subject subject = subjectRepository.findById(reqBody.getSubjectId()).get();
        Question question = new Question();
        question.setDescription(reqBody.getDescription());
        question.setSubject(subject);
        question.setTest(test);
        question = questionRepository.save(question);
        List<Option> options = new ArrayList<>();
        for (Option opt : reqBody.getOptions()) {
            Option option = new Option();
            option.setText(opt.getText());
            option.setCorrect(opt.isCorrect());
            option.setQuestion(question);
            options.add(option);
        }
        optionRepository.saveAll(options);
//        question.setOptions(options);
        questionRepository.save(question);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/by-test/{testId}")
    public ResponseEntity<?> getAllQuestionByTest(@PathVariable Long testId) {
        List<Question> questionList = questionRepository.findByTestId(testId);
        return ResponseEntity.ok(questionList);
    }

}
