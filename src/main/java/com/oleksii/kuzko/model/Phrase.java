package com.oleksii.kuzko.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author The Weather Company, An IBM Business
 */
public class Phrase {

    private String id;
    private String label;
    private List<Word> words = new ArrayList<>();
    private LocalDateTime creationDate;
    private LocalDateTime lastAccessDate;
    private double probabilityFactor;
    private double probabilityMultiplier;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public LocalDateTime getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(LocalDateTime lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public double getProbabilityFactor() {
        return probabilityFactor;
    }

    public void setProbabilityFactor(double probabilityFactor) {
        this.probabilityFactor = probabilityFactor;
    }

    public double getProbabilityMultiplier() {
        return probabilityMultiplier;
    }

    public void setProbabilityMultiplier(double probabilityMultiplier) {
        this.probabilityMultiplier = probabilityMultiplier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, words, creationDate, lastAccessDate, probabilityFactor, probabilityMultiplier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Phrase phrase = (Phrase) o;
        return Double.compare(phrase.probabilityFactor, probabilityFactor) == 0 &&
                Double.compare(phrase.probabilityMultiplier, probabilityMultiplier) == 0 &&
                Objects.equals(id, phrase.id) &&
                Objects.equals(label, phrase.label) &&
                Objects.equals(words, phrase.words) &&
                Objects.equals(creationDate, phrase.creationDate) &&
                Objects.equals(lastAccessDate, phrase.lastAccessDate);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("label", label)
                .add("words", words)
                .add("creationDate", creationDate)
                .add("lastAccessDate", lastAccessDate)
                .add("probabilityFactor", probabilityFactor)
                .add("probabilityMultiplier", probabilityMultiplier)
                .toString();
    }
}
