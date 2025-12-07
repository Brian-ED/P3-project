package com.example.application.database.ClDiDB;

import java.time.ZonedDateTime;

import com.example.application.model.Answer;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollRollPayload;

import jakarta.persistence.Column;

public class YesOrNoElaborateRollRollAnswer extends Answer<YesOrNoElaborateRollRollPayload> {

    public YesOrNoElaborateRollRollAnswer() {
        super(YesOrNoElaborateRollRollPayload.class);
    }

    @Column(nullable = false)
    private Boolean yesNo;

    @Column(nullable = false)
    ZonedDateTime timestamp1;

    @Column(nullable = false)
    ZonedDateTime timestamp2;

    @Override
    public void answer(YesOrNoElaborateRollRollPayload p) {
        this.yesNo = p.yesNo();
        this.timestamp1 = p.timestamp1();
        this.timestamp2 = p.timestamp2();
    }

	@Override
	public YesOrNoElaborateRollRollPayload toPayload() {
		return new YesOrNoElaborateRollRollPayload(yesNo, timestamp1, timestamp2);
	}
}
