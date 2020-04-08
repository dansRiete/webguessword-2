package com.guessword.service;

import com.guessword.dao.QuestionDao;
import com.guessword.dao.QuestionRepository;
import com.guessword.dto.QuestionDto;
import com.guessword.dto.mapper.QuestionMapper;
import com.guessword.domain.entity.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionService.class);

    private final QuestionDao questionDao;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public QuestionService(
        final QuestionDao questionDao,
        final QuestionRepository questionRepository,
        final QuestionMapper questionMapper
    ) {
        this.questionDao = questionDao;
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    public QuestionDto getRandom() {
        return null;
    }

    public List<QuestionDto> findAllMysql() {
        long startTime = System.currentTimeMillis();
        final List<Question> allMysqlQuestions = questionDao.getAllMysql();
        LOGGER.info(
            String.format("Fetched all from MySQL. Time: %d ms. Found: %d",
                System.currentTimeMillis() - startTime, allMysqlQuestions.size()));
        return questionMapper.toDto(allMysqlQuestions);
    }

    @Transactional
    public boolean copyAll() {
        long startTime = System.currentTimeMillis();
        List<Question> questions = questionDao.getAllMysql();
        questionRepository.saveAll(questions);
        LOGGER.info(
            String.format("Copied all question from MySQL. Time: %d ms. Size: %d",
                System.currentTimeMillis() - startTime, questions.size()));
        return true;
    }
}
