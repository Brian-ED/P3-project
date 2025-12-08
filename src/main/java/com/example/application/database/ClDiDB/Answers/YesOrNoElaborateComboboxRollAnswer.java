package com.example.application.database.ClDiDB.Answers;

import java.time.ZonedDateTime;

import com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxRollPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public final class YesOrNoElaborateComboboxRollAnswer extends Answer<YesOrNoElaborateComboboxRollPayload> {

    public YesOrNoElaborateComboboxRollAnswer() {
        super(YesOrNoElaborateComboboxRollPayload.class);
    }

    @Column(nullable = false)
    private Boolean value;

    @Column(nullable = false)
    private Short whichIsSelected;

    @Column(nullable = false)
    private ZonedDateTime timestamp;

    @Override
    public void answerProto(YesOrNoElaborateComboboxRollPayload p) {
        this.value = p.yesNo();
        this.whichIsSelected = p.whichIsSelected();
        this.timestamp = p.timestamp();
    }

	@Override
	public YesOrNoElaborateComboboxRollPayload toPayload() {
        return new YesOrNoElaborateComboboxRollPayload(value, whichIsSelected, timestamp);
	}
}
