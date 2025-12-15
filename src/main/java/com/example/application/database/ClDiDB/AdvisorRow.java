package com.example.application.database.ClDiDB;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class AdvisorRow {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, columnDefinition = "text", unique = true)
    private String fullName;
    public String getFullName() {return fullName;};
    public void setFullName(String fullName) {this.fullName = fullName;};
}
