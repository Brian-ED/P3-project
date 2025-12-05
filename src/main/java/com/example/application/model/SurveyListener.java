package com.example.application.model;

public interface SurveyListener {
    void currentQuestionChanged(int newIndex);
    void questionAnswered(int index, AnswerPayload payload);
}
