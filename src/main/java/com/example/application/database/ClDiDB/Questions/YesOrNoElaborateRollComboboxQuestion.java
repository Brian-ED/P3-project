package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateRollComboboxAnswer;

public final class YesOrNoElaborateRollComboboxQuestion
        extends GenericQuestion<YesOrNoElaborateRollComboboxAnswer> {
    public YesOrNoElaborateRollComboboxQuestion(String title, String q0, String q1, String[] q1Subs,
            YesOrNoElaborateRollComboboxAnswer answer) {
        super(answer, title);
        this.q0 = q0;
        this.q1 = q1;
        this.q1Subs = q1Subs;
    }

    private final String q0, q1;
    private final String[] q1Subs;

    public String getRollQuestionTitle() {
        return q0;
    }

    public String getComboboxQuestionTitle() {
        return q1;
    }

    public String[] getComboboxQuestionOptions() {
        return q1Subs;
    }

    public String format() {
        return "";
        /* TODO */
    }
}
