package com.example.model;

public class AnsweredSurvey {
    SurveyType surveyType;
    Answer<?>[] answers;

    AnsweredSurvey(Answer<?>[] answers, SurveyType surveyType) {
        this.surveyType = surveyType;
        this.answers = answers;
    }
}
