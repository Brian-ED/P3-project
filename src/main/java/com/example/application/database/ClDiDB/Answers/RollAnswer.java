package com.example.application.database.ClDiDB.Answers;

import java.time.ZonedDateTime;

import com.example.application.model.AnswerPayload.RollPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public final class RollAnswer extends Answer<RollPayload> {
    public RollAnswer() {
        super(RollPayload.class);
    }

    @NotNull
    @Column(nullable = false)
    private ZonedDateTime timestamp;

    @Override
    public void answerProto(RollPayload p) {
        this.timestamp = p.timestamp();
    }

	@Override
	public RollPayload toPayload() {
        return new RollPayload(timestamp);
	}
}
