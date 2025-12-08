package com.example.application.database.ClDiDB.Answers;

import com.example.application.model.AnswerPayload.YesOrNoPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public final class YesOrNoAnswer extends Answer<YesOrNoPayload> {
    public YesOrNoAnswer() {
        super(YesOrNoPayload.class);
    }

    @Column(nullable = false)
    private Boolean yesNo;

    @Override
    public void answerProto(YesOrNoPayload p) {
        this.yesNo = p.yesNo();
    }

	@Override
	public YesOrNoPayload toPayload() {
        return new YesOrNoPayload(yesNo);
	}
}
