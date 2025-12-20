package com.example.application.model;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.database.ClDiDB.CitizenRow;
import com.example.application.database.ClDiDB.Survey;
import com.example.application.database.ClDiDB.SurveyEveningRow;
import com.example.application.database.ClDiDB.SurveyMorningRow;
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

    public Optional<Citizen> getCitizenWithID(UUID UserID) {
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


    public SleepAdvisor initAsAdvisor(String username) {
        final SleepAdvisor advisor;
        Optional<SleepAdvisor> maybeA = database.getAdvisorByName(username);
        if (maybeA.isEmpty()) {
            advisor = database.newAdvisor(username);
        } else {
            advisor = maybeA.orElseThrow();
        }
        this.user = Optional.of(advisor);
        return advisor;
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

    @Override
    public String[] getAllAdvisorNames() {
        SleepAdvisor[] advisors = database.getAllAdvisors();
        String[] advisorNames = new String[advisors.length];
        for (int i=0; i < advisors.length; i++) {
            advisorNames[i] = advisors[i].getFullName();
        }
        return advisorNames;
    }

    @Override
    public DynamicSurvey initDynamicSurvey(SurveyType type, Citizen owner) {
        Survey surveyRow = switch (type) {
            case SurveyType.morning -> new SurveyMorningRow();
            case SurveyType.evening -> new SurveyEveningRow();
        };

        CitizenRow ownerRow = database.getCitizenRowById(owner.getID()).orElseThrow();
        surveyRow.setOwner(ownerRow);

        return new DynamicSurvey(
            type,
            surveyRow.getAnswers(),
            database.getCitizenById(owner.getID()).orElseThrow(),
            () -> {
                ZonedDateTime now = ZonedDateTime.now();
                surveyRow.setWhenAnswered(now);
                System.out.println(surveyRow);
                System.out.println(surveyRow.getAnswers()[0].getAnswer().toPayload());
                database.saveSurvey(surveyRow);
                AnsweredSurvey finishedSurvey = switch (type) {
                    case SurveyType.morning -> new AnsweredMorningSurvey(surveyRow.getID(), surveyRow.getAnswers(), now);
                    case SurveyType.evening -> new AnsweredEveningSurvey(surveyRow.getID(), surveyRow.getAnswers(), now);
                };
                return finishedSurvey;
            }
        );
    }

    public Citizen createCitizen(String username) {
        return database
            .getCitizenByName(username)
            .orElseGet(() ->
                database.newCitizen(username)
            );
    }

    public Citizen getThisCitizen(String username) {
        if (!user.isEmpty()) {
            User userR = user.orElseThrow();
            if (userR.getUserType() != UserType.CITIZEN || !(userR instanceof Citizen)) {
                throw new IllegalStateException("Tried initializing advisor as citizen");
            }
            return (Citizen)userR;
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
