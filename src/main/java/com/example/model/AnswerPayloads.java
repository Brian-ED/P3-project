package com.example.model;

import java.time.Instant;

public class AnswerPayloads {
    public record YesOrNoPayload(boolean yes) implements AnswerPayload {}
    public record RollPayload(Instant timestamp) implements AnswerPayload {}
    public record ComboBoxPayload(Short whichIsSelected) implements AnswerPayload {}
}
