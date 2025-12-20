package com.example.application.database.ClDiDB.Questions;

import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateRollRollAnswer;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollRollPayload;

public final class YesOrNoElaborateRollRollQuestion extends GenericQuestion<YesOrNoElaborateRollRollAnswer> { 
    public YesOrNoElaborateRollRollQuestion(String title, String q0, String q1, YesOrNoElaborateRollRollAnswer answer) 
    { 
        super(answer, title); 
        this.q0 = q0; 
        this.q1 = q1; } 
        
        private final String q0, q1;
         
        public String getRollQuestion0Title() { return q0; } 
        public String getRollQuestion1Title() { return q1; } 
        
        public String format(Boolean hasAnswered) { 
            if (hasAnswered) { 
                YesOrNoElaborateRollRollPayload x = this.getAnswer().toPayload(); 
                if (x.yesNo()) 
                    { return getMainQuestionTitle() + 
                        ", svaret: Ja; og " + q0 + ", svaret: " + 
                        x.timestamp1() + "; og " + q1 + ", svaret:" + 
                        x.timestamp2() + ";"; } 
                        else { 
                            return getMainQuestionTitle() + ", svaret: Nej"; } 
                        } 
                        else { 
                            return getMainQuestionTitle() + ", og hvis ja: " + q0 + " og " + q1; 
                        } 
                    } 
                }