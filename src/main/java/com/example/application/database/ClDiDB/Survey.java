package com.example.application.database.ClDiDB;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.example.application.database.ClDiDB.Questions.GenericQuestion;
import com.example.application.model.SurveyType;

public sealed interface Survey permits SurveyEveningRow, SurveyMorningRow {
    SurveyType getType();
    UUID getID();
    CitizenRow getOwner();
    void setOwner(CitizenRow owner);
    ZonedDateTime getWhenAnswered();
    void setWhenAnswered(ZonedDateTime whenAnswered);
    GenericQuestion<?>[] getAnswers();
}
