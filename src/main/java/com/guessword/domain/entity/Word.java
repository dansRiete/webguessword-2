package com.guessword.domain.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @Override
    public String toString() {
        return word + (transcription != null ? " [" + transcription + "]" : "");
    }
}


