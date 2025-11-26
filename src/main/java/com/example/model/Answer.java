package com.example.model;

public abstract class Answer<P extends AnswerPayload> {
    private final Class<P> payloadType;

    protected Answer(Class<P> payloadType) {
        this.payloadType = payloadType;
    }

    public Class<P> type() {
        return payloadType;
    }

    public abstract void answer(P payload);

}
