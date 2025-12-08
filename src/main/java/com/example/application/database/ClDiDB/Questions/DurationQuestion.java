package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.DurationAnswer;

public final class DurationQuestion extends GenericQuestion<DurationAnswer> {
    public DurationQuestion(String title, DurationAnswer answer) {
        super(answer, title);
    }

    public String format() {
        return "";
        /* TODO */
    }
}
