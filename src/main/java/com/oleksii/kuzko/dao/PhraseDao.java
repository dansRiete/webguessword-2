package com.oleksii.kuzko.dao;

import com.oleksii.kuzko.model.Phrase;
import com.oleksii.kuzko.model.User;
import com.oleksii.kuzko.model.Word;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import javax.sql.DataSource;

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

    private final static PostgresqlPhraseMapper POSTGRESQL_PHRASE_MAPPER = new PostgresqlPhraseMapper();
//    private final static MysqlPhraseMapper MYSQL_PHRASE_MAPPER = new MysqlPhraseMapper();
    private final static Logger LOGGER = Logger.getLogger(PhraseDao.class);

    private final static String SELECT_ALL_POSTGRES = "SELECT\n" +
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

    private final static String SELECT_ALL_MYSQL =
            "SELECT * FROM guessword.words INNER JOIN guessword.users " +
                    "ON user_id = guessword.users.id";

//    private final static String INSERT_PHRASE =

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final NamedParameterJdbcTemplate mysqlNamedParameterJdbcTemplate;

    public PhraseDao(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            @Qualifier("mysqlDatasource") DataSource mysqlDatasource
    ) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.mysqlNamedParameterJdbcTemplate = new NamedParameterJdbcTemplate(mysqlDatasource);
    }

    public List<Phrase> getAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_POSTGRES, POSTGRESQL_PHRASE_MAPPER);
    }

    /*public int createAll(List<Phrase> phrasesToCreate){

    }*/

    public List<Phrase> getAllMysql() {
        return mysqlNamedParameterJdbcTemplate.query(SELECT_ALL_MYSQL, new MysqlPhraseMapper(false));
    }

    private final static class PostgresqlPhraseMapper implements ResultSetExtractor<List<Phrase>> {

        @Override
        public List<Phrase> extractData(ResultSet rs) throws SQLException, DataAccessException {
            //todo not to complete
            List<Phrase> phrases = new ArrayList<>();
            List<Word> words = new ArrayList<>();
            Phrase phrase = null;
            while (rs.next()) {
                String phraseId = rs.getString(PHRASE_ID);
                if (phrase == null || !phrase.getId().equals(phraseId)) {
                    String label = rs.getString(PHRASE_LABEL);
                    LocalDateTime lastAccessDate = DateTimeUtils.toLocalDateTime(rs.getTimestamp(PHRASE_LAST_ACCESS_DATE));
                    double probabilityFactor = rs.getFloat(PHRASE_PROBABILITY_FACTOR);
                    double probabilityMultiplier = rs.getFloat(PHRASE_PROBABILITY_MULTIPLIER);
                    phrases.add(new Phrase(
                            rs.getString(PHRASE_ID), DateTimeUtils.toLocalDateTime(rs.getTimestamp(PHRASE_CREATION_DATE)),
                            probabilityFactor, probabilityMultiplier, label, null/*todo*/, null/*todo*/, words, lastAccessDate
                    ));
                }
                if (rs.getString(WORDS_WORD) != null) {
                    Word word = new Word(
                            rs.getString(WORDS_WORD),
                            rs.getString(WORDS_WORD_LANGUAGE_CODE_ALIAS),
                            rs.getString(WORDS_WORD_TRANSCRIPTION_ALIAS)
                    );
                    phrase.getWords().add(word);
                }
            }
            return phrases;

        }
    }


    private final static class MysqlPhraseMapper implements ResultSetExtractor<List<Phrase>> {

        private final boolean includeUser;

        public MysqlPhraseMapper(boolean includeUser) {
            this.includeUser = includeUser;
        }

        @Override
        public List<Phrase> extractData(ResultSet rs) throws SQLException, DataAccessException {

            List<Phrase> phrases = new ArrayList<>();
            while (rs.next()) {

                final String forWord = rs.getString("for_word");
                final String natWord = rs.getString("nat_word");
                final String transcription = rs.getString("transcr");
                final String label = rs.getString("label") != null && rs.getString("label").equals("")
                        ? null : rs.getString("label");
                final User user = new User(rs.getString("login"), rs.getString("email"), rs.getString("name"), rs.getString("password"));


                LocalDateTime lastAccessDate = DateTimeUtils.toLocalDateTime(rs.getTimestamp("last_accs_date"));
                double probabilityFactor = new BigDecimal(rs.getFloat("prob_factor"))
                        .setScale(2, RoundingMode.UP).doubleValue();
                double probabilityMultiplier = new BigDecimal(rs.getFloat("rate"))
                        .setScale(2, RoundingMode.UP).doubleValue();

                if (forWord.contains("/") && natWord.contains("/")) {

                    final String[] forWords = forWord.split("/");
                    final String[] natWords = natWord.split("/");
                    if (forWords.length != natWords.length) {
                        LOGGER.error("forWords.length != natWords.length, forWord = " + forWord + ", natWord = " + natWord);
                        continue;
                    }
                    for (int i = 0; i < forWords.length; i++) {
                        Word foreignWord = new Word(forWords[i], "en", transcription);
                        Word nativeWord = new Word(natWords[i], "ru", null);
                        List<Word> words = Collections.unmodifiableList(Arrays.asList(foreignWord, nativeWord));
                        phrases.add(
                                new Phrase(
                                        UUID.randomUUID().toString(),
                                        DateTimeUtils.toLocalDateTime(rs.getTimestamp("create_date")),
                                        probabilityFactor,
                                        probabilityMultiplier,
                                        label,
                                        user.getLogin(), includeUser ? user : null,
                                        words,
                                        lastAccessDate
                                )
                        );
                    }

                } else {

                    List<Word> words = new ArrayList<>();


                    if (forWord.contains("/")) {
                        List<Word> engWords = Arrays.stream(forWord.split("/"))
                                .map(wordLiteral -> new Word(wordLiteral, "en", transcription))
                                .collect(Collectors.toList());
                        words.addAll(engWords);
                    } else {
                        words.add(new Word(forWord, "en", transcription));
                    }

                    if (natWord.contains("/")) {
                        List<Word> natWords = Arrays.stream(natWord.split("/"))
                                .map(wordLiteral -> new Word(wordLiteral, "ru", null))
                                .collect(Collectors.toList());
                        words.addAll(natWords);
                    } else {
                        words.add(new Word(natWord, "ru", null));
                    }

                    phrases.add(new Phrase(
                            UUID.randomUUID().toString(),
                            DateTimeUtils.toLocalDateTime(rs.getTimestamp("create_date")),
                            probabilityFactor,
                            probabilityMultiplier,
                            label,
                            user.getLogin(),
                            includeUser ? user : null,
                            Collections.unmodifiableList(words),
                            lastAccessDate
                    ));
                }


            }
            return phrases;
        }
    }

}
