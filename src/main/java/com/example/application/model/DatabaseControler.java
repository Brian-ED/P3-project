package com.example.application.model;

import java.util.Optional;

public interface DatabaseControler {
    Citizen[] searchCitizensByName(String username);
    Optional<Citizen> getCitizenByName(String username);
    Citizen newCitizen(String username);
}
