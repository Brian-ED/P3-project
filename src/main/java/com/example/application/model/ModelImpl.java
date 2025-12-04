package com.example.application.model;

import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
//@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS) // Gives one instance per user session
public class ModelImpl implements Model {

    private Optional<User> user = Optional.empty();
    private Optional<UserType> userType = Optional.empty();
    private final DatabaseControler database;

    ModelImpl(DatabaseControler database) { // , String username ?
        // Model.user = Optional.of(database.getUser(username));
        this.userType = Optional.of(UserType.CITIZEN);
        this.database = database;
    }

    public Citizen initCitizen(String UserName) {
        this.userType = Optional.of(UserType.CITIZEN);
        Optional<Citizen> x = database.getCitizenByName(UserName);
        Citizen citizen;
        if (x.isEmpty()) {
            citizen = new Citizen(UserName, null, new AnsweredSurvey[0], null);
        } else {
            citizen = x.orElseThrow();
        }
        this.user = Optional.of(citizen);
        return citizen;
    }

    public Optional<User> getUser() {
        return user;
    }

    public Optional<Citizen> getCitizen() {
        if (user.isEmpty()) {
            return Optional.empty();
        }
        if (userType.isEmpty()) {
            return Optional.empty();
        }
        User existingUser = user.orElseThrow();
        UserType existingUserType = userType.orElseThrow();
        if (existingUserType != UserType.CITIZEN) {
            return Optional.empty();
        };
        return Optional.of((Citizen)existingUser);
    }

    public Optional<SleepAdvisor> getAdvisor() {
        if (user.isEmpty()) {
            return Optional.empty();
        }
        User existingUser = user.orElseThrow();
        if (existingUser.getUserType() != UserType.ADVISOR) {
            return Optional.empty();
        };
        return Optional.of((SleepAdvisor)existingUser);
    }


    public Optional<UserType> getUserType() {
      return userType;
    }
}
