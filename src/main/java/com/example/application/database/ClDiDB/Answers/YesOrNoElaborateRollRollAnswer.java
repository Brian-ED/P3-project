package com.example.application.database.ClDiDB.Answers;

import java.time.ZonedDateTime;

import com.example.application.model.AnswerPayload.YesOrNoElaborateRollRollPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public final class YesOrNoElaborateRollRollAnswer extends Answer<YesOrNoElaborateRollRollPayload> {

    public YesOrNoElaborateRollRollAnswer() {
        super(YesOrNoElaborateRollRollPayload.class);
    }

    @NotNull
    @Column(nullable = false)
    private Boolean yesNo;

    @NotNull
    @Column(nullable = false)
    private ZonedDateTime timestamp1;

    @NotNull
    @Column(nullable = false)
    private ZonedDateTime timestamp2;

	@Override
	public YesOrNoElaborateRollRollPayload toPayload() {
		return new YesOrNoElaborateRollRollPayload(yesNo, timestamp1, timestamp2);
	}

	@Override
	protected void answerProto(YesOrNoElaborateRollRollPayload p) {
        this.yesNo = p.yesNo();
        this.timestamp1 = p.timestamp1();
        this.timestamp2 = p.timestamp2();
	}
}
