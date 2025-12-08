package com.example.application.database.ClDiDB.Answers;

import com.example.application.model.AnswerPayload.TextFieldPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public final class TextFieldAnswer extends Answer<TextFieldPayload> {
    @Column(nullable = false, columnDefinition = "text")
    private String value;

    public TextFieldAnswer() {
        super(TextFieldPayload.class);
    }

    public void answerProto(TextFieldPayload value) {
        this.value = value.text();
    }

	@Override
	public TextFieldPayload toPayload() {
		return new TextFieldPayload(value);
	}
};
