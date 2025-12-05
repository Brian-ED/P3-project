package com.example.application.database.ClDiDB;

import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class AnsweredSurveyEveningTableRow {
    AnsweredSurveyEveningTableRow() {};

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CitizenRow owner;
    public CitizenRow getOwner() {return owner;};
    public void setOwner(CitizenRow owner) {this.owner = owner;};

    @Column(nullable = false)
    private ZonedDateTime whenAnswered;
    public ZonedDateTime getWhenAnswered() {return whenAnswered;};
    public void setWhenAnswered(ZonedDateTime whenAnswered) {this.whenAnswered = whenAnswered;};

    //---------
    // Answers:

    @Column(nullable = false)
    private Boolean answer1YesNo;
    public Boolean getAnswer1YesNo() {return answer1YesNo;};
    public void setAnswer1YesNo(Boolean answer1YesNo) {this.answer1YesNo = answer1YesNo;};

    @Column(nullable = false)
    private Integer answer1Elaborate;
    public Integer getAnswer1Elaborate() {return answer1Elaborate;};
    public void setAnswer1Elaborate(Integer answer1Elaborate) {this.answer1Elaborate = answer1Elaborate;};
}
