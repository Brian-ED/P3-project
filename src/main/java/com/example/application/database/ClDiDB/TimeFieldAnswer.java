package com.example.application.database.ClDiDB;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TimeFieldAnswer {

    @Column(nullable = false)
    private LocalTime elabValueDate;

    protected TimeFieldAnswer(LocalTime elabValueDate) {
        assert elabValueDate != null;
        this.elabValueDate = elabValueDate;
    }
}
