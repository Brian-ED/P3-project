package com.example.application.database.ClDiDB.Answers;

import java.time.ZonedDateTime;

import com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxRollPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public final class YesOrNoElaborateComboboxRollAnswer extends Answer<YesOrNoElaborateComboboxRollPayload> {

    public YesOrNoElaborateComboboxRollAnswer() {
        super(YesOrNoElaborateComboboxRollPayload.class);
    }

    @NotNull
    @Column(nullable = false)
    private Boolean value;

    @NotNull
    @Column(nullable = false)
    private Short whichIsSelected;

    @NotNull
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
