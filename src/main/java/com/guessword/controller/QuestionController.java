package com.guessword.controller;

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

    public QuestionController(
        QuestionService questionService,
        QuestionRepository questionRepository
    ) {
        this.questionService = questionService;
        this.questionRepository = questionRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Question> getRandom() {
        Question foundQuestion = questionService.getRandom();
        if (foundQuestion == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundQuestion);
        }
    }

    @GetMapping
    public ResponseEntity<List<Question>> findAll() {
        return ResponseEntity.of(Optional.of(questionRepository.findAll()));
    }

    @GetMapping(value = "/allMysql", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Question>> getAllMysql() {
        final List<Question> allMysqlQuestions = questionService.findAllMysql();
        return ResponseEntity.ok(allMysqlQuestions);
    }

    @GetMapping(value = "/copy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> copy() {
        final List<Question> allMysqlQuestions = questionService.findAllMysql();
        return ResponseEntity.ok(questionService.copyAll(allMysqlQuestions));
    }

}