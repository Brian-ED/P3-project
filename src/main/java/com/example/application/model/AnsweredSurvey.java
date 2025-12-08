package com.example.application.model;

import java.time.ZonedDateTime;

import com.example.application.database.ClDiDB.Questions.GenericQuestion;

public class AnsweredSurvey {
    private final SurveyType surveyType;
    private final GenericQuestion<?>[] answers;
    private final ZonedDateTime whenAnswered;

    public SurveyType getSurveyType() {return surveyType;}

    public AnsweredSurvey(GenericQuestion<?>[] answers, SurveyType surveyType, ZonedDateTime whenAnswered) {
        this.surveyType = surveyType;
        this.answers = answers;
        this.whenAnswered = whenAnswered;
    }
    // Getter-metoder som bruges i Citizenview
    public GenericQuestion<?>[] getAnswers() {
        return answers;
    }

    public ZonedDateTime getWhenAnswered() {
        return whenAnswered;
    }

    public SurveyType getType() {
        return surveyType;
    }
}
