package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.DurationAnswer;

public final class RollQuestionShort extends GenericQuestion<DurationAnswer> {

    public RollQuestionShort(String title, DurationAnswer answer) {
        super(answer, title);
    }

    @Override
    public String format(Boolean hasAnswered) {
        return "";
        // TODO: format value when needed
    }
}
