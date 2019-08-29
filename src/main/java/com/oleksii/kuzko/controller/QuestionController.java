package com.oleksii.kuzko.controller;

import com.oleksii.kuzko.model.Question;
import com.oleksii.kuzko.service.QuestionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
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

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Question>> getAll() {
        return ResponseEntity.ok(questionService.getAll());
    }

    @GetMapping(value = "/allMysql", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Question>> getAllMysql() {
        return ResponseEntity.ok(questionService.getAllMysql());
    }

    @GetMapping(value = "/copy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity copy() {
        return ResponseEntity.ok(questionService.copy());
    }

}