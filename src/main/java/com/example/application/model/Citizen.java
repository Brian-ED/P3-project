package com.example.application.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

// ChatGPT generated class
class EventSource<T> {

    public static class Token {}
    private final Map<Token, Function<T,Void>> listeners = new HashMap<>();

    public Token addListener(Function<T,Void> listener) {
        Token t = new Token();
        listeners.put(t, listener);
        return t;
    }

    public void removeListener(Token token) {
        listeners.remove(token);
    }

    public void fire(T value) {
        for (var entry : listeners.entrySet()) {
            entry.getValue().apply(value);
        }
    }
}


public class Citizen implements User {
    public Citizen(String fullName, Long id, AnsweredSurvey[] answeredSurveys, Optional<SleepAdvisor> assignedAdvisor) {
        this.fullName = fullName;
        this.id = id;
        this.answeredSurveys = answeredSurveys;
        this.assignedAdvisor = assignedAdvisor;
    }

    public final Long id;
    private String fullName;

    @Override
    public String getFullName() {
        return fullName;
    }

    public final DynamicSurvey currentSurveyMorning = new DynamicSurvey(SurveyType.morning);
    public final DynamicSurvey currentSurveyEvening = new DynamicSurvey(SurveyType.evening);
    private Optional<SleepAdvisor> assignedAdvisor;
    private AnsweredSurvey[] answeredSurveys;

    private EventSource<Integer> userID = new EventSource<>();
    public EventSource.Token addLoginListener(Function<Integer,Void> listener) {return userID.addListener(listener);}
    public void removeLoginListener(EventSource.Token token) {userID.removeListener(token);}
    public void userLoggedIn(Integer value) {userID.fire(value);}

    /* TODO */ public void seeAnsweredSurvey() {}
    /* TODO */ public void answerMorningSurvey() {}
    /* TODO */ public void answerEveningSurvey() {}
    public void changeAssignedAdvisor(Optional<SleepAdvisor> newSleepAdvisor) {
        this.assignedAdvisor = newSleepAdvisor;
    }
    public Optional<SleepAdvisor> getAssignedAdvisor() {
        return this.assignedAdvisor;
    }
    @Override
	public UserType getUserType() {
		return UserType.CITIZEN;
	}
    public AnsweredSurvey[] getSurveys() {
        return this.answeredSurveys;
    };

}
