package com.example.application.database.ClDiDB;


import java.time.ZonedDateTime;

import com.example.application.model.Answer;
import com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxRollPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class YesOrNoElaborateComboboxRollAnswer extends Answer<YesOrNoElaborateComboboxRollPayload> {

    public YesOrNoElaborateComboboxRollAnswer() {
        super(YesOrNoElaborateComboboxRollPayload.class);
    }

    @Column(nullable = false)
    private Boolean value;

    @Column(nullable = false)
    Short whichIsSelected;

    @Column(nullable = false)
    ZonedDateTime timestamp;

    @Override
    public void answer(YesOrNoElaborateComboboxRollPayload p) {
        this.value = p.yesNo();
        this.whichIsSelected = p.whichIsSelected();
        this.timestamp = p.timestamp();
    }

	@Override
	public YesOrNoElaborateComboboxRollPayload toPayload() {
        return new YesOrNoElaborateComboboxRollPayload(value, whichIsSelected, timestamp);
	}
}
