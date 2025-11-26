package com.example.model;

import java.util.HashMap;
import java.util.Map;

public class AnswerRegistry {

    private final Map<Class<? extends AnswerPayload>, Answer<?>> registry = new HashMap<>();

    public <P extends AnswerPayload> void register(Class<P> payloadClass, Answer<P> answer) {
        registry.put(payloadClass, answer);
    }

    @SuppressWarnings("unchecked")
    public <P extends AnswerPayload> void dispatch(P payload) {
        Answer<P> answer = (Answer<P>) registry.get(payload.getClass());
        if (answer == null) {
            throw new IllegalStateException("No answer registered for " + payload.getClass());
        }
        answer.answer(payload);
    }
}
