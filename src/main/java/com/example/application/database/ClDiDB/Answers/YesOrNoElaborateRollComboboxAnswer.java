package com.example.application.database.ClDiDB.Answers;


import java.time.ZonedDateTime;

import com.example.application.model.AnswerPayload.YesOrNoElaborateRollComboboxPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public final class YesOrNoElaborateRollComboboxAnswer extends Answer<YesOrNoElaborateRollComboboxPayload> {

    public YesOrNoElaborateRollComboboxAnswer() {
        super(YesOrNoElaborateRollComboboxPayload.class);
    }

    @NotNull
    @Column(nullable = false)
    private Boolean value;

    @NotNull
    @Column(nullable = false)
    private ZonedDateTime timestamp;

    @NotNull
    @Column(nullable = false)
    private Short whichIsSelected;

    @Override
    public void answerProto(YesOrNoElaborateRollComboboxPayload p) {
        this.value = p.yesNo();
        this.timestamp = p.timestamp();
        this.whichIsSelected = p.whichIsSelected();
    }

	@Override
	public YesOrNoElaborateRollComboboxPayload toPayload() {
        return new YesOrNoElaborateRollComboboxPayload(value, timestamp, whichIsSelected);
	}
}
