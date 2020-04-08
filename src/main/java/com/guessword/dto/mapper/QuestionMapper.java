package com.guessword.dto.mapper;

import java.util.List;

import com.guessword.dto.QuestionDto;
import com.guessword.domain.entity.Question;
import org.mapstruct.Mapper;

@Mapper(uses = WordMapper.class)
public interface QuestionMapper {
    QuestionDto toDto(Question question);
    List<QuestionDto> toDto(List<Question> questions);
}
