package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateComboboxRollAnswer;

public final class YesOrNoElaborateComboboxRollQuestion
        extends GenericQuestion<YesOrNoElaborateComboboxRollAnswer> {
    public YesOrNoElaborateComboboxRollQuestion(String title, String q0, String[] q0Subs, String q1,
            YesOrNoElaborateComboboxRollAnswer answer) {
        super(answer, title);
        this.q0 = q0;
        this.q0Subs = q0Subs;
        this.q1 = q1;
    }

    private final String q0, q1;
    private final String[] q0Subs;

    public String getComboboxQuestionTitle() {
        return q0;
    }

    public String getRollQuestionTitle() {
        return q1;
    }

    public String[] getComboboxQuestionOptions() {
        return q0Subs;
    }

    public String format() {
        return "";
        /* TODO */
    }
}
