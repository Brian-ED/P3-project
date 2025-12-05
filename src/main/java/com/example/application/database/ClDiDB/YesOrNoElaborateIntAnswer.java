package com.example.application.database.ClDiDB;

import com.example.application.model.Answer;
import com.example.application.model.AnswerPayloads;
import com.example.application.model.AnswerPayloads.YesOrNoElaborateIntPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class YesOrNoElaborateIntAnswer extends Answer<YesOrNoElaborateIntPayload> {

    public YesOrNoElaborateIntAnswer(AnswerPayloads.YesOrNoElaborateIntPayload yesOrNoPayload) {
        super(AnswerPayloads.YesOrNoElaborateIntPayload.class);
    }

    @Column(nullable = false)
    private Boolean value;

    @Override
    public void answer(YesOrNoElaborateIntPayload p) {
        this.value = p.yesNo();
    }
}
