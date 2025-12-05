package com.example.application.database.ClDiDB;

import com.example.application.model.Answer;
import com.example.application.model.AnswerPayloads;
import com.example.application.model.AnswerPayloads.ComboBoxPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ComboBoxAnswer extends Answer<ComboBoxPayload> {

    protected ComboBoxAnswer(AnswerPayloads.ComboBoxPayload payloadType) {
        super(AnswerPayloads.ComboBoxPayload.class);
    }

    @Column(nullable = false)
    private Short whichIsSelected;

    @Override
    public void answer(ComboBoxPayload p) {
        this.whichIsSelected = p.whichIsSelected();
    }
}
