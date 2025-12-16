package com.example.application.model;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.database.ClDiDB.CitizenRow;
import com.example.application.database.ClDiDB.Survey;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;


@VaadinSessionScope
@Component
public class ModelImpl implements Model {
    private Optional<User> user = Optional.empty();
    private final DatabaseControler database;

    @Autowired
    public ModelImpl(DatabaseControler database) { // , String username ?
        // Model.user = Optional.of(database.getUser(username));
        this.database = database;
    }

    public Optional<Citizen> initAsCitizenWithID(UUID UserID) {
        Optional<Citizen> citizen = database.getCitizenById(UserID);
        this.user = Optional.ofNullable(citizen.orElse(null)); // Java type inference couldn't infer the Optional<Citizen> to Optional<User>
        return citizen;
    }

    public Citizen initAsCitizen(String username) {
        final Citizen citizen;
        Optional<Citizen> maybeC = database.getCitizenByName(username);
        if (maybeC.isEmpty()) {
            citizen = database.newCitizen(username);
        } else {
            citizen = maybeC.orElseThrow();
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
        User existingUser = user.orElseThrow();
        if (existingUser.getUserType() != UserType.CITIZEN) {
            return Optional.empty();
        };
        return Optional.of((Citizen)existingUser);
    }

    public SleepAdvisor getThisAdvisor(String username) {
        if (!user.isEmpty()) {
            User userR = user.orElseThrow();
            if (userR.getUserType() != UserType.ADVISOR || !(userR instanceof SleepAdvisor)) {
                throw new IllegalStateException("Tried initializing citizen as advisor"); // If you as an admin hit this error, you likely navigated to a citizen site first.
            }
            return (SleepAdvisor)userR;
        }

        SleepAdvisor advisor = database
            .getAdvisorByName(username)
            .orElseGet(() ->
                database.newAdvisor(username)
            );

        this.user = Optional.of(advisor);

        return advisor;
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

    @Override
    public String[] getAllAdvisorNames() {
        SleepAdvisor[] advisors = database.getAllAdvisors();
        String[] advisorNames = new String[advisors.length];
        for (int i=0; i < advisors.length; i++) {
            advisorNames[i] = advisors[i].getFullName();
        }
        return advisorNames;
    }

    public DynamicSurvey initDynamicSurvey(SurveyType type, CitizenRow owner) {
        return new DynamicSurvey(type, owner) {{
            addListener(new SaveSurveyListener(database));
        }};
    }

    public Citizen getThisCitizen(String username) {
        if (!user.isEmpty()) {
            User userR = user.orElseThrow();
            if (userR.getUserType() != UserType.CITIZEN || !(userR instanceof Citizen)) {
                throw new IllegalStateException("Tried initializing advisor as citizen");
            }
            return (Citizen)(user.orElseThrow());
        }

        Citizen citizen = database
            .getCitizenByName(username)
            .orElseGet(() ->
                database.newCitizen(username)
            );

        this.user = Optional.of(citizen);

        return citizen;
    }

	@Override
	public void setAssignedAdvisorByName(Citizen citizen, String advisorName) {
        citizen.setAssignedAdvisor(database.getAdvisorByName(advisorName));
	}

}
