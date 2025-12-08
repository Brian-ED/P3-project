package com.example.application.model;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.database.ClDiDB.CitizenRow;
import com.example.application.database.ClDiDB.Survey;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;


@VaadinSessionScope
@Component
public class ModelImpl implements Model {
    private Optional<User> user = Optional.empty();
    private Optional<UserType> userType = Optional.empty();
    private final DatabaseControler database;

    @Autowired
    public ModelImpl(DatabaseControler database) { // , String username ?
        // Model.user = Optional.of(database.getUser(username));
        this.database = database;
    }

    public Citizen initAsCitizen(String username) {
        this.userType = Optional.of(UserType.CITIZEN);
        Citizen citizen = database.getCitizenByName(username)
                    .orElseThrow(() -> new IllegalStateException(
                "Citizen does not exist — cannot auto-create here"
            ));
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

    public SleepAdvisor getThisAdvisor(String username) {
        if (!userType.isEmpty()) {
            if (user.isEmpty()) {
                throw new IllegalStateException("userType existed but User did not: " + userType.orElseThrow());
            }
            if (userType.orElseThrow() != UserType.ADVISOR || !(user.orElseThrow() instanceof SleepAdvisor)) {
                throw new IllegalStateException("Tried initializing citizen as advisor");
            }
            return (SleepAdvisor)(user.orElseThrow());
        }

        SleepAdvisor advisor = database
            .getAdvisorByName(username)
            .orElseGet(() ->
                database.newAdvisor(username)
            );

        this.userType = Optional.of(UserType.ADVISOR);
        this.user = Optional.of(advisor);

        return advisor;
    }


    public Optional<UserType> getUserType() {
      return userType;
    }

    private class SaveSurveyListener implements SurveyListener {
		@Override public void currentQuestionChanged(int newIndex) {}
		@Override public void questionAnswered(int index, AnswerPayload payload) {}
        private final DatabaseControler db;

        public SaveSurveyListener(DatabaseControler db) {
            this.db = db;
        }

        @Override
		public void notifySubmitted(Survey survey) {
            db.saveSurvey(survey);
        }
    }

    public DynamicSurvey initDynamicSurvey(SurveyType type, CitizenRow owner) {
        return new DynamicSurvey(type, owner) {{
            addListener(new SaveSurveyListener(database));
        }};
    }

    public Citizen getThisCitizen(String username) {
        if (!userType.isEmpty()) {
            if (user.isEmpty()) {
                throw new IllegalStateException("userType existed but User did not: " + userType.orElseThrow());
            }
            if (userType.orElseThrow() != UserType.CITIZEN || !(user.orElseThrow() instanceof Citizen)) {
                throw new IllegalStateException("Tried initializing advisor as citizen");
            }
            return (Citizen)(user.orElseThrow());
        }

        Citizen citizen = database
            .getCitizenByName(username)
            .orElseGet(() ->
                database.newCitizen(username)
            );

        this.userType = Optional.of(UserType.CITIZEN);
        this.user = Optional.of(citizen);

        return citizen;
    }

}
