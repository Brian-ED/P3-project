package com.example.application.model;

import com.example.application.database.ClDiDB.Survey;

public interface SurveyListener {
    void currentQuestionChanged(int newIndex);
    void questionAnswered(int index, AnswerPayload payload);
    void notifySubmitted(Survey survey);
}
