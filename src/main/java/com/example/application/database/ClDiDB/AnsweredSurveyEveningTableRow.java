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
    private Boolean answer0YesNo;
    public Boolean getAnswer0YesNo() {return answer0YesNo;};
    public void setAnswer0YesNo(Boolean answer0YesNo) {this.answer0YesNo = answer0YesNo;};

    @Column(nullable = false)
    ZonedDateTime answer0Timestamp1;
    public ZonedDateTime getAnswer0Timestamp1() {return answer0Timestamp1;};
    public void setAnswer0Timestamp1(ZonedDateTime answer0Timestamp1) {this.answer0Timestamp1 = answer0Timestamp1;};

    @Column(nullable = false)
    ZonedDateTime answer0Timestamp2;
    public ZonedDateTime getAnswer0Timestamp2() {return answer0Timestamp2;};
    public void setAnswer0Timestamp2(ZonedDateTime answer0Timestamp2) {this.answer0Timestamp2 = answer0Timestamp2;};

    private Boolean answer1YesNo;
    public Boolean getAnswer1YesNo() {return answer1YesNo;};
    public void setAnswer1YesNo(Boolean answer1YesNo) {this.answer1YesNo = answer1YesNo;};

    @Column(nullable = false)
    ZonedDateTime answer1Timestamp;
    public ZonedDateTime getAnswer1Timestamp() {return answer1Timestamp;};
    public void setAnswer1Timestamp(ZonedDateTime answer1Timestamp) {this.answer1Timestamp = answer1Timestamp;};

    private Boolean answer2YesNo;
    public Boolean getAnswer2YesNo() {return answer2YesNo;};
    public void setAnswer2YesNo(Boolean answer2YesNo) {this.answer2YesNo = answer2YesNo;};

    private Short answer2WhichIsSelected;
    public Short getAnswer2WhichIsSelected() {return answer2WhichIsSelected;};
    public void setAnswer2WhichIsSelected(Short answer2WhichIsSelected) {this.answer2WhichIsSelected = answer2WhichIsSelected;};

    @Column(nullable = false)
    private ZonedDateTime answer2Timestamp;
    public ZonedDateTime getAnswer2Timestamp() {return answer2Timestamp;};
    public void setAnswer2Timestamp(ZonedDateTime answer2Timestamp) {this.answer2Timestamp = answer2Timestamp;};

    private Boolean answer3YesNo;
    public Boolean getAnswer3YesNo() {return answer3YesNo;};
    public void setAnswer3YesNo(Boolean answer3YesNo) {this.answer3YesNo = answer3YesNo;};

    @Column(nullable = false)
    ZonedDateTime answer3Timestamp;
    public ZonedDateTime getAnswer3Timestamp() {return answer3Timestamp;};
    public void setAnswer3Timestamp(ZonedDateTime answer3Timestamp) {this.answer3Timestamp = answer3Timestamp;};

    private Short answer3WhichIsSelected;
    public Short getAnswer3WhichIsSelected() {return answer3WhichIsSelected;};
    public void setAnswer3WhichIsSelected(Short answer3WhichIsSelected) {this.answer3WhichIsSelected = answer3WhichIsSelected;};

    private Boolean answer4YesNo;
    public Boolean getAnswer4YesNo() {return answer4YesNo;};
    public void setAnswer4YesNo(Boolean answer4YesNo) {this.answer4YesNo = answer4YesNo;};

    @Column(nullable = false)
    ZonedDateTime answer4Timestamp;
    public ZonedDateTime getAnswer4Timestamp() {return answer4Timestamp;};
    public void setAnswer4Timestamp(ZonedDateTime answer4Timestamp) {this.answer4Timestamp = answer4Timestamp;};
}
