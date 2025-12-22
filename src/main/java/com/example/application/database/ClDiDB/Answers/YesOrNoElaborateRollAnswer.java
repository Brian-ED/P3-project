package com.example.application.database.ClDiDB.Answers;

import java.time.ZonedDateTime;

import com.example.application.model.AnswerPayload.YesOrNoElaborateRollPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public final class YesOrNoElaborateRollAnswer extends Answer<YesOrNoElaborateRollPayload> {

    public YesOrNoElaborateRollAnswer() {
        super(YesOrNoElaborateRollPayload.class);
    }

    @NotNull
    @Column(nullable = false)
    private Boolean value;

    @NotNull
    @Column(nullable = false)
    private ZonedDateTime timestamp;

    @Override
    public void answerProto(YesOrNoElaborateRollPayload p) {
        this.value = p.yesNo();
        this.timestamp = p.timestamp();
    }

	@Override
	public YesOrNoElaborateRollPayload toPayload() {
        return new YesOrNoElaborateRollPayload(value, timestamp);
	}
}
