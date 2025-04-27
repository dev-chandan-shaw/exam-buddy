package com.project.textbookres.service;

import com.project.textbookres.dto.QuestionStatus;
import com.project.textbookres.model.TestAttempt;
import com.project.textbookres.model.Topic;
import com.project.textbookres.model.test_analysis.*;
import com.project.textbookres.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestAnalysisService {

    @Autowired
    TestAnalysisRepository testAnalysisRepository;

    @Autowired
    SubjectStatsRepository subjectStatsRepository;

    @Autowired
    TopicAnalysisRepository topicAnalysisRepository;

    @Autowired
    TopicQuestionStatsRepository topicQuestionStatsRepository;

    @Autowired
    TestAttemptRepository testAttemptRepository;

    public void generateTestAnalysis(TestAttempt testAttempt) {
        TestAnalysis testAnalysis = new TestAnalysis();
        testAnalysis.setTestName(testAttempt.getTest().getTitle());
        testAnalysis.setTestAttemptId(testAttempt.getId());

        List<SubjectStats> subjectStats = getSubjectStats(testAttempt, testAnalysis);
        subjectStatsRepository.saveAll(subjectStats);
        testAnalysis.setSubjectStats(subjectStats);

        List<TopicAnalysis> topicAnalysis = getTopicAnalysis(testAttempt, testAnalysis);
        topicAnalysisRepository.saveAll(topicAnalysis);
        testAnalysis.setTopicsAnalysis(topicAnalysis);

        List<TestAttempt> testAttempts = testAttemptRepository.findByTestIdOrderByMarksObtainedDesc(testAttempt.getTest().getId());
        setTestAnalysisStats(testAnalysis, testAttempt, testAttempts);

        testAnalysisRepository.save(testAnalysis);
    }

    private List<SubjectStats> getSubjectStats(TestAttempt testAttempt, TestAnalysis testAnalysis) {
        return testAttempt.getTestSections()
                .stream()
                .map(testAttemptSection -> {
                    SubjectStats subjectStats = new SubjectStats();
                    subjectStats.setSubject(testAttemptSection.getSubject());
                    subjectStats.setTotalQuestions(testAttemptSection.getQuestions().size());
                    subjectStats.setTotalMarks(testAttemptSection.getTotalMarks());
                    subjectStats.setMarksObtained(testAttemptSection.getMarksObtained());
                    subjectStats.setTimeTakenSeconds(testAttemptSection.getTimeTakenSeconds());
                    subjectStats.setTotalTime(testAttemptSection.getTotalTime());
                    subjectStats.setTotalAttemptedQuestions(testAttemptSection.getTotalAttemptedQuestions());
                    subjectStats.setAccuracy(testAttemptSection.getAccuracy());
                    return subjectStats;
                })
                .toList();
    }

    private List<TopicAnalysis> getTopicAnalysis(TestAttempt testAttempt, TestAnalysis testAnalysis) {
        Map<Topic, List<TopicQuestionStats> > topicQuestionMap = new LinkedHashMap<>();

        testAttempt.getTestSections().forEach(testAttemptSection -> {
            testAttemptSection.getQuestions().forEach(testAttemptQuestionState -> {
                Topic topic = testAttemptQuestionState.getQuestion().getSubtopic().getTopic();
                TopicQuestionStats topicQuestionStats = new TopicQuestionStats();
                topicQuestionStats.setQuestionNo(testAttemptSection.getQuestions().indexOf(testAttemptQuestionState) + 1);
                topicQuestionStats.setAttempted(testAttemptQuestionState.getStatus() == QuestionStatus.ANSWERED || testAttemptQuestionState.getStatus() == QuestionStatus.MARKED_AND_ANSWERED);
                topicQuestionStats.setCorrect(testAttemptQuestionState.isCorrect());
                topicQuestionMap.computeIfAbsent(topic, k -> new ArrayList<>()).add(topicQuestionStats);
            });
        });

        List<TopicQuestionStats> allQuestionStats = topicQuestionMap
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toList());

        // Save all TopicQuestionStats at once
        topicQuestionStatsRepository.saveAll(allQuestionStats);

        // setting the topic analysis
        return topicQuestionMap.entrySet().stream().map(entry -> {
            TopicAnalysis topicAnalysis = new TopicAnalysis();
            topicAnalysis.setTopic(entry.getKey());
            topicAnalysis.setTopicQuestionStats(entry.getValue());
            return topicAnalysis;
        }).collect(Collectors.toList());
    }

    public TestAnalysis getTestAnalysis(long testAttemptId) {
        Optional<TestAnalysis> optionalTestAnalysis = testAnalysisRepository.findByTestAttemptId(testAttemptId);
        if (optionalTestAnalysis.isEmpty()) {
            throw new RuntimeException("Test analysis not found");
        }

        Optional<TestAttempt> testAttemptOptional = testAttemptRepository.findById(testAttemptId);
        if (testAttemptOptional.isEmpty()) {
            throw new RuntimeException("Test attempt not found");
        }
        TestAttempt testAttempt = testAttemptOptional.get();
        List<TestAttempt> testAttempts = testAttemptRepository.findByTestIdOrderByMarksObtainedDesc(testAttempt.getTest().getId());
        TestAnalysis testAnalysis = optionalTestAnalysis.get();
        setTestAnalysisStats(testAnalysis, testAttempt, testAttempts);
        return testAnalysis;
    }

    private void setTestAnalysisStats(TestAnalysis testAnalysis, TestAttempt testAttempt, List<TestAttempt> testAttempts) {
        int rankOfUser = testAttempts.indexOf(testAttempt) + 1;
        int rankOutOf = testAttempts.size();
        double percentile = (rankOutOf - rankOfUser + 1) * 100.0 / rankOutOf;
        testAnalysis.setRankOfUser(rankOfUser);
        testAnalysis.setRankOutOf(rankOutOf);
        testAnalysis.setPercentile(percentile);
    }

}
