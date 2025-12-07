package com.example.application.model;

import java.util.Optional;

public interface Model {
    Citizen initCitizen(String UserName);
    Optional<User> getUser();
    Optional<Citizen> getCitizen();
    Optional<SleepAdvisor> getAdvisor();
    Optional<UserType> getUserType();
}
