package com.example.application.database.ClDiDB.Answers;

import com.example.application.model.AnswerPayload.ComboBoxPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public final class ComboBoxAnswer extends Answer<ComboBoxPayload> {

    public ComboBoxAnswer() {
        super(ComboBoxPayload.class);
    }

    @Column(nullable = false)
    private Short whichIsSelected;

    @Override
    public void answerProto(ComboBoxPayload p) {
        this.whichIsSelected = p.whichIsSelected();
    }

	@Override
	public ComboBoxPayload toPayload() {
        return new ComboBoxPayload(whichIsSelected);
    }
}
