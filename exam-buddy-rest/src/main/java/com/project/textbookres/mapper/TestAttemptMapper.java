package com.project.textbookres.mapper;

import com.project.textbookres.dto.*;
import com.project.textbookres.model.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TestAttemptMapper {

    public ActiveTestResponse toActiveTestResponse(TestAttempt attempt) {
        ActiveTestResponse dto = new ActiveTestResponse();
        dto.setId(attempt.getId());
        dto.setTestId(attempt.getTest().getId());
        dto.setStartTime(attempt.getStartTime());
        dto.setCompleted(attempt.isCompleted());
        dto.setTestSections(
                attempt.getTestSections().stream()
                        .map(this::mapSectionToDto)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    private ActiveTestSectionResponse mapSectionToDto(TestAttemptSection section) {
        ActiveTestSectionResponse dto = new ActiveTestSectionResponse();
        dto.setId(section.getId());
        dto.setSubject(section.getSubject());

        dto.setTimeTakenSeconds(section.getTimeTakenSeconds());
        dto.setQuestions(
                section.getQuestions().stream()
                        .map(this::mapQuestionToDto)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    private ActiveTestQuestionStateResponse mapQuestionToDto(TestAttemptQuestionState q) {
        ActiveTestQuestionStateResponse dto = new ActiveTestQuestionStateResponse();
        dto.setId(q.getId());
        dto.setSelectedOptionId(q.getSelectedOptionId());
        dto.setTimeTakenSeconds(q.getTimeTakenSeconds());
        dto.setStatus(q.getStatus());
        dto.setQuestion(mapToQuestionDto(q.getQuestion()));
        return dto;
    }

    private ActiveTestQuestionResponseDto mapToQuestionDto(Question question) {
        ActiveTestQuestionResponseDto dto = new ActiveTestQuestionResponseDto();
        dto.setId(question.getId());
        dto.setDescription(question.getDescription());
        dto.setPassage(question.getPassage());
        dto.setOptions(
                question.getOptions().stream()
                        .map(this::mapToOptionDto)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    private OptionResponseDto mapToOptionDto(QuestionOption option) {
        OptionResponseDto dto = new OptionResponseDto();
        dto.setId(option.getId());
        dto.setDescription(option.getDescription());
        return dto;
    }


}
