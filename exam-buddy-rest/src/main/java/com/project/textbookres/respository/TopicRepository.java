package com.project.textbookres.respository;

import com.project.textbookres.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findBySubjectId(Long subjectId);
}
