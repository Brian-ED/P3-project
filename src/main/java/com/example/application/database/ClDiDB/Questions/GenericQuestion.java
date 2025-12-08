package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.Answer;

public sealed abstract class GenericQuestion<A extends Answer<?>>
    permits
        YesOrNoElaborateRollRollQuestion,
        YesOrNoElaborateRollQuestion,
        YesOrNoElaborateComboboxRollQuestion,
        YesOrNoElaborateRollComboboxQuestion,
        YesOrNoElaborateComboboxQuestion,
        ComboBoxQuestion,
        RollQuestion,
        DurationQuestion,
        TextFieldQuestion,
        YesOrNoQuestion {

    public GenericQuestion(A answer, String title) {
        this.answer = answer;
        this.title = title;
    }
    private final String title;

    private final A answer;

    public String getMainQuestionTitle(){
        return title;
    };

    public A getAnswer() {
        return answer;
    };

    abstract public String format();
}
