package com.guessword.controller;

import com.guessword.domain.dto.QuestionDto;
import com.guessword.domain.dto.mapper.QuestionMapper;
import com.guessword.service.QuestionService;
import com.guessword.dao.QuestionRepository;
import com.guessword.domain.entity.Question;
import com.guessword.service.QuestionService.QuestionLikelihood;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
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

    @GetMapping(value = "/getNext")
    public ResponseEntity<QuestionDto> getRandom() {
        QuestionDto foundQuestion = questionService.getRandom();
        if (foundQuestion == null) {
            return ResponseEntity.status(500).build();
        } else {
            return ResponseEntity.ok(foundQuestion);
        }
    }

    @PostMapping(value = "/right")
    public ResponseEntity<QuestionDto> rightAnswer(@RequestBody QuestionDto answeredQuestionDto) {
        return ResponseEntity.ok(questionService.rightAnswer(answeredQuestionDto, false));
    }

    @PostMapping(value = "/wrong")
    public ResponseEntity<QuestionDto> wrongAnswer(@RequestBody QuestionDto answeredQuestionDto) {
        return ResponseEntity.ok(questionService.wrongAnswer(answeredQuestionDto, false));
    }

    @PostMapping(value = "/rollbackRight")
    public ResponseEntity<QuestionDto> rollbackRight() {
        return ResponseEntity.ok(questionService.rollbackLast(true));
    }

    @PostMapping(value = "/rollbackWrong")
    public ResponseEntity<QuestionDto> rollbackWrong() {
        return ResponseEntity.ok(questionService.rollbackLast(false));
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

    @GetMapping(value = "/recalculate/{power}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<Integer, QuestionLikelihood>>> recalculate(@PathVariable Double power) {
        return ResponseEntity.ok(questionService.recalculateRandomizer(power));
    }

}