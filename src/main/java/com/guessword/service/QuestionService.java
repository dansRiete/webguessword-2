package com.guessword.service;

import com.guessword.dao.QuestionDao;
import com.guessword.dao.QuestionRepository;
import com.guessword.entity.Question;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionDao questionDao;
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionDao questionDao, final QuestionRepository questionRepository) {
        this.questionDao = questionDao;
        this.questionRepository = questionRepository;
    }

    public Question getRandom() {
        return null;
    }

    public List<Question> findAllMysql() {
        return questionDao.getAllMysql();
    }

    @Transactional
    public boolean copyAll(List<Question> questions) {
        for (Question currentQuestion : questions) {
            questionRepository.saveAndFlush(currentQuestion);
        }
        return true;
    }
}
