package com.example.application.database.ClDiDB;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class NumberFieldAnswer {

    @Column(nullable = false)
    private Integer answer;

    protected NumberFieldAnswer(Integer answer) {
        this.answer = answer;
    }
};
