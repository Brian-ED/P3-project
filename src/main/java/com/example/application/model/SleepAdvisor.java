package com.example.application.model;

import java.util.Optional;

import com.example.application.database.ClDiDB.AdvisorRow;

public class SleepAdvisor implements User {
    private String fullName;
    private AdvisorRow row;

    public SleepAdvisor(AdvisorRow row) {
        this.fullName = row.getFullName();
        this.row = row;
    }



    @Override
    public String getFullName() {
        return fullName;
    }

    public AdvisorRow getRow() {
        return this.row;
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
