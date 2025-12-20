package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateRollRollAnswer;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollRollPayload;

public final class YesOrNoElaborateRollRollQuestion
        extends GenericQuestion<YesOrNoElaborateRollRollAnswer> {

    private final RollQuestionShort q0;
    private final RollQuestion q1;

    public YesOrNoElaborateRollRollQuestion(
            String title,
            RollQuestionShort q0,
            RollQuestion q1,
            YesOrNoElaborateRollRollAnswer answer
    ) {
        super(answer, title);
        this.q0 = q0;
        this.q1 = q1;
    }

    public RollQuestionShort getRollQuestion0() {
        return q0;
    }

    public RollQuestion getRollQuestion1() {
        return q1;
    }

    public String getRollQuestion0Title() {
        return q0.getMainQuestionTitle();
    }

    public String getRollQuestion1Title() {
        return q1.getMainQuestionTitle();
    }

    @Override
    public String format(Boolean hasAnswered) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'format'");
    }
}