package com.example.application.model;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.example.application.database.ClDiDB.Questions.GenericQuestion;

public sealed abstract class AnsweredSurvey permits AnsweredMorningSurvey, AnsweredEveningSurvey {
    private final SurveyType surveyType;
    private final GenericQuestion<?>[] answers;
    private final ZonedDateTime whenAnswered;
    private final UUID id;

    public SurveyType getSurveyType() {return surveyType;}

    public AnsweredSurvey(UUID id, GenericQuestion<?>[] answers, SurveyType surveyType, ZonedDateTime whenAnswered) {
        this.id = id;
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

    public UUID getID() {
        return id;
    }

    public SurveyType getType() {
        return surveyType;
    }
}
