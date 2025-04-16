package com.project.textbookres.controller;

import com.project.textbookres.dto.CreateExamRequest;
import com.project.textbookres.dto.CreateExamSection;
import com.project.textbookres.dto.ExamRequest;
import com.project.textbookres.model.Exam;
import com.project.textbookres.model.ExamSection;
import com.project.textbookres.model.Subject;
import com.project.textbookres.respository.ExamRepository;
import com.project.textbookres.respository.ExamSectionRepository;
import com.project.textbookres.respository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//
@RestController
@RequestMapping("api/exams")
public class ExamController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamSectionRepository examSectionRepository;

    @GetMapping
    public ResponseEntity<?> getAllExam() {
        List<Exam> examList = examRepository.findAll();
        return ResponseEntity.ok(examList);
    }

    @PostMapping
    public ResponseEntity<?> createExam(@RequestBody CreateExamRequest reqBody) {
        List<ExamSection> sections = new ArrayList<>();
        for (CreateExamSection section : reqBody.getSections()) {
            ExamSection examSection = new ExamSection();
            examSection.setTime(section.getTime());
            examSection.setMarks(section.getMarks());
            Optional<Subject> optionalSubject = subjectRepository.findById(section.getSubjectId());
            if (optionalSubject.isEmpty()) {
                return ResponseEntity.badRequest().body("Subject not found with id " + section.getSubjectId());
            }
            Subject subject = optionalSubject.get();
            examSection.setSubject(subject);
            sections.add(examSection);
        }
        Exam exam = new Exam();
        exam.setTitle(reqBody.getTitle());
        exam.setMarksPerQuestion(reqBody.getMarksPerQuestion());
        exam.setNegativeMark(reqBody.getNegativeMark());
        exam.getSections().addAll(sections);
        examSectionRepository.saveAll(sections);
        examRepository.save(exam);
        return ResponseEntity.ok(exam);
    }

}
