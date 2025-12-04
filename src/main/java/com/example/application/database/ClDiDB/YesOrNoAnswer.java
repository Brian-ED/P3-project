package com.example.application.database.ClDiDB;

import com.example.application.model.Answer;
import com.example.application.model.AnswerPayloads;
import com.example.application.model.AnswerPayloads.YesOrNoPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class YesOrNoAnswer extends Answer<YesOrNoPayload> {

    public YesOrNoAnswer(AnswerPayloads.YesOrNoPayload yesOrNoPayload) {
        super(AnswerPayloads.YesOrNoPayload.class);
    }

    @Column(nullable = false)
    private Boolean value;

    @Override
    public void answer(YesOrNoPayload p) {
        this.value = p.yesNo();
    }
}
