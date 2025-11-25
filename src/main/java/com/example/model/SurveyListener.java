package com.example.model;

public interface SurveyListener {
    void currentQuestionChanged(int newIndex);
    void questionAnswered(int index, AnswerPayload payload);
}
