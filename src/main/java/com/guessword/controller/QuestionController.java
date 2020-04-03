package com.guessword.controller;

import com.guessword.dto.QuestionDto;
import com.guessword.dto.mapper.QuestionMapper;
import com.guessword.service.QuestionService;
import com.guessword.dao.QuestionRepository;
import com.guessword.entity.Question;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public QuestionController(
        final QuestionService questionService,
        final QuestionRepository questionRepository,
        final QuestionMapper questionMapper
    ) {
        this.questionService = questionService;
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> getRandom() {
        QuestionDto foundQuestion = questionService.getRandom();
        if (foundQuestion == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundQuestion);
        }
    }

    @GetMapping
    public ResponseEntity<List<QuestionDto>> findAll() {
        final List<Question> allQuestions = questionRepository.findAll();
        final List<QuestionDto> allQuestionDtos = questionMapper.toDto(allQuestions);
        return ResponseEntity.of(Optional.of(allQuestionDtos));
    }

    @GetMapping(value = "/mysql", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDto>> findAllMysql() {
        final List<QuestionDto> allMysqlQuestions = questionService.findAllMysql();
        return ResponseEntity.ok(allMysqlQuestions);
    }

    @GetMapping(value = "/copyFromMysql", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> copy() {
        return ResponseEntity.ok(questionService.copyAll());
    }

}