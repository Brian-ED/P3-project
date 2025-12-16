package com.example.application.model;

import java.util.Optional;
import java.util.UUID;

import com.example.application.database.ClDiDB.CitizenRow;

public interface Model {
    Citizen initAsCitizen(String UserName);
    SleepAdvisor initAsAdvisor(String UserName);
    Optional<Citizen> getCitizenWithID(UUID UserID);
    DynamicSurvey initDynamicSurvey(SurveyType type, CitizenRow owner);
    Optional<User> getUser();
    Optional<Citizen> getCitizen();
    Citizen getThisCitizen(String username);
    SleepAdvisor getThisAdvisor(String username);
    String[] getAllAdvisorNames();
    void setAssignedAdvisorByName(Citizen citizen, String advisorName);
}
