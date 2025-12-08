package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.ComboBoxAnswer;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public final class ComboBoxQuestion extends GenericQuestion<ComboBoxAnswer> {
    public ComboBoxQuestion(String title, String[] subs, ComboBoxAnswer answer) {
        super(answer, title);
        this.subs = subs;
    }

    private final String[] subs;

    public String[] getComboboxQuestionOptions() {
        return subs;
    }

    @Embedded
    private final ComboBoxAnswer answer = new ComboBoxAnswer();

    public String format(Boolean hasAnswered) {
        return "";
        /* TODO */
    }
}
