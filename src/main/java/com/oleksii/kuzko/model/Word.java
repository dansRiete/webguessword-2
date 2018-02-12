package com.oleksii.kuzko.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * @author The Weather Company, An IBM Business
 */
public class Word {

    private String word;
    private String language;
    private String transcription;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, language, transcription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word) &&
                Objects.equals(language, word1.language) &&
                Objects.equals(transcription, word1.transcription);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("word", word)
                .add("language", language)
                .add("transcription", transcription)
                .toString();
    }
}
