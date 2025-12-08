package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateRollAnswer;

public final class YesOrNoElaborateRollQuestion extends GenericQuestion<YesOrNoElaborateRollAnswer> {
    public YesOrNoElaborateRollQuestion(String title, String q0, YesOrNoElaborateRollAnswer answer) {
        super(answer, title);
        this.q0 = q0;
    }

    private final String q0;

    public String getRollQuestionTitle() {
        return q0;
    }

    public String format(Boolean hasAnswered) {
        return "";
        /* TODO */
    }
}
