package com.oleksii.kuzko.service;

import com.oleksii.kuzko.dao.QuestionDao;
import com.oleksii.kuzko.dao.QuestionRepository;
import com.oleksii.kuzko.entity.Question;
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
