package com.example.application.model;

import java.time.Instant;

public class AnswerPayloads {
    public record YesOrNoPayload(boolean yesNo) implements AnswerPayload {}
    public record YesOrNoElaborateIntPayload(boolean yesNo, int elaborate) implements AnswerPayload {}
    public record RollPayload(Instant timestamp) implements AnswerPayload {}
    public record ComboBoxPayload(Short whichIsSelected) implements AnswerPayload {}
}
