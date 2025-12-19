package com.example.application.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Citizen implements User {

    public static class CitizenListenerToken {}
    private final Map<CitizenListenerToken, Runnable> listeners = new HashMap<>();

    public CitizenListenerToken addUpdateListener(Runnable listener) {
        CitizenListenerToken t = new CitizenListenerToken();
        listeners.put(t, listener);
        return t;
    }

    public void removeUpdateListener(CitizenListenerToken token) {
        listeners.remove(token);
    }

    private void sendUpdateNotification() {
        for (var listener : listeners.values()) {
            listener.run();
        }
    }

    public Citizen(UUID id, String fullName, List<AnsweredSurvey> answeredSurveys, Optional<SleepAdvisor> assignedAdvisor) {
        this.fullName = fullName;
        this.id = id;
        this.answeredSurveys = answeredSurveys;
        this.assignedAdvisor = Optional.empty();
    }

    private final String fullName;
    private final UUID id;

    private Optional<SleepAdvisor> assignedAdvisor;
    private List<AnsweredSurvey> answeredSurveys;

    public void setAssignedAdvisor(Optional<SleepAdvisor> assignedAdvisor) {
        this.assignedAdvisor = assignedAdvisor;
        sendUpdateNotification();
    }
    public void setAssignedAdvisor(SleepAdvisor assignedAdvisor) {
        this.assignedAdvisor = Optional.of(assignedAdvisor);
        sendUpdateNotification();
    }
    public void clearAssignedAdvisor() {
        this.assignedAdvisor = Optional.empty();
        sendUpdateNotification();
    }

    public Optional<SleepAdvisor> getAssignedAdvisor() {
        return this.assignedAdvisor;
    }
    @Override
	public UserType getUserType() {
		return UserType.CITIZEN;
	}
    public List<AnsweredSurvey> getSurveys() {
        return this.answeredSurveys;
    };

    public void submitSurvey(AnsweredSurvey survey) {
        answeredSurveys.add(survey);
    };

    @Override
    public String getFullName() {
        return fullName;
    }

    public UUID getID() {
        return id;
    }
}
