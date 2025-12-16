package com.example.application.model;

import java.util.UUID;

public class SleepAdvisor implements User {

    private final String fullName;
    private final UUID id;

    public SleepAdvisor(UUID id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public UUID getID() {
        return id;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

	@Override
	public UserType getUserType() {
		return UserType.ADVISOR;
	}
}
