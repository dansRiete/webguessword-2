package com.guessword.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.guessword.domain.BaseQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto implements BaseQuestion {
    private Integer id;
    private Double probabilityFactor;
    private Double probabilityMultiplier;
    private LocalDateTime created;
    private LocalDateTime lastAccessed;
    private String tag;
    private List<WordDto> words;
}
