package com.project.textbookres.service;

import com.project.textbookres.controller.TestAttemptController;
import com.project.textbookres.dto.QuestionStatus;
import com.project.textbookres.dto.test_solution.TestSolution;
import com.project.textbookres.dto.test_solution.TestSolutionQuestion;
import com.project.textbookres.dto.test_solution.TestSolutionQuestionStatus;
import com.project.textbookres.dto.test_solution.TestSolutionSection;
import com.project.textbookres.model.*;
import com.project.textbookres.respository.SavedQuestionRepository;
import com.project.textbookres.respository.TestAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestSolutionService {
    @Autowired
    private TestAttemptRepository testAttemptRepository;

    @Autowired
    private SavedQuestionRepository savedQuestionRepository;

    public TestSolution getTestSolution(long testAttemptId, User user) {
        TestAttempt testAttempt = testAttemptRepository.findById(testAttemptId).get();
        List<TestSolutionSection> testSolutionSections = new ArrayList<>();
        List<SavedQuestion> savedQuestions = savedQuestionRepository.findAllByUserAndTest(user, testAttempt.getTest());

        for (TestAttemptSection testAttemptSection : testAttempt.getTestSections()) {
            List<TestSolutionQuestion> testSolutionQuestions = new ArrayList<>();
            for (TestAttemptQuestionState questionState : testAttemptSection.getQuestions()) {
                TestSolutionQuestion testSolutionQuestion = new TestSolutionQuestion();
                testSolutionQuestion.setQuestion(questionState.getQuestion());
                testSolutionQuestion.setSelectedOptionId(questionState.getSelectedOptionId());
                testSolutionQuestion.setTimeTakenSeconds(questionState.getTimeTakenSeconds());
                testSolutionQuestion.setQuestionSaved(checkIsQuestionSaved(savedQuestions, questionState));
                testSolutionQuestion.setStatus(setTestSolutionStatus(questionState));
                testSolutionQuestions.add(testSolutionQuestion);
            }
            TestSolutionSection testSolutionSection = new TestSolutionSection();
            testSolutionSection.setSubject(testAttemptSection.getSubject());
            testSolutionSection.setQuestions(testSolutionQuestions);
            testSolutionSections.add(testSolutionSection);
        }

        TestSolution testSolution = new TestSolution();
        testSolution.setTestAttemptId(testAttempt.getId());
        testSolution.setTestId(testAttempt.getTest().getId());
        testSolution.setTestName(testAttempt.getTest().getTitle());
        testSolution.setSections(testSolutionSections);

        return testSolution;
    }

    private boolean checkIsQuestionSaved(List<SavedQuestion> savedQuestions, TestAttemptQuestionState questionState) {
        return savedQuestions.stream().anyMatch(savedQuestion -> savedQuestion.getQuestion().getId() == questionState.getQuestion().getId());
    }

    private TestSolutionQuestionStatus setTestSolutionStatus(TestAttemptQuestionState questionState) {
        QuestionStatus status = questionState.getStatus();
        boolean isAttempted = status == QuestionStatus.ANSWERED || status == QuestionStatus.MARKED_AND_ANSWERED;
        boolean isCorrect = questionState.getQuestion().getOptions().stream().anyMatch(option -> option.getId() == questionState.getSelectedOptionId() && option.isCorrect());
        return isAttempted ? isCorrect ? TestSolutionQuestionStatus.CORRECT : TestSolutionQuestionStatus.INCORRECT : TestSolutionQuestionStatus.UNATTEMPTED;
    }
}
