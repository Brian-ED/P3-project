package com.example.application.model;

import java.util.Optional;

public class SleepAdvisor implements User {
    private String fullName;

    public SleepAdvisor(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    /* TODO */ public void searchCitizensList() {}
    /* TODO */ public void showOnlyYourCitizens() {}
    /* TODO */ public void sortCitizensByAdvisor() {}
    /* TODO */ public void sortCitizensByName() {}
    public void changeAssignedAdvisor(Citizen citizen) {
        citizen.changeAssignedAdvisor(Optional.of(this));
    }
    /* TODO */ public void seeCitizenData() {}

	@Override
	public UserType getUserType() {
		return UserType.ADVISOR;
	}
}
