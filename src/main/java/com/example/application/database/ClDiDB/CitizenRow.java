package com.example.application.database.ClDiDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class CitizenRow {
    @Id
    @GeneratedValue
    private UUID id;
    public UUID getId() {return id;};

    @Column(nullable = false, columnDefinition = "text", unique = true)
    private String fullName;
    public String getFullName() {return fullName;};
    public void setFullName(String fullName) {this.fullName = fullName;};

    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private AdvisorRow assignedAdvisor;
    public Optional<AdvisorRow> getAssignedAdvisor() {return Optional.ofNullable(assignedAdvisor);};
    public void setAssignedAdvisor(AdvisorRow assignedAdvisor) {this.assignedAdvisor = assignedAdvisor;};

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SurveyMorningRow> morningSurveys = new ArrayList<>();
    public List<SurveyMorningRow> getMorningSurveys() {return morningSurveys;};
    public void setMorningSurveys(List<SurveyMorningRow> morningSurveys) {this.morningSurveys = morningSurveys;};

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<SurveyEveningRow> eveningSurveys = new ArrayList<>();
    public List<SurveyEveningRow> getEveningSurveys() {return eveningSurveys;};
    public void setEveningSurveys(List<SurveyEveningRow> eveningSurveys) {this.eveningSurveys = eveningSurveys;};
}
