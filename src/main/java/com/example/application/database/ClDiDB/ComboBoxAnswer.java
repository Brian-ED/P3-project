package com.example.application.database.ClDiDB;

import com.example.application.model.Answer;
import com.example.application.model.AnswerPayload.ComboBoxPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ComboBoxAnswer extends Answer<ComboBoxPayload> {

    public ComboBoxAnswer() {
        super(ComboBoxPayload.class);
    }

    @Column(nullable = false)
    private Short whichIsSelected;

    @Override
    public void answer(ComboBoxPayload p) {
        this.whichIsSelected = p.whichIsSelected();
    }

	@Override
	public ComboBoxPayload toPayload() {
        return new ComboBoxPayload(whichIsSelected);
    }
}
