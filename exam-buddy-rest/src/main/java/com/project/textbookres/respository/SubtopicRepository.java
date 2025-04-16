package com.project.textbookres.respository;

import com.project.textbookres.model.Subtopic;
import com.project.textbookres.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubtopicRepository extends JpaRepository<Subtopic, Long> {
    List<Subtopic> findByTopicId(Long topicId);
}
