package com.example.application.database.ClDiDB.Answers;

import com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public final class YesOrNoElaborateComboboxAnswer extends Answer<YesOrNoElaborateComboboxPayload> {

    public YesOrNoElaborateComboboxAnswer() {
        super(YesOrNoElaborateComboboxPayload.class);
    }

    @NotNull
    @Column(nullable = false)
    private Boolean yesNo;

    @NotNull
    @Column(nullable = false)
    private Short whichIsSelected;

    @Override
    public void answerProto(YesOrNoElaborateComboboxPayload p) {
        this.yesNo = p.yesNo();
        this.whichIsSelected = p.whichIsSelected();
    }

	@Override
	public YesOrNoElaborateComboboxPayload toPayload() {
        return new YesOrNoElaborateComboboxPayload(yesNo, whichIsSelected);
	}
}
