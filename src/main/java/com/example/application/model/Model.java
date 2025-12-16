package com.example.application.model;

import java.util.Optional;
import java.util.UUID;

import com.example.application.database.ClDiDB.CitizenRow;

public interface Model {
    Citizen initAsCitizen(String UserName);
    Optional<Citizen> initAsCitizenWithID(UUID UserID);
    DynamicSurvey initDynamicSurvey(SurveyType type, CitizenRow owner);
    Optional<User> getUser();
    Optional<Citizen> getCitizen();
    Citizen getThisCitizen(String username);
    SleepAdvisor getThisAdvisor(String username);
    String[] getAllAdvisorNames();
    void setAssignedAdvisorByName(Citizen citizen, String advisorName);
}
