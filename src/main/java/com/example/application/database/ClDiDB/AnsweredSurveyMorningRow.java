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
public class AnsweredSurveyMorningRow {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private ZonedDateTime whenAnswered;
    public ZonedDateTime getWhenAnswered() {return whenAnswered;};
    public void setWhenAnswered(ZonedDateTime whenAnswered) {this.whenAnswered = whenAnswered;};

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CitizenRow owner;
    public CitizenRow getOwner() {return owner;};
    public void setOwner(CitizenRow owner) {this.owner = owner;};

    //---------
    // Answers:

    // AskMoreIfYesQuestion("Tager du nogen gange sovemedicin eller melatonin piller",
    //     ComboBoxQuestion("Hvad tager du?", "Sovemedicin", "Melatonin")),
    @Column(nullable = false)
    private Boolean answer0YesNo;
    public Boolean getAnswer0YesNo() {return answer0YesNo;};
    public void setAnswer0YesNo(Boolean answer0YesNo) {this.answer0YesNo = answer0YesNo;};

    @Column(nullable = false)
    private Short answer0Elaborate;
    public Short getAnswer0Elaborate() {return answer0Elaborate;};
    public void setAnswer0Elaborate(Short answer0Elaborate) {this.answer0Elaborate = answer0Elaborate;};

    // ComboBoxQuestion("Hvad foretog du dig de sidste par timer inden du gik i seng?", "Ting", "Sager"),
    @Column(nullable = false)
    private Short answer1WhichIsSelected;
    public Short getAnswer1WhichIsSelected() {return answer1WhichIsSelected;};
    public void setAnswer1WhichIsSelected(Short answer1WhichIsSelected) {this.answer1WhichIsSelected = answer1WhichIsSelected;};

    // RollQuestion("I går gik jeg i seng klokken:"),
    @Column(nullable = false)
    ZonedDateTime answer2Roll;
    public ZonedDateTime getAnswer2Roll() {return answer2Roll;};
    public void setAnswer2Roll(ZonedDateTime answer2Roll) {this.answer2Roll = answer2Roll;};

    // RollQuestion("Jeg slukkede lyset klokken:"),
    @Column(nullable = false)
    ZonedDateTime answer3Roll;
    public ZonedDateTime getAnswer3Roll() {return answer3Roll;};
    public void setAnswer3Roll(ZonedDateTime answer3Roll) {this.answer3Roll = answer3Roll;};

    // RollQuestion("Efter jeg slukkede lyset, sov jeg ca. efter:"),
    @Column(nullable = false)
    Integer answer4Minutes;
    public Integer getAnswer4Minutes() {return answer4Minutes;}
    public void setAnswer4Minutes(Integer answer4Minutes) {this.answer4Minutes = answer4Minutes;}

    // ComboBoxQuestion("Jeg vågnede cirka x gange i løbet af natten:",
    //     "1", "2", "3"),
    @Column(nullable = false)
    private Short answer5WhichIsSelected;
    public Short getAnswer5WhichIsSelected() {return answer5WhichIsSelected;};
    public void setAnswer5WhichIsSelected(Short answer5WhichIsSelected) {this.answer5WhichIsSelected = answer5WhichIsSelected;};

    // RollQuestion("Jeg var sammenlagt vågen i cirka x minutter i løbet af natten"),
    @Column(nullable = false)
    Integer answer6Minutes;
    public Integer getAnswer6Minutes() {return answer6Minutes;}
    public void setAnswer6Minutes(Integer answer6Minutes) {this.answer6Minutes = answer6Minutes;}

    // RollQuestion("I morges vågnede jeg klokken:"),
    @Column(nullable = false)
    ZonedDateTime answer7Roll;
    public ZonedDateTime getAnswer7Roll() {return answer7Roll;};
    public void setAnswer7Roll(ZonedDateTime answer7Roll) {this.answer7Roll = answer7Roll;};

    // RollQuestion("Og jeg stod op klokken:")
    @Column(nullable = false)
    ZonedDateTime answer8Roll;
    public ZonedDateTime getAnswer8Roll() {return answer8Roll;}
    public void setAnswer8Roll(ZonedDateTime answer8Roll) {this.answer8Roll = answer8Roll;}
}
