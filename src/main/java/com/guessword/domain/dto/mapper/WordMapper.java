package com.guessword.domain.dto.mapper;

import com.guessword.domain.dto.WordDto;
import com.guessword.domain.entity.Word;
import org.mapstruct.Mapper;

@Mapper
public interface WordMapper {
    WordDto toDto(Word question);
    Word toEntity(WordDto questionDto);
}
