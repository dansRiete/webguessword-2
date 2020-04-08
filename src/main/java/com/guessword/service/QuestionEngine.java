package com.guessword.service;

import java.util.HashMap;
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

    private final QuestionRepository questionRepository;
    private final Map<Integer, QuestionLikelihood> randomizer = new HashMap<>();

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

    private void recalculateRandomizer(double power) {

        long startTime = System.currentTimeMillis();

        randomizer.clear();
        double poweredProbFactorSum = 0;

        for (Question question : questionMap.values()) {
            if (question.getProbabilityFactor() <= 3) {
                continue;
            }
            double shiftedProbF;
            QuestionLikelihood questionLikelihood =
                new QuestionLikelihood(
                    question.getId(),
                    question.getProbabilityFactor(),
                    shiftedProbF = Math.pow(question.getProbabilityFactor(), power),
                    -1,
                    -1,
                    -1,
                    -1
                );
            randomizer.put(questionLikelihood.getId(), questionLikelihood);
            poweredProbFactorSum += shiftedProbF;
        }

        long index = -1;
        for (QuestionLikelihood question : randomizer.values()) {
            if (question.getProbabilityFactor() <= 3) {
                continue;
            }
            question.setPercentage(question.getPoweredProbabilityFactor() / poweredProbFactorSum);
            question.setRange(Math.round(MAX_RANGE * question.getPercentage()));
            question.setIndexStart(++index);
            question.setIndexEnd(index = index + question.getRange() - 1);
        }

        LOGGER.info(String.format("Randomizer has been recalculated. Questions size: %d. Time: %d ms.",
            questionMap.values().size(), System.currentTimeMillis() - startTime));
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    private static class QuestionLikelihood {

        int id;
        double probabilityFactor;
        double poweredProbabilityFactor;
        double percentage;
        long range;
        long indexStart = -1;
        long indexEnd = -1;
    }
}
