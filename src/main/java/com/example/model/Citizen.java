package com.example.model;

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


public class Citizen {
    public Citizen() {}

    private String fullName;
    private Optional<DynamicSurvey> currentSurvey;
    private SleepAdvisor assignedAdvisor;
    private AnsweredSurvey[] answeredSurveys;

    private EventSource<Integer> userID = new EventSource<>();
    public EventSource.Token addLoginListener(Function<Integer,Void> listener) {return userID.addListener(listener);}
    public void removeLoginListener(EventSource.Token token) {userID.removeListener(token);}
    public void userLoggedIn(Integer value) {userID.fire(value);}

    /* TODO */ public void seeAnsweredSurvey() {}
    /* TODO */ public void answerMorningSurvey() {}
    /* TODO */ public void answerEveningSurvey() {}
    public void changeAssignedAdvisor(SleepAdvisor newSleepAdvisor) {
        this.assignedAdvisor = newSleepAdvisor;
    }
}
