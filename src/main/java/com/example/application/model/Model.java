package com.example.application.model;

import java.util.Optional;

import com.example.application.database.ClDiDB.CitizenRow;

public interface Model {
    Citizen initAsCitizen(String UserName);
    DynamicSurvey initDynamicSurvey(SurveyType type, CitizenRow owner);
    Optional<User> getUser();
    Optional<Citizen> getCitizen();
    Optional<UserType> getUserType();
    Citizen getThisCitizen(String username);
    SleepAdvisor getThisAdvisor(String username);
}
