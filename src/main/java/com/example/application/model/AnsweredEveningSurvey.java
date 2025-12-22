package com.example.application.model;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.example.application.database.ClDiDB.Questions.GenericQuestion;

public final class AnsweredEveningSurvey extends AnsweredSurvey {
    public AnsweredEveningSurvey(UUID id, GenericQuestion<?>[] answers, ZonedDateTime whenAnswered) {
        super(id, answers, SurveyType.evening, whenAnswered);
    }
}
