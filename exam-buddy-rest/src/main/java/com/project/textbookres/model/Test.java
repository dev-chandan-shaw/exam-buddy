package com.project.textbookres.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private boolean pyqTest;

    private boolean sectionTest;

    private boolean publish;

    private int totalTime;

    private int totalMarks;


    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ManyToMany
    @JoinTable(
            name = "test_subjects",
            joinColumns = @JoinColumn(name = "test_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;


//    @ManyToMany
//    @JoinTable(
//            name = "test_question",
//            joinColumns = @JoinColumn(name = "test_id"),
//            inverseJoinColumns = @JoinColumn(name = "question_id")
//    )
//    private List<Question> questions;
//
//    @ManyToMany
//    @JoinTable(
//            name = "test_subject",
//            joinColumns = @JoinColumn(name = "test_id"),
//            inverseJoinColumns = @JoinColumn(name = "subject_id")
//    )
//    private List<Subject> subjects;

    // Constructors
    public Test() {}

    public Test(String title) {
        this.title = title;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public boolean isPyqTest() {
        return pyqTest;
    }

    public void setPyqTest(boolean pyqTest) {
        this.pyqTest = pyqTest;
    }

    public boolean isSectionTest() {
        return sectionTest;
    }

    public void setSectionTest(boolean sectionTest) {
        this.sectionTest = sectionTest;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    //    public List<Question> getQuestions() {
//        return questions;
//    }
//
//    public void setQuestions(List<Question> questions) {
//        this.questions = questions;
//    }


}
