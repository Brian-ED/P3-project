package com.example.application.model;

import java.util.Optional;

import com.example.application.database.ClDiDB.ComboBoxAnswer;
import com.example.application.database.ClDiDB.DurationAnswer;
import com.example.application.database.ClDiDB.RollAnswer;
import com.example.application.database.ClDiDB.TextFieldAnswer;
import com.example.application.database.ClDiDB.YesOrNoAnswer;
import com.example.application.database.ClDiDB.YesOrNoElaborateComboboxAnswer;
import com.example.application.database.ClDiDB.YesOrNoElaborateComboboxRollAnswer;
import com.example.application.database.ClDiDB.YesOrNoElaborateRollAnswer;
import com.example.application.database.ClDiDB.YesOrNoElaborateRollComboboxAnswer;
import com.example.application.database.ClDiDB.YesOrNoElaborateRollRollAnswer;

public class Question {

    public final String questionTitle;
    public final Optional<String[]> answerCases;
    public final QuestionType type;
    public final Question[] subQuestions;
    public final Answer<?> answer;


    private Answer<?> getAnswer(QuestionType type, Question[] subQuestions) {
        return switch (type) {
            case COMBOBOX -> switch (subQuestions.length) {
                case 0 -> new ComboBoxAnswer();
                default -> throw new IllegalArgumentException("Unexpected value: " + type.toString() + subQuestions.toString());
            };
            case TIME_FIELD -> switch (subQuestions.length) {
                case 0 -> new RollAnswer();
                default -> throw new IllegalArgumentException("Unexpected value: " + type.toString() + subQuestions.toString());
            };
            case YES_NO -> switch (subQuestions.length) {
                case 0 -> new YesOrNoAnswer();
                case 1 -> switch (subQuestions[0].type) {
                    case COMBOBOX -> new YesOrNoElaborateComboboxAnswer();
                    case TIME_FIELD -> new YesOrNoElaborateRollAnswer();
                    default -> throw new IllegalArgumentException("Unexpected value: " + subQuestions[0].type);
                };
                case 2 -> switch (subQuestions[0].type) {
                    case TIME_FIELD -> switch (subQuestions[1].type) {
                        case TIME_FIELD -> new YesOrNoElaborateRollRollAnswer();
                        case COMBOBOX -> new YesOrNoElaborateRollComboboxAnswer();
                        default -> throw new IllegalArgumentException("Unexpected value: " + subQuestions[0].type);
                    };
                    case COMBOBOX -> switch (subQuestions[1].type) {
                        case TIME_FIELD -> new YesOrNoElaborateComboboxRollAnswer();
                        default -> throw new IllegalArgumentException("Unexpected value: " + subQuestions[0].type);
                    };
                    default -> throw new IllegalArgumentException("Unexpected value: " + subQuestions[0].type);
                };
                default -> throw new IllegalArgumentException("Unexpected value: " + type.toString() + subQuestions.toString());
            };
            case DURATION -> switch (subQuestions.length) {
                case 0 -> new DurationAnswer();
                default -> throw new IllegalArgumentException("Unexpected value: " + type.toString() + subQuestions.toString());
            };
            case TEXT_FIELD -> switch (subQuestions.length) {
                case 0 -> new TextFieldAnswer();
                default -> throw new IllegalArgumentException("Unexpected value: " + type.toString() + subQuestions.toString());
            };
        };
    }

    public Question(String questionTitle, QuestionType type) {
        this.questionTitle = questionTitle;
        this.type = type;
        this.subQuestions = new Question[0];
        this.answerCases = Optional.empty();
        this.answer = getAnswer(type, subQuestions);
    }

    public Question(String questionTitle, QuestionType type, String[] answerCases) {
        this.questionTitle = questionTitle;
        this.type = type;
        this.subQuestions = new Question[0];
        this.answerCases = Optional.of(answerCases);
        this.answer = getAnswer(type, subQuestions);
    }

    public Question(String questionTitle, QuestionType type, Question[] subQuestions) {
        this.questionTitle = questionTitle;
        this.type = type;
        this.subQuestions = subQuestions;
        this.answerCases = Optional.empty();
        this.answer = getAnswer(type, subQuestions);
    }

}
