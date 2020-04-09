package com.guessword.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.guessword.dao.QuestionRepository;
import com.guessword.domain.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionEngine.class);
    private static final double CHANCE_OF_APPEARING_TRAINED_PHRASES = 1D / 35D;
    private static final double POWER = 1.25;
    private static final int MAX_RANGE = 1_000_000;
    private static final int LEARNED_QUESTION_PROB_MARGIN = 3;
    private static final double ALLOWABLE_INDEX_ERROR_FACTOR = 0.95;

    private final QuestionRepository questionRepository;
    private final Map<Integer, QuestionLikelihood> nonLearnedQuestions = new HashMap<>();
    private final Map<Integer, QuestionLikelihood> learnedQuestions = new HashMap<>();

    private Map<Integer, Question> questionMap;

    public QuestionEngine(final QuestionRepository QuestionRepository) {
        this.questionRepository = QuestionRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        long startTime = System.currentTimeMillis();
        questionMap = questionRepository.findAll().stream().collect(
            Collectors.toMap(Question::getId, Function.identity())
        );
        recalculateRandomizer(POWER);
        LOGGER.info(String.format("QuestionEngine has been initialized. Questions size: %d. Time: %d ms.",
            questionMap.values().size(), System.currentTimeMillis() - startTime)
        );
    }

    public List<Map<Integer, QuestionLikelihood>> recalculateRandomizer(double power) {

        long startTime = System.currentTimeMillis();

        nonLearnedQuestions.clear();
        double poweredProbFactorSum = 0;
        int learnedIndexes = 0;

        for (Question question : questionMap.values()) {
            double poweredProbFactor;
            QuestionLikelihood questionLikelihood =
                new QuestionLikelihood(
                    question.getId(),
                    question.getProbabilityFactor(),
                    poweredProbFactor = Math.pow(question.getProbabilityFactor(), power),
                    -1,
                    -1,
                    -1,
                    -1
                );
            if (question.getProbabilityFactor() <= LEARNED_QUESTION_PROB_MARGIN) {
                // Do not perform calculation for already learned questions
                learnedQuestions.put(learnedIndexes++, questionLikelihood);
                continue;
            }
            nonLearnedQuestions.put(questionLikelihood.getId(), questionLikelihood);
            poweredProbFactorSum += poweredProbFactor;
        }

        long index = -1;
        for (QuestionLikelihood question : nonLearnedQuestions.values()) {
            if (question.getProbabilityFactor() <= LEARNED_QUESTION_PROB_MARGIN) {
                continue;
            }
            question.setPercentage(question.getPoweredProbabilityFactor() / poweredProbFactorSum);
            question.setRange(Math.round(MAX_RANGE * question.getPercentage()));
            question.setIndexStart(++index);
            question.setIndexEnd(index = index + question.getRange() - 1);
        }
        if(index < MAX_RANGE * ALLOWABLE_INDEX_ERROR_FACTOR) {
            LOGGER.warn("Quetions' indexes recalculating with fault. End index was: {}", index);
        }
        LOGGER.info("Quetions' indexes has been recalculated. Non learned questions size: {}. Learned questions size:"
                + " {}. Time: {} ms. Index end: {}",
            questionMap.values().size(),
            learnedQuestions.values().size(),
            System.currentTimeMillis() - startTime,
            index
        );
        return Arrays.asList(nonLearnedQuestions, learnedQuestions);
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class QuestionLikelihood {
        int id;
        double probabilityFactor;
        double poweredProbabilityFactor;
        double percentage;
        long range;
        long indexStart = -1;
        long indexEnd = -1;
    }
}
