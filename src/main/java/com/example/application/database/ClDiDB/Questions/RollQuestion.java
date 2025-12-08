package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.RollAnswer;

public final class RollQuestion extends GenericQuestion<RollAnswer> {
    public RollQuestion(String title, RollAnswer answer) {
        super(answer, title);
    }

    public String format() {
        return "";
        /* TODO */
    }
}
