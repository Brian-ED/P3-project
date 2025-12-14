package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.RollAnswer;


public final class FineRollQuestion extends GenericQuestion<RollAnswer> {
    public FineRollQuestion(String title, RollAnswer answer) {
        super(answer, title);
    }

    public String format(Boolean hasAnswered) {
        return "";
        /* TODO */
    }
}