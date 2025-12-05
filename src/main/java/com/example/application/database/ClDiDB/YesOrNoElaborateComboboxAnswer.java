package com.example.application.database.ClDiDB;

import com.example.application.model.Answer;
import com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class YesOrNoElaborateComboboxAnswer extends Answer<YesOrNoElaborateComboboxPayload> {

    public YesOrNoElaborateComboboxAnswer() {
        super(YesOrNoElaborateComboboxPayload.class);
    }

    @Column(nullable = false)
    private Boolean value;

    @Column(nullable = false)
    Short whichIsSelected;

    @Override
    public void answer(YesOrNoElaborateComboboxPayload p) {
        this.value = p.yesNo();
        this.whichIsSelected = p.whichIsSelected();
    }

	@Override
	public YesOrNoElaborateComboboxPayload toPayload() {
        return new YesOrNoElaborateComboboxPayload(value, whichIsSelected);
	}
}
