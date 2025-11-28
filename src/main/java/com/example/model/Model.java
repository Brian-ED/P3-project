package com.example.model;

import java.util.Optional;

public class Model {

    private static Optional<User> user = Optional.empty();
    private static Optional<UserType> userType = Optional.empty();

    public static void InitModel(DatabaseControler database, Citizen citizen) {
        Model.user = Optional.of(citizen);
        Model.userType = Optional.of(UserType.CITIZEN);
    }

    public void InitModel(DatabaseControler database, SleepAdvisor advisor) {
        Model.user = Optional.of(advisor);
        Model.userType = Optional.of(UserType.ADVISOR);
    }

    public static Optional<User> getUser() {
      return user;
    }

    public static Optional<UserType> getUserType() {
      return userType;
    }
}
