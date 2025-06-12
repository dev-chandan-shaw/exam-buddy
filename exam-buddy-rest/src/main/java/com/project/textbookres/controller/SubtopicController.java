package com.project.textbookres.controller;

import com.project.textbookres.dto.SubtopicCreateRequest;
import com.project.textbookres.model.Subject;
import com.project.textbookres.model.Subtopic;
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
@RequestMapping("/subtopic")
public class SubtopicController {
    @Autowired
    private SubtopicRepository subtopicRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TopicRepository topicRepository;

    @PostMapping
    public ResponseEntity<Subtopic> addSubtopic(@RequestParam String topicName) {
       Subtopic subtopic = new Subtopic();
       subtopic.setName(topicName);
       subtopicRepository.save(subtopic);
       return ResponseEntity.ok(subtopic);
    }

    @GetMapping
    private List<Subtopic> getAllSubtopics() {
        return subtopicRepository.findAll();
    }

    @GetMapping("/topic")
    private List<Subtopic> getSubtopicsByTopic(@RequestParam Long topicId) {
        return subtopicRepository.findByTopicId(topicId);
    }


}
