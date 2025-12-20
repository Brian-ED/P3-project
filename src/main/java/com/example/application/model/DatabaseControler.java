package com.example.application.model;

import java.util.Optional;
import java.util.UUID;

import com.example.application.database.ClDiDB.CitizenRow;
import com.example.application.database.ClDiDB.Survey;

public interface DatabaseControler {
    Citizen[] searchCitizensByName(String username);
    Optional<Citizen> getCitizenByName(String username);
    Optional<SleepAdvisor> getAdvisorByName(String username);
    Citizen newCitizen(String username);
    SleepAdvisor newAdvisor(String username);
    void saveSurvey(Survey survey); // TODO this file should maybe not have Survey, since it's from the DB
    SleepAdvisor[] getAllAdvisors();
    Optional<Citizen> getCitizenById(UUID id);
    Optional<CitizenRow> getCitizenRowById(UUID id); // TODO this was used for a quickfix. Ideally should be removed and fixed properly
}
