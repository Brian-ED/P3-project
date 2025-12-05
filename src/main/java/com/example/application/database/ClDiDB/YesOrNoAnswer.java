package com.example.application.database.ClDiDB;

import com.example.application.model.Answer;
import com.example.application.model.AnswerPayload.YesOrNoPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class YesOrNoAnswer extends Answer<YesOrNoPayload> {
    public YesOrNoAnswer() {
        super(YesOrNoPayload.class);
    }

    @Column(nullable = false)
    private Boolean yesNo;

    @Override
    public void answer(YesOrNoPayload p) {
        this.yesNo = p.yesNo();
    }

	@Override
	public YesOrNoPayload toPayload() {
        return new YesOrNoPayload(yesNo);
	}
}
