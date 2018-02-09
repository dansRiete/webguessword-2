package com.oleksii.kuzko.dao;

import com.oleksii.kuzko.model.Language;
import com.oleksii.kuzko.model.Phrase;
import com.oleksii.kuzko.model.Word;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author The Weather Company, An IBM Business
 */
@Repository
public class PhraseDao {

    private final static String PHRASE_ID = "id";
    private final static String USER_LOGIN = "user_login";
    private final static String PHRASE_CREATION_DATE = "creation_date";
    private final static String PHRASE_PROBABILITY_FACTOR = "probability_factor";
    private final static String PHRASE_PROBABILITY_MULTIPLIER = "probability_multiplier";
    private final static String PHRASE_LAST_ACCESS_DATE = "last_access_date";
    private final static String PHRASE_LABEL = "label";
    private final static String PHRASE_WORD_ID_ALIAS = "word_id";
    private final static String WORDS_WORD = "word";
    private final static String WORDS_WORD_ADDITION_DATE_ALIAS = "word_addition_date";
    private final static String WORDS_WORD_LANGUAGE_CODE_ALIAS = "word_language_code";
    private final static String WORDS_WORD_LANGUAGE_NAME_ALIAS = "word_language_name";
    private final static String WORDS_WORD_TRANSCRIPTION_ALIAS = "word_transcription";

    private final static PhraseMapper PHRASE_MAPPER = new PhraseMapper();

    private final static String SELECT_ALL = "SELECT\n" +
            "  phrases." + PHRASE_ID + ",\n" +
            "  phrases." + USER_LOGIN + ",\n" +
            "  phrases." + PHRASE_CREATION_DATE + ",\n" +
            "  phrases." + PHRASE_PROBABILITY_FACTOR + ",\n" +
            "  phrases." + PHRASE_PROBABILITY_MULTIPLIER + ",\n" +
            "  phrases." + PHRASE_LAST_ACCESS_DATE + ",\n" +
            "  phrases." + PHRASE_LABEL + ",\n" +
            "  phrases_words.phrase_word_id AS " + PHRASE_WORD_ID_ALIAS + ",\n" +
            "  words." + WORDS_WORD + ",\n" +
            "  phrases_words.addition_date AS " + WORDS_WORD_ADDITION_DATE_ALIAS + ",\n" +
            "  languages.code AS " + WORDS_WORD_LANGUAGE_CODE_ALIAS + ",\n" +
            "  languages.language_name AS " + WORDS_WORD_LANGUAGE_NAME_ALIAS + ",\n" +
            "  words.transcription AS " + WORDS_WORD_TRANSCRIPTION_ALIAS + "\n" +
            "FROM public.phrases\n" +
            "  INNER JOIN phrases_words ON phrases.id = phrases_words.phrase_id\n" +
            "  INNER JOIN words ON phrases_words.word = words.word\n" +
            "  INNER JOIN languages ON words.language_code = languages.code\n" +
            "  WHERE phrases.is_active = TRUE AND phrases_words.is_active = TRUE\n" +
            "  ORDER BY user_login, id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PhraseDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Phrase> getAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL, PHRASE_MAPPER);
    }

    private final static class PhraseMapper implements ResultSetExtractor<List<Phrase>> {

        @Override
        public List<Phrase> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Phrase> phrases = new ArrayList<>();
            Phrase phrase = null;
            while (rs.next()) {
                String phraseId = rs.getString(PHRASE_ID);
                if (phrase == null || !phrase.getId().equals(phraseId)) {
                    phrase = new Phrase();
                    phrase.setId(rs.getString(PHRASE_ID));
                    phrase.setLabel(rs.getString(PHRASE_LABEL));
                    phrase.setCreationDate(DateTimeUtils.toZonedDateTime(rs.getTimestamp(PHRASE_CREATION_DATE)));
                    phrase.setLastAccessDate(DateTimeUtils.toZonedDateTime(rs.getTimestamp(PHRASE_LAST_ACCESS_DATE)));
                    phrase.setProbabilityFactor(rs.getFloat(PHRASE_PROBABILITY_FACTOR));
                    phrase.setProbabilityMultiplier(rs.getFloat(PHRASE_PROBABILITY_MULTIPLIER));
                    phrases.add(phrase);
                }
                String word = rs.getString(WORDS_WORD);
                if (word != null) {
                    Language language = new Language();
                    language.setCode(rs.getString(WORDS_WORD_LANGUAGE_CODE_ALIAS));
                    language.setName(rs.getString(WORDS_WORD_LANGUAGE_NAME_ALIAS));
                    Word word1 = new Word();
                    word1.setWord(word);
                    word1.setLanguage(language);
                    word1.setTranscription(rs.getString(WORDS_WORD_TRANSCRIPTION_ALIAS));
                    //Put an empty ArrayList if absent
                    phrase.getWords().computeIfAbsent(language, k -> new ArrayList<>());
                    phrase.getWords().get(language).add(word1);
                }
            }
            return phrases;
        }
    }

}
