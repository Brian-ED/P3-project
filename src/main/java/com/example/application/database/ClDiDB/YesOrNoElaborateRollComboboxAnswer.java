package com.example.application.database.ClDiDB;


import java.time.ZonedDateTime;

import com.example.application.model.Answer;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollComboboxPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class YesOrNoElaborateRollComboboxAnswer extends Answer<YesOrNoElaborateRollComboboxPayload> {

    public YesOrNoElaborateRollComboboxAnswer() {
        super(YesOrNoElaborateRollComboboxPayload.class);
    }

    @Column(nullable = false)
    private Boolean value;

    @Column(nullable = false)
    ZonedDateTime timestamp;

    @Column(nullable = false)
    Short whichIsSelected;

    @Override
    public void answer(YesOrNoElaborateRollComboboxPayload p) {
        this.value = p.yesNo();
        this.timestamp = p.timestamp();
        this.whichIsSelected = p.whichIsSelected();
    }

	@Override
	public YesOrNoElaborateRollComboboxPayload toPayload() {
        return new YesOrNoElaborateRollComboboxPayload(value, timestamp, whichIsSelected);
	}
}
