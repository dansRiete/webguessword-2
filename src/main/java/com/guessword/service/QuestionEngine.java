package com.guessword.service;

import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import com.guessword.dao.QuestionRepository;
import com.guessword.entity.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class QuestionEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionEngine.class);
    private static final double CHANCE_OF_APPEARING_TRAINED_PHRASES = 1D / 35D;

    private final QuestionRepository questionRepository;

    private Map<Integer, Question> questionMap;
    private int activePhrasesNumber;
    private int activeUntrainedPhrasesNumber;
    private int activeTrainedPhrasesNumber;
    private int greatestPhrasesIndex;
    private HashSet<String> selectedLabels;

    public QuestionEngine(final QuestionRepository QuestionRepository) {
        this.questionRepository = QuestionRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        questionMap =  questionRepository.findAll().stream().collect(
            Collectors.toMap(Question::getId, question -> question)
        );
        reloadIndices();
    }

    public void reloadIndices() {
        System.out.println("reloadIndices() from PhraseRepository");

        /*if(availablePhrases.isEmpty()){
            throw new RuntimeException("Active Phrases list was empty. Reload indices impossible");
        }*/

        final long RANGE = 1_000_000_000;
        long startTime = System.currentTimeMillis();
        double temp = 0;
        double indexOfTrained;      //  Index of appearing learnt words
        double rangeOfUnTrained;    //  Range indices non learnt words
        double scaleOfOneProb;
        int modificatePhrasesIndicesNumber = 0;
        int untrainedPhrasesProbabilityFactorsSumm = 0;
        this.activePhrasesNumber = 0;
        this.activeUntrainedPhrasesNumber = 0;

        for (Question question : questionMap.values()) {
            question.setIndexStart(0);
            question.setIndexEnd(0);
            if (question.isInList(selectedLabels)) {
                this.activePhrasesNumber++;
                if (question.getProbabilityFactor() > 3) {
                    this.activeUntrainedPhrasesNumber++;
                    untrainedPhrasesProbabilityFactorsSumm += question.getProbabilityFactor();
                }
            }
        }

        this.activeTrainedPhrasesNumber = activePhrasesNumber - activeUntrainedPhrasesNumber;
        indexOfTrained = CHANCE_OF_APPEARING_TRAINED_PHRASES / activeTrainedPhrasesNumber;
        rangeOfUnTrained = activeTrainedPhrasesNumber > 0 ? 1 - CHANCE_OF_APPEARING_TRAINED_PHRASES : 1;
        scaleOfOneProb = rangeOfUnTrained / untrainedPhrasesProbabilityFactorsSumm;

        for (Question question : questionMap.values()) { //Sets indices for nonlearnt words
            if (question.isInList(this.selectedLabels)) {
                int indexStart;
                int indexEnd;
                double prob;
                prob = question.getProbabilityFactor();

                //If activeUntrainedPhrasesNumber == 0 then all words have been learnt, setting equal for all indices
                if (activeUntrainedPhrasesNumber == 0) {

                    indexStart = (int) (temp * RANGE);
                    question.setIndexStart(indexStart);
                    temp += CHANCE_OF_APPEARING_TRAINED_PHRASES / activeTrainedPhrasesNumber;
                    indexEnd = (int) ((temp * RANGE) - 1);
                    question.setIndexEnd(indexEnd);

                } else { //Otherwise, set indices by algorithm

                    if (prob > 3) {

                        indexStart = (int) (temp * RANGE);
                        question.setIndexStart(indexStart);
                        temp += scaleOfOneProb * prob;
                        indexEnd = (int) ((temp * RANGE) - 1);
                        question.setIndexEnd(indexEnd);

                    } else {

                        indexStart = (int) (temp * RANGE);
                        question.setIndexStart(indexStart);
                        temp += indexOfTrained;
                        indexEnd = (int) ((temp * RANGE) - 1);
                        question.setIndexEnd(indexEnd);
                    }
                }

                modificatePhrasesIndicesNumber++;
                if (modificatePhrasesIndicesNumber == activePhrasesNumber) {
                    this.greatestPhrasesIndex = question.getIndexEnd();
                }
            }
        }

        LOGGER.info(String.format("Indices reloaded. Size: %d, time: %d ms", modificatePhrasesIndicesNumber,
            System.currentTimeMillis() - startTime));
    }

}
