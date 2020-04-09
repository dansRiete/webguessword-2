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

/**
 * Contains all the logic for randomizing questions, ensuring the calculation of the probability of asking questions
 * depending on external conditions such as: the number of questions already studied and not studied, the category of
 * currently selected questions and others ...
 * @author Oleksii Kuzko. 9 April 2020.
 */
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
        LOGGER.info("Fetched all from MySQL. Time: {} ms. Found: {}",
            System.currentTimeMillis() - startTime, allMysqlQuestions.size());
        return questionMapper.toDto(allMysqlQuestions);
    }

    @Transactional
    public boolean copyAll() {
        long startTime = System.currentTimeMillis();
        List<Question> questions = questionDao.getAllMysql();
        questionRepository.saveAll(questions);
        LOGGER.info("Copied all question from MySQL. Time: {} ms. Size: {}",
                System.currentTimeMillis() - startTime, questions.size());
        return true;
    }
}
