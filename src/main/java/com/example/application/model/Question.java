package com.example.application.model;

import java.util.Optional;

import com.example.application.database.ClDiDB.YesOrNoAnswer;
import com.example.application.model.AnswerPayloads.YesOrNoPayload;

public class Question {

    public final String questionTitle;
    public final Optional<String[]> answerCases; // used (only?) in combobox
    public final QuestionType type;
    public final Question[] subQuestions;

    public Question(String questionTitle, QuestionType type) {
        this.questionTitle = questionTitle;
        this.type = type;
        this.subQuestions = new Question[0];
        this.answerCases = Optional.empty();
    }

    public Question(String questionTitle, QuestionType type, String[] answerCases) {
        this.questionTitle = questionTitle;
        this.type = type;
        this.subQuestions = new Question[0];
        this.answerCases = Optional.of(answerCases);
    }

    public Question(String questionTitle, QuestionType type, Question[] subQuestions) {
        this.questionTitle = questionTitle;
        this.type = type;
        this.subQuestions = subQuestions;
        this.answerCases = Optional.empty();
    }

    // Temporary till Answers are properly implement
    public Answer<?> answer() {
        YesOrNoPayload p = new YesOrNoPayload(true);
        return new YesOrNoAnswer(p);
    }
}
