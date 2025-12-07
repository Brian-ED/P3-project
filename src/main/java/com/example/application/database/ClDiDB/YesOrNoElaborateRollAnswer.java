package com.example.application.database.ClDiDB;

import java.time.ZonedDateTime;

import com.example.application.model.Answer;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class YesOrNoElaborateRollAnswer extends Answer<YesOrNoElaborateRollPayload> {

    public YesOrNoElaborateRollAnswer() {
        super(YesOrNoElaborateRollPayload.class);
    }

    @Column(nullable = false)
    private Boolean value;

    @Column(nullable = false)
    ZonedDateTime timestamp;

    @Override
    public void answer(YesOrNoElaborateRollPayload p) {
        this.value = p.yesNo();
        this.timestamp = p.timestamp();
    }

	@Override
	public YesOrNoElaborateRollPayload toPayload() {
        return new YesOrNoElaborateRollPayload(value, timestamp);
	}
}
