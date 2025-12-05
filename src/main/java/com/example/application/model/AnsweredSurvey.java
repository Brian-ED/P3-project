package com.example.application.model;

import java.time.ZonedDateTime;

public class AnsweredSurvey {
    public final SurveyType surveyType;
    public final Answer<?>[] answers;
    public final ZonedDateTime whenAnswered;

    public AnsweredSurvey(Answer<?>[] answers, SurveyType surveyType, ZonedDateTime whenAnswered) {
        this.surveyType = surveyType;
        this.answers = answers;
        this.whenAnswered = whenAnswered;
    }
}
