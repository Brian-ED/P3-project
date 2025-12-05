package com.example.application.model;

public abstract class Answer<P extends AnswerPayload> {
    private final Class<P> payloadClass;

    protected Answer(Class<P> payloadClass) {
        this.payloadClass = payloadClass;
    }

    public abstract void answer(P payload);
    public abstract P toPayload();

    public Class<P> getPayloadClass() {
        return payloadClass;
    }

}
