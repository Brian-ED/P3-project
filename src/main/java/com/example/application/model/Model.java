package com.example.application.model;

import java.util.Optional;
import java.util.UUID;

public interface Model {
    Citizen initAsCitizen(String userName);
    Citizen createCitizen(String userName);
    SleepAdvisor initAsAdvisor(String userName);
    Optional<Citizen> getCitizenWithID(UUID userID);
    DynamicSurvey initDynamicSurvey(SurveyType type, Citizen owner);
    Optional<User> getUser();
    Optional<Citizen> getCitizen();
    Citizen getThisCitizen(String username);
    SleepAdvisor getThisAdvisor(String username);
    String[] getAllAdvisorNames();
    void setAssignedAdvisorByName(Citizen citizen, String advisorName);
}
