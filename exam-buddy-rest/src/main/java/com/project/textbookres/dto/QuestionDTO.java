package com.project.textbookres.dto;

import java.util.List;

public class QuestionDTO {
    private Long id;
    private String description;
    private List<OptionDTO> options;

    public QuestionDTO(Long id, String description, List<OptionDTO> options) {
        this.id = id;
        this.description = description;
        this.options = options;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDTO> options) {
        this.options = options;
    }
}
