package com.guessword.domain.dto.mapper;

import java.util.List;

import com.guessword.domain.dto.QuestionDto;
import com.guessword.domain.entity.Question;
import org.mapstruct.Mapper;

@Mapper(uses = WordMapper.class)
public interface QuestionMapper {
    QuestionDto toDto(Question question);
    Question toEntity(QuestionDto questionDto);
    List<QuestionDto> toDto(List<Question> questions);
}