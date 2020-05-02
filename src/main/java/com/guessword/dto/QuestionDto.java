package com.guessword.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private Integer id;
    private Double probabilityFactor;
    private Double probabilityMultiplier;
    private LocalDateTime created;
    private LocalDateTime lastAccessed;
    private String tag;
    private List<WordDto> words;
}
