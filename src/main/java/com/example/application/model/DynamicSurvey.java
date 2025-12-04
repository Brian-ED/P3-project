package com.example.application.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DynamicSurvey {

    private static Question AskMoreIfYesQuestion(String title, Question... more) {
        return new Question(title, QuestionType.YES_NO, more);
    }
    private static Question RollQuestion(String title) {
        return new Question(title, QuestionType.TIME_FIELD);
    }
    private static Question ComboBoxQuestion(String title, String... options) {
        return new Question(title, QuestionType.COMBOBOX, options);
    }

    public static Question[] morningSurvey = new Question[] {
        AskMoreIfYesQuestion("Tager du nogen gange sovemedicin eller melatonin piller",
            ComboBoxQuestion("Hvad tager du?", "Sovemedicin", "Melatonin")),
        ComboBoxQuestion("Hvad foretog du dig de sidste par timer inden du gik i seng?", "Ting", "Sager"),
        RollQuestion("I går gik jeg i seng klokken:"),
        RollQuestion("Jeg slukkede lyset klokken:"),
        RollQuestion("Efter jeg slukkede lyset, sov jeg ca. efter:"),
        ComboBoxQuestion("Jeg vågnede cirka x gange i løbet af natten:",
            "1", "2", "3"),
        RollQuestion("Jeg var sammenlagt vågen i cirka x minutter i løbet af natten"),
        RollQuestion("I morges vågnede jeg klokken:"),
        RollQuestion("Og jeg stod op klokken:")
    };
    public static Question[] eveningSurvey = new Question[] {
        AskMoreIfYesQuestion("Har du været fysisk aktiv i dag?",
            RollQuestion("Hvor mange minutter?"),
            RollQuestion("Hvornår på dagen?")),
        AskMoreIfYesQuestion("Har du været ude i dagslys?",
            RollQuestion("Hvornår på dagen?")),
        AskMoreIfYesQuestion("Har du drukket koffeinholdige drikke i dag?",
            ComboBoxQuestion ("Hvilke drikke (flere kan vælges)", "Monster", "Kaffe", "Sodavand"),
            RollQuestion("Hvornår på dagen indtager du den sidste drik?")),
        AskMoreIfYesQuestion(
            "Har du drukket alkohol?",
            RollQuestion ("Hvornår på dagen drak du den sidste genstand?"),
            ComboBoxQuestion("Hvor mange genstande har du ca. drukket i løbet af dagen?",
                "1", "2", "3")),
        AskMoreIfYesQuestion("Har du sovet i løbet af dagen?",
            RollQuestion("Hvornår på dagen"))
    };

    public Integer currentQuestionIndex = 0;
    public Question currentQuestion() {return surveyQuestions[currentQuestionIndex];};
    SurveyType surveyType;
    public final Integer length;
    public final Question[] surveyQuestions;
    public Optional<Answer<?>>[] surveyAnswers;

    private final List<SurveyListener> listeners = new ArrayList<>();

    public void addListener(SurveyListener l) {
        listeners.add(l);
    }

    public void removeListener(SurveyListener l) {
        listeners.remove(l);
    }

    // Should only have idempotent functions subscribed
    private void notifyCurrentQuestionChanged() {
        for (var l : listeners) {
            l.currentQuestionChanged(currentQuestionIndex);
        }
    }

    private void notifyQuestionAnswered(int index, AnswerPayload payload) {
        for (var l : listeners) {
            l.questionAnswered(index, payload);
        }
    }

    public DynamicSurvey(SurveyType surveyType) {
        this.surveyType = surveyType;
        this.surveyQuestions = switch (surveyType) {
            case morning -> morningSurvey;
            case evening -> eveningSurvey;
        };
        this.length = surveyQuestions.length;
    }

    public void nextQuestion() {
        if (currentQuestionIndex + 1 < surveyQuestions.length) {
            currentQuestionIndex += 1;
            notifyCurrentQuestionChanged();
        }
    }

    public void previousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex -= 1;
            notifyCurrentQuestionChanged();
        }
    }

    public Optional<AnsweredSurvey> submitAnswers() {
        Answer<?>[] answers = {};
        // Fill in answers array from dynamic questions
        for (int i = 0; i < surveyAnswers.length; i++) {
            Optional<Answer<?>> q = surveyAnswers[i];
            if (q.isEmpty()) {
                return Optional.empty();
            }
            answers[i] = q.orElseThrow();
        }

        return Optional.of(new AnsweredSurvey(answers, surveyType, ZonedDateTime.now()));
    }
}
