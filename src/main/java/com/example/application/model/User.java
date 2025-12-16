package com.example.application.model;

import java.util.UUID;

public interface User {
    UUID getID();
    String getFullName();
    UserType getUserType();
}
