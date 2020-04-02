package com.oleksii.kuzko.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    private Integer id;

    @Column(nullable = false)
    private Double probabilityFactor;

    @Column(nullable = false)
    private Double probabilityMultiplier;

    @Column(nullable = false)
    private LocalDateTime created;

    private LocalDateTime lastAccessed;

    private String tag;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Word> words;

    @Override
    public String toString() {
        final String s = "Question{"
            + words.stream().map(Word::getWord).collect(Collectors.joining(",")) + "}"
            + ", prob=" + ((int) probabilityFactor.doubleValue());
        return s;
    }
}
