package com.project.textbookres.controller;

import com.project.textbookres.dto.CreateQuestionRequest;
import com.project.textbookres.dto.OptionDTO;
import com.project.textbookres.model.*;
import com.project.textbookres.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private TestSectionRepository testSectionRepository;

    @Autowired
    private SubtopicRepository subtopicRepository;

    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    @GetMapping
    public ResponseEntity<?> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    public ResponseEntity<?> addQuestion(@RequestBody CreateQuestionRequest reqBody) {
        long examId = reqBody.getExamId();
        long testSectionId = reqBody.getTestSectionId();
        long subtopicId = reqBody.getSubtopicId();
        Optional<Exam> optionalExam = examRepository.findById(examId);
        if (optionalExam.isEmpty()) {
            return ResponseEntity.badRequest().body("NO exam is present for given id: " + examId);
        }

        Optional<TestSection> optionalTestSection = testSectionRepository.findById(testSectionId);
        if (optionalTestSection.isEmpty()) {
            return ResponseEntity.badRequest().body("Test section is not present for given id: " + testSectionId);
        }

        Optional<Subtopic> optionalSubtopic = subtopicRepository.findById(subtopicId);
        if (optionalSubtopic.isEmpty()) {
            return ResponseEntity.badRequest().body("Subtopic is not present for given id: " + subtopicId);
        }

        //creating the options
        List<QuestionOption> questionOptions = new ArrayList<>();
        for (OptionDTO option : reqBody.getOptions()) {
            QuestionOption questionOption = new QuestionOption();
            questionOption.setCorrect(option.isCorrect());
            questionOption.setDescription(option.getDescription());
            questionOptions.add(questionOption);
        }

        //creating the question object
        Question question = new Question();
        question.setPassage(reqBody.getPassage());
        question.setSubtopic(optionalSubtopic.get());
        question.setExam(optionalExam.get());
        question.setOptions(questionOptions);
        question.setDescription(reqBody.getDescription());
        question.setSolution(reqBody.getSolution());

        //add question to the associated section
        optionalTestSection.get().getQuestions().add(question);

        //saving all to the db
        // save the question before saving options
        questionRepository.save(question);
        questionOptionRepository.saveAll(questionOptions);
        testSectionRepository.save(optionalTestSection.get());

        return ResponseEntity.ok(question);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllQuestions() {
        questionRepository.deleteAll();
        return ResponseEntity.ok("Deleted all questions");
    }

}

