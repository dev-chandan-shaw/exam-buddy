package com.project.textbookres.controller;

import com.project.textbookres.dto.CreateTopicRequest;
import com.project.textbookres.model.Subject;
import com.project.textbookres.model.Topic;
import com.project.textbookres.respository.SubjectRepository;
import com.project.textbookres.respository.SubtopicRepository;
import com.project.textbookres.respository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/topics")
public class TopicController {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubjectRepository subjectRepository;
    @PostMapping
    private ResponseEntity<?> addTopic(@RequestBody CreateTopicRequest reqBody) {
        Optional<Subject> optionalSubject = subjectRepository.findById(reqBody.getSubjectId());
        if (optionalSubject.isEmpty()) {
            return ResponseEntity.badRequest().body("Subject not found");
        }
        Topic topic = new Topic();
        topic.setName(reqBody.getName());
        topic.setSubjectId(reqBody.getSubjectId());
        topicRepository.save(topic);
        return ResponseEntity.ok(topic);
    }

    @GetMapping
    private List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @GetMapping("/subject")
    private List<Topic> getTopicsBySubject(@RequestParam Long subjectId) {
        return topicRepository.findBySubjectId(subjectId);
    }
}
