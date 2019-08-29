package com.oleksii.kuzko.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Question extends BaseModel {

    private final LocalDateTime creationDate;
    private final Double probabilityFactor;
    private final Double probabilityMultiplier;
    private final String label;
    private final String userId;
    private final User user;
    private final List<Word> words;
    private final LocalDateTime lastAccessDate;

    public Question(
            String id, LocalDateTime creationDate, Double probabilityFactor,
            Double probabilityMultiplier, String label, String userId, User user, List<Word> words,
            LocalDateTime lastAccessDate
    ) {
        super(id);
        this.probabilityFactor = probabilityFactor;
        this.probabilityMultiplier = probabilityMultiplier;
        this.creationDate = creationDate;
        this.label = label;
        this.userId = userId;
        this.user = user;
        this.words = words;
        this.lastAccessDate = lastAccessDate;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public List<Word> getWords() {
        return words;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public LocalDateTime getLastAccessDate() {
        return lastAccessDate;
    }

    public double getProbabilityFactor() {
        return probabilityFactor;
    }

    public double getProbabilityMultiplier() {
        return probabilityMultiplier;
    }

    public User getUser() {
        return user;
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
        Question question = (Question) o;
        return Double.compare(question.probabilityFactor, probabilityFactor) == 0 &&
                Double.compare(question.probabilityMultiplier, probabilityMultiplier) == 0 &&
                Objects.equals(id, question.id) &&
                Objects.equals(label, question.label) &&
                Objects.equals(words, question.words) &&
                Objects.equals(creationDate, question.creationDate) &&
                Objects.equals(lastAccessDate, question.lastAccessDate);
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

    public String getUserId() {
        return userId;
    }
}
