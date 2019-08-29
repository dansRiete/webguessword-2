package com.oleksii.kuzko.service;

import com.oleksii.kuzko.dao.QuestionDao;
import com.oleksii.kuzko.model.Question;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionDao questionDao;

    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public Question getRandom() {
        return null;
    }

    public List<Question> getAll() {
        return questionDao.getAll();
    }

    public List<Question> getAllMysql() {
        return questionDao.getAllMysql();
    }

    @Transactional
    public boolean copy() {
        List<Question> allQuestions = getAllMysql();
        for (Question currentQuestion : allQuestions) {
            questionDao.createPhrase(currentQuestion);
        }
        return true;
    }
}
