package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateRollRollAnswer;

public final class YesOrNoElaborateRollRollQuestion extends GenericQuestion<YesOrNoElaborateRollRollAnswer> {
    public YesOrNoElaborateRollRollQuestion(String title, String q0, String q1, YesOrNoElaborateRollRollAnswer answer) {
        super(answer, title);
        this.q0 = q0;
        this.q1 = q1;
    }

    private final String q0, q1;

    public String getRollQuestion0Title() {
        return q0;
    }

    public String getRollQuestion1Title() {
        return q1;
    }

    public String format() {
        return "";
        /* TODO */
    }
}
