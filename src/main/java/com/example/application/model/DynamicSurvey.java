package com.example.application.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.application.database.ClDiDB.CitizenRow;
import com.example.application.database.ClDiDB.Survey;
import com.example.application.database.ClDiDB.SurveyEveningRow;
import com.example.application.database.ClDiDB.SurveyMorningRow;
import com.example.application.database.ClDiDB.Questions.GenericQuestion;

public class DynamicSurvey {

    public Integer currentQuestionIndex = 0;
    public GenericQuestion<?> currentQuestion() {return survey.getAnswers()[currentQuestionIndex];};
    public Boolean hasCurrentQuestionBeenAnswered() {return hasAnswered[currentQuestionIndex];};
    SurveyType surveyType;
    private final Survey survey;
    private final Boolean[] hasAnswered;
    private final Integer length;
    private final SurveyType type;

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

    public DynamicSurvey(SurveyType type, CitizenRow owner) {
        this.type = type;
        this.survey = switch (type) {
            case evening -> new SurveyEveningRow();
            case morning -> new SurveyMorningRow();
        };
        survey.setOwner(owner);

        this.length = survey.getAnswers().length;
        this.hasAnswered = new Boolean[length];
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
    
    public Boolean submitAnswers() {
        survey.setWhenAnswered(ZonedDateTime.now());

        for(boolean b : hasAnswered) if(!b) return false;
        for (var i : listeners) {
            i.notifySubmitted(survey);
        }
        return true;
    }
}
