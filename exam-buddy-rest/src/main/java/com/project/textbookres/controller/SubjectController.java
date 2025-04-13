package com.project.textbookres.controller;
import com.project.textbookres.model.Subject;
import com.project.textbookres.respository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/subjects")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping
    private List<Subject> getAllSubject() {

        return subjectRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> addSubject(@RequestBody Subject reqBody) {
        Subject subject = new Subject();
        subject.setName(reqBody.getName());
        subjectRepository.save(subject);
        return ResponseEntity.ok(subject);
    }
}
