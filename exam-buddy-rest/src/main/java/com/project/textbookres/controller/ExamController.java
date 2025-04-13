package com.project.textbookres.controller;

import com.project.textbookres.dto.ExamRequest;
import com.project.textbookres.model.Exam;
import com.project.textbookres.model.Subject;
import com.project.textbookres.respository.ExamRepository;
import com.project.textbookres.respository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/exams")
public class ExamController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ExamRepository examRepository;

    @GetMapping
    public ResponseEntity<?> getAllExam() {
        List<Exam> examList = examRepository.findAll();
        return ResponseEntity.ok(examList);
    }

    @PostMapping
    public ResponseEntity<?> createExam(@RequestBody ExamRequest requestBody) {

        String title = requestBody.getTitle();
        Exam exam = new Exam();
        exam.setMarksPerQuestion(requestBody.getMarksPerQuestion());
        exam.setNegativeMark(requestBody.getNegativeMark());
        exam.setTitle(requestBody.getTitle());
        exam = examRepository.save(exam);

        List<Subject> subjectList = new ArrayList<>();
        for (Subject sub : requestBody.getSubjects()) {
            Subject subject = new Subject(sub.getName(), sub.getTotalMarks(), sub.getTotalTime(), exam);
            subject = subjectRepository.save(subject);
            subjectList.add(subject);
        }

        exam.setSubjects(subjectList);
        exam = examRepository.save(exam);
        return ResponseEntity.ok(exam);
    }
}
