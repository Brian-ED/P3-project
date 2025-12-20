package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateRollRollAnswer;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollRollPayload;

public final class YesOrNoElaborateRollRollQuestion
        extends GenericQuestion<YesOrNoElaborateRollRollAnswer> {

    private final RollQuestionShort q0; // minutes
    private final RollQuestion q1;      // time-of-day

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

    public String format(Boolean hasAnswered) {
        if (hasAnswered) {
            YesOrNoElaborateRollRollPayload x = this.getAnswer().toPayload();
            if (x.yesNo()) {
                // Use the *titles from the objects* instead of old strings
                return getMainQuestionTitle()
                        + ", svaret: Ja; og "
                        + q0.getMainQuestionTitle() + ", svaret: " + x.timestamp1()
                        + "; og "
                        + q1.getMainQuestionTitle() + ", svaret:" + x.timestamp2()
                        + ";";
            } else {
                return getMainQuestionTitle() + ", svaret: Nej";
            }
        } else {
            return getMainQuestionTitle()
                    + ", og hvis ja: "
                    + q0.getMainQuestionTitle() + " og "
                    + q1.getMainQuestionTitle();
        }
    }
}
