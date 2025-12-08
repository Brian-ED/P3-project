package com.example.application.database.ClDiDB.Answers;

import com.example.application.model.AnswerPayload.DurationPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public final class DurationAnswer extends Answer<DurationPayload> {

    public DurationAnswer() {
        super(DurationPayload.class);
    }

    @Column(nullable = false)
    private Integer minutes;

    @Override
    public void answerProto(DurationPayload p) {
        this.minutes = p.minutes();
    }

    @Override
    public DurationPayload toPayload() {
        return new DurationPayload(minutes);
    }
}
