package com.example.application.model;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import com.example.application.database.ClDiDB.CitizenRow;
import com.example.application.database.ClDiDB.SurveyEveningRow;
import com.example.application.database.ClDiDB.SurveyMorningRow;

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
    private final CitizenRow row;
    public Citizen(CitizenRow citizenData) {
        List<SurveyMorningRow> mornings = citizenData.getMorningSurveys();
        List<SurveyEveningRow> evenings = citizenData.getEveningSurveys();
        AnsweredSurvey[] answeredSurveys = new AnsweredSurvey[mornings.size()+evenings.size()];
        for (int i = 0; i<mornings.size(); i++) {
            SurveyMorningRow m = mornings.get(i);
            answeredSurveys[i] = new AnsweredSurvey(m.getAnswers(), SurveyType.morning, m.getWhenAnswered());
        }
        for (int i = 0; i<evenings.size(); i++)  {
            SurveyEveningRow e = evenings.get(i);
            answeredSurveys[mornings.size() + i] = new AnsweredSurvey(e.getAnswers(), SurveyType.morning, e.getWhenAnswered());
        }
        this.row = citizenData;
        this.fullName = citizenData.getFullName();
        this.answeredSurveys = answeredSurveys;
        this.assignedAdvisor = Optional.empty();
        this.currentSurveyMorning = new DynamicSurvey(SurveyType.morning, citizenData);
        this.currentSurveyEvening = new DynamicSurvey(SurveyType.evening, citizenData);
    }


    private String fullName;
    private UUID id;
    private final DynamicSurvey currentSurveyMorning;
    private final DynamicSurvey currentSurveyEvening;
    DynamicSurvey getCurrentSurveyMorning() {return currentSurveyMorning;}
    DynamicSurvey getCurrentSurveyEvening() {return currentSurveyEvening;}

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


public String getLastEntry() {
    if (answeredSurveys == null || answeredSurveys.length == 0) {
        return "Ingen indtastninger";
    }

    return Arrays.stream(answeredSurveys)
                 .map(AnsweredSurvey::getWhenAnswered) // ZonedDateTime
                 .max(ZonedDateTime::compareTo)        // Compare ZonedDateTime
                 .map(ZonedDateTime::toString)         // Convert to string
                 .orElse("Ingen indtastninger");
}


@Override
public String getFullName() {
        return fullName;
}

public String getSeverity() {
    // Placeholder logic; you can replace with actual logic based on surveys
    return "Ukendt"; // or "Moderat"
}

public String getAdvisor() {
    return assignedAdvisor.map(SleepAdvisor::getFullName).orElse("");
}

public void setAdvisor(SleepAdvisor advisor) {
    this.assignedAdvisor = Optional.ofNullable(advisor);
}

public boolean isHighlight() {
    return "Moderat".equalsIgnoreCase(getSeverity());
}

public UUID getId() {
    return id;
}

public CitizenRow getRow() {
    return this.row; // row is the CitizenRow you store internally
}

}
