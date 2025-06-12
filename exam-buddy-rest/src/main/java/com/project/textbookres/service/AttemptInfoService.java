package com.project.textbookres.service;

import com.project.textbookres.model.TestAttempt;
import com.project.textbookres.model.AttemptInfo;
import com.project.textbookres.respository.AttemptInfoRepository;
import com.project.textbookres.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class AttemptInfoService {
    @Autowired
    private AttemptInfoRepository attemptInfoRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveAttemptInfo(TestAttempt testAttempt, long userId) {
        AttemptInfo attemptInfo = new AttemptInfo();
        attemptInfo.setAttemptId(testAttempt.getId());
        attemptInfo.setAttemptedAt(testAttempt.getStartTime());
        attemptInfo.setTestId(testAttempt.getTest().getId());
        attemptInfo.setUserId(userId);
        attemptInfoRepository.save(attemptInfo);
    }

    public List<AttemptInfo> getAttemptInfoByTestId(long testId, long userId) {
        return attemptInfoRepository.findByTestIdAndUserId(testId, userId);
    }
}
