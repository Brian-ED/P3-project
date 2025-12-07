package com.example.application.database.ClDiDB;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class CitizenRow {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    public Long getId() {return id;};

    @Column(nullable = false, columnDefinition = "text")
    private String fullName;
    public String getFullName() {return fullName;};
    public void setFullName(String fullName) {this.fullName = fullName;};

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private AdvisorRow assignedAdvisor;
    public AdvisorRow getAssignedAdvisor() {return assignedAdvisor;};
    public void setAssignedAdvisor(AdvisorRow assignedAdvisor) {this.assignedAdvisor = assignedAdvisor;};

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<AnsweredSurveyMorningRow> morningSurveys = new ArrayList<>();
    public List<AnsweredSurveyMorningRow> getMorningSurveys() {return morningSurveys;};
    public void setMorningSurveys(List<AnsweredSurveyMorningRow> morningSurveys) {this.morningSurveys = morningSurveys;};

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<AnsweredSurveyEveningTableRow> eveningSurveys = new ArrayList<>();
    public List<AnsweredSurveyEveningTableRow> getEveningSurveys() {return eveningSurveys;};
    public void setEveningSurveys(List<AnsweredSurveyEveningTableRow> eveningSurveys) {this.eveningSurveys = eveningSurveys;};
}
