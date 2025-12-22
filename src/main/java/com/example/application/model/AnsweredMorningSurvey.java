package com.example.application.model;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.example.application.database.ClDiDB.Questions.GenericQuestion;

public final class AnsweredMorningSurvey extends AnsweredSurvey {
    public AnsweredMorningSurvey(UUID id, GenericQuestion<?>[] answers, ZonedDateTime whenAnswered) {
        super(id, answers, SurveyType.morning, whenAnswered);
    }
}
