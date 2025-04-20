package com.project.textbookres.service;

import com.project.textbookres.model.*;
import com.project.textbookres.respository.TestAttemptQuestionStateRepository;
import com.project.textbookres.respository.TestAttemptSectionRepository;
import com.project.textbookres.respository.TestAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActiveTestService {

    @Autowired
    private TestAttemptRepository testAttemptRepository;

    @Autowired
    private TestAttemptSectionRepository testAttemptSectionRepository;

    @Autowired
    private TestAttemptQuestionStateRepository testAttemptQuestionStateRepository;


    public TestAttempt createTestAttempt(Test test, User user) {


        List<TestAttemptSection> testAttemptSections = new ArrayList<>();

        for (TestSection testSection : test.getTestSections()) {
            List<TestAttemptQuestionState> testAttemptQuestionStates = new ArrayList<>();
            for (Question question : testSection.getQuestions()) {
                TestAttemptQuestionState testAttemptQuestionState = new TestAttemptQuestionState();
                testAttemptQuestionState.setQuestion(question);
                testAttemptQuestionStates.add(testAttemptQuestionState);
            }

            TestAttemptSection testAttemptSection = new TestAttemptSection();
            testAttemptSection.setSubject(testSection.getSubject());
            testAttemptSections.add(testAttemptSection);
            testAttemptSection.setTotalMarks(testSection.getMarks());
            testAttemptSection.setTotalTime(testSection.getTime());
            testAttemptSection.getQuestions().addAll(testAttemptQuestionStates);
            testAttemptQuestionStateRepository.saveAll(testAttemptQuestionStates);
        }

        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setTest(test);
        testAttempt.setUser(user);
        testAttempt.getTestSections().addAll(testAttemptSections);
        testAttemptSectionRepository.saveAll(testAttemptSections);
        testAttemptRepository.save(testAttempt);

        return testAttempt;
    }
}
