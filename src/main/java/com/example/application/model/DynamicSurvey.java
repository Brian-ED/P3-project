package com.example.application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.example.application.database.ClDiDB.Questions.GenericQuestion;

public class DynamicSurvey {

    public Integer currentQuestionIndex = 0;
    public GenericQuestion<?> currentQuestion() {
        return questions[currentQuestionIndex];
    };
    public Boolean hasCurrentQuestionBeenAnswered() {return hasAnswered[currentQuestionIndex];};
    SurveyType surveyType;
    private final boolean[] hasAnswered;
    private final Integer length;
    private final SurveyType type;
    private final GenericQuestion<?>[] questions;

    private final List<SurveyListener> listeners = new ArrayList<>();
    private final Supplier<AnsweredSurvey> submitListenerToProvideAnsweredSurvey;

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

    public DynamicSurvey(SurveyType type, GenericQuestion<?>[] questions, Citizen owner, Supplier<AnsweredSurvey> submitListenerToProvideAnsweredSurvey) {
        this.submitListenerToProvideAnsweredSurvey = submitListenerToProvideAnsweredSurvey;
        this.type = type;
        this.questions = questions;
        this.length = questions.length;
        this.hasAnswered = new boolean[length];
        for (int i=0; i<questions.length; i++) {
            final int index = i;
            questions[i].getAnswer().addListener(a->{hasAnswered[index] = true;});
        }
    }

    public void nextQuestion() {
        if (currentQuestionIndex + 1 < length) {
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

    public SurveyType getType() {
        return type;
    }

    public Optional<AnsweredSurvey> submitAnswers() {
        for(boolean b : hasAnswered) {
            if(!b) return Optional.empty();
        }
        return Optional.of(submitListenerToProvideAnsweredSurvey.get());
    }

    public int totalQuestions() {
        return length;
    }
}
