package com.project.textbookres.service;

import com.project.textbookres.controller.TestAttemptController;
import com.project.textbookres.dto.QuestionStatus;
import com.project.textbookres.dto.test_solution.TestSolution;
import com.project.textbookres.dto.test_solution.TestSolutionQuestion;
import com.project.textbookres.dto.test_solution.TestSolutionQuestionStatus;
import com.project.textbookres.dto.test_solution.TestSolutionSection;
import com.project.textbookres.model.TestAttempt;
import com.project.textbookres.model.TestAttemptQuestionState;
import com.project.textbookres.model.TestAttemptSection;
import com.project.textbookres.respository.TestAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestSolutionService {
    @Autowired
    private TestAttemptRepository testAttemptRepository;

    public TestSolution getTestSolution(long testAttemptId) {
        TestAttempt testAttempt = testAttemptRepository.findById(testAttemptId).get();
        List<TestSolutionSection> testSolutionSections = new ArrayList<>();

        for (TestAttemptSection testAttemptSection : testAttempt.getTestSections()) {
            List<TestSolutionQuestion> testSolutionQuestions = new ArrayList<>();
            for (TestAttemptQuestionState questionState : testAttemptSection.getQuestions()) {
                TestSolutionQuestion testSolutionQuestion = new TestSolutionQuestion();
                testSolutionQuestion.setQuestion(questionState.getQuestion());
                testSolutionQuestion.setSelectedOptionId(questionState.getSelectedOptionId());
                testSolutionQuestion.setTimeTakenSeconds(questionState.getTimeTakenSeconds());
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
        testSolution.setTestName(testAttempt.getTest().getTitle());
        testSolution.setSections(testSolutionSections);

        return testSolution;
    }

    private TestSolutionQuestionStatus setTestSolutionStatus(TestAttemptQuestionState questionState) {
        QuestionStatus status = questionState.getStatus();
        boolean isAttempted = status == QuestionStatus.ANSWERED || status == QuestionStatus.MARKED_AND_ANSWERED;
        boolean isCorrect = questionState.getQuestion().getOptions().stream().anyMatch(option -> option.getId() == questionState.getSelectedOptionId() && option.isCorrect());
        return isAttempted ? isCorrect ? TestSolutionQuestionStatus.CORRECT : TestSolutionQuestionStatus.INCORRECT : TestSolutionQuestionStatus.NOT_ATTEMPTED;
    }
}
