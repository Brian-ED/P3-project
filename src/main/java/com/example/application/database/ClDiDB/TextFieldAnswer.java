package com.example.application.database.ClDiDB;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TextFieldAnswer {

    @Column(nullable = false, columnDefinition = "text")
    private String value;

    protected TextFieldAnswer(String value) {
        this.value = value;
    }
};
