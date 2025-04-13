package com.project.textbookres.dto;

import com.project.textbookres.model.Exam;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.List;

public class TestDTO {
    private String title;

    private boolean pyqTest;

    private boolean sectionTest;

    private boolean publish;

    private Exam exam;

    public TestDTO() {

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

}