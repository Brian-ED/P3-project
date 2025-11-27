package com.example.database;

import java.time.Instant;
import java.time.LocalTime;

import com.example.model.Answer;
import com.example.model.AnswerPayloads;
import com.example.model.AnswerPayloads.ComboBoxPayload;
import com.example.model.AnswerPayloads.RollPayload;
import com.example.model.AnswerPayloads.YesOrNoPayload;
import com.example.model.Citizen;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

public class ClDiDB {

    @Embeddable
    public static class Survey {
        Survey(Citizen answerer) {
            super();
            this.answerer = answerer;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(nullable = false)
        private Long id;

        @Column(nullable = false)
        private Citizen answerer;
    }
    @Embeddable
    public static class MorningSurvey {
        @Embedded
        Survey survey;

        MorningSurvey(Citizen answerer) {
            super();
            this.survey = new Survey(answerer);
        }
    }
    @Embeddable
    public static class EveningSurvey {
        @Embedded Survey survey;

        @Embedded ComboBoxAnswer answer1;
        @Embedded RollAnswer answer2;
        @Embedded ComboBoxAnswer answer3;

        EveningSurvey(Citizen answerer) {
            super();
            this.survey = new Survey(answerer);
        }
    }

    @Entity
    @Table
    public static class AnsweredSurveyMorning {
        @Embedded
        MorningSurvey survey;

        AnsweredSurveyMorning() {
            super();
        }
    }
    @Entity
    @Table
    public static class AnsweredSurveyEvening {
        @Embedded
        EveningSurvey survey;

        AnsweredSurveyEvening() {
            super();
        }
    }
    @Entity
    @Table
    public static class UnAnsweredSurveyMorning {
        @Embedded
        MorningSurvey survey;

        UnAnsweredSurveyMorning(Citizen answerer) {
            super();
            this.survey = new MorningSurvey(answerer);
        }
    }

    @Entity
    @Table
    public static class UnAnsweredSurveyEvening {
        @Embedded
        MorningSurvey survey;

        UnAnsweredSurveyEvening(Citizen answerer) {
            super();
            this.survey = new MorningSurvey(answerer);
        }
    }

    @Embeddable
    public static class ComboBoxAnswer extends Answer<ComboBoxPayload> {

        protected ComboBoxAnswer(AnswerPayloads.ComboBoxPayload payloadType) {
            super(AnswerPayloads.ComboBoxPayload.class);
        }

        @Column(nullable = false)
        private Short whichIsSelected;

        @Override
        public void answer(ComboBoxPayload p) {
            this.whichIsSelected = p.whichIsSelected();
        }
    }

    @Embeddable
    public static class RollAnswer extends Answer<RollPayload> {

        protected RollAnswer(AnswerPayloads.RollPayload rollPayload) {
            super(AnswerPayloads.RollPayload.class);
        }

        @Column(nullable = false)
        private Instant timestamp;

        @Override
        public void answer(RollPayload p) {
            this.timestamp = p.timestamp();
        }
    }

    @Embeddable
    public static class YesOrNoAnswer extends Answer<YesOrNoPayload> {

        public YesOrNoAnswer(AnswerPayloads.YesOrNoPayload yesOrNoPayload) {
            super(AnswerPayloads.YesOrNoPayload.class);
        }

        @Column(nullable = false)
        private Boolean value;

        @Override
        public void answer(YesOrNoPayload p) {
            this.value = p.yes();
        }
    }

    @Embeddable
    abstract public static class YesOrNoElaborateDatesAnswer {

        @Column(nullable = false)
        private boolean yesNoValue;

        @Column
        @Nullable
        private LocalTime elabValueDate1;

        @Column
        @Nullable
        private LocalTime elabValueDate2;

        protected YesOrNoElaborateDatesAnswer(boolean yesNoValue, LocalTime elabValueDate1, LocalTime elabValueDate2) {
            if (yesNoValue == false) {
                assert elabValueDate1 == null;
                assert elabValueDate2 == null;
            } else {
                assert elabValueDate1 != null;
                assert elabValueDate2 != null;
            }
            this.yesNoValue = yesNoValue;
            this.elabValueDate1 = elabValueDate1;
            this.elabValueDate2 = elabValueDate2;
        }

    }

    @Embeddable
    public static class TimeFieldAnswer {

        @Column(nullable = false)
        private LocalTime elabValueDate;

        protected TimeFieldAnswer(LocalTime elabValueDate) {
            assert elabValueDate != null;
            this.elabValueDate = elabValueDate;
        }
    }

    @Embeddable
    public static class TextFieldAnswer {

        @Lob
        @Column(nullable = false)
        private String value;

        protected TextFieldAnswer(String value) {
            this.value = value;
        }
    };

    @Embeddable
    public static class NumberFieldAnswer {

        @Column(nullable = false)
        private Integer answer;

        protected NumberFieldAnswer(Integer answer) {
            this.answer = answer;
        }
    };

    @Embeddable
    public static class CheckboxGroupAnswer_MAX_31 {

        private static Integer signedIntSizeExcludingSignBit = 31;

        @Column(nullable = false)
        private Integer answer;

        private static Integer booleansToInt(boolean[] checkboxes) {
            assert checkboxes.length <= signedIntSizeExcludingSignBit;
            Integer result = 0;
            for (Integer i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i]) {
                    result |= 1 << i;
                }
            }
            return result;
        }

        private static boolean[] intToBooleans(Integer value) {
            boolean[] arr = new boolean[31];
            for (int i = 0; i < 31; i++) {
                arr[i] = (value & (1 << i)) != 0;
            }
            return arr;
        }
        CheckboxGroupAnswer_MAX_31(boolean[] answer) {
            super();
            assert answer.length <= signedIntSizeExcludingSignBit;

            this.answer = booleansToInt(answer);
        }

        public boolean[] getAnswer() {
        return intToBooleans(answer);
        }
    };

    //    CHECKBOX_GROUP,
    //    TIME_FIELD
}
