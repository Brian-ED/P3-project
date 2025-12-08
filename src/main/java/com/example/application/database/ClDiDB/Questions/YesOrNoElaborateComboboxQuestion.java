package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateComboboxAnswer;

public final class YesOrNoElaborateComboboxQuestion extends GenericQuestion<YesOrNoElaborateComboboxAnswer> {
    public YesOrNoElaborateComboboxQuestion(String title, String q0, String[] subs, YesOrNoElaborateComboboxAnswer answer) {
        super(answer, title);
        this.q0 = q0;
        this.subs = subs;
    }

    private final String q0;
    private final String[] subs;

    public String getComboboxQuestionTitle() {
        return q0;
    }

    public String[] getComboboxQuestionOptions() {
        return subs;
    }

    public String format(Boolean hasAnswered) {
        return "";
        /* TODO */
    }
}
