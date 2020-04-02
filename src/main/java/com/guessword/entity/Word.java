package com.guessword.entity;


import java.util.Arrays;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "word_seq")
    private Integer id;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private String language;

    private String transcription;

    private String normalizeWord(String word) {
        return Arrays.stream(word.split(" "))
            .map(word1 -> word1.equals("I") ? "I" : word1.toLowerCase()).collect(Collectors.joining(" "));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("word", word)
            .toString();
    }
}


