package com.example.application.database.ClDiDB;

import java.time.Instant;

import com.example.application.model.Answer;
import com.example.application.model.AnswerPayloads;
import com.example.application.model.AnswerPayloads.RollPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RollAnswer extends Answer<RollPayload> {

    protected RollAnswer(AnswerPayloads.RollPayload rollPayload) {
        super(AnswerPayloads.RollPayload.class);
    }

    @Column(nullable = false)
    private Instant timestamp;

    @Override
    public void answer(RollPayload p) {
        this.timestamp = p.timestamp();
    }
}
