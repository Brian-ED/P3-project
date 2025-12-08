package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.TextFieldAnswer;

public final class TextFieldQuestion extends GenericQuestion<TextFieldAnswer> {
    public TextFieldQuestion(String title, TextFieldAnswer answer) {
        super(answer, title);
    }

    public String format(Boolean hasAnswered) {
        return "";
        /* TODO */
    }
}
