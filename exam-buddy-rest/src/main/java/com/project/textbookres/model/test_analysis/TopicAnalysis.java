package com.project.textbookres.model.test_analysis;

import com.project.textbookres.model.Topic;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class TopicAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Topic topic;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<TopicQuestionStats> topicQuestionStats;
}
