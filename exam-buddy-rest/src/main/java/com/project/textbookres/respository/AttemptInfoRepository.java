package com.project.textbookres.respository;

import com.project.textbookres.model.AttemptInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttemptInfoRepository extends JpaRepository<AttemptInfo, Long> {

    List<AttemptInfo> findByTestIdAndUserId(long testId, long userId);
}
