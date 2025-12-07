package com.example.application.database.ClDiDB;

import com.example.application.model.Answer;
import com.example.application.model.AnswerPayload.TextFieldPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TextFieldAnswer extends Answer<TextFieldPayload> {
    @Column(nullable = false, columnDefinition = "text")
    private String value;

    public TextFieldAnswer() {
        super(TextFieldPayload.class);
    }

    public TextFieldAnswer(TextFieldPayload p) {
        super(TextFieldPayload.class);
        answer(p);
    }

    public void answer(TextFieldPayload value) {
        this.value = value.text();
    }

	@Override
	public TextFieldPayload toPayload() {
		return new TextFieldPayload(value);
	}
};
