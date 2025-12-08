package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.YesOrNoAnswer;

public final class YesOrNoQuestion extends GenericQuestion<YesOrNoAnswer> {
    YesOrNoQuestion(String title, YesOrNoAnswer answer) {
        super(answer, title);
    }

    public String format() {
        return "";
        /* TODO */
    }
}
