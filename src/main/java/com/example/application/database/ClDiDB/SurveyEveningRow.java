package com.example.application.database.ClDiDB;

import java.time.ZonedDateTime;

import com.example.application.database.ClDiDB.Answers.*;
import com.example.application.database.ClDiDB.Questions.*;
import com.example.application.model.SurveyType;

import jakarta.persistence.*;

@Entity
@Table
public final class SurveyEveningRow implements Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CitizenRow owner;
    public CitizenRow getOwner() { return owner; }
    public void setOwner(CitizenRow owner) { this.owner = owner; }

    @Column(nullable = false)
    private ZonedDateTime whenAnswered;
    public ZonedDateTime getWhenAnswered() { return whenAnswered; }
    public void setWhenAnswered(ZonedDateTime whenAnswered) { this.whenAnswered = whenAnswered; }

    // ---------
    // Embedded Answers with AttributeOverrides
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "answer0_timestamp")),
        @AttributeOverride(name = "value1", column = @Column(name = "answer0_value1")),
        @AttributeOverride(name = "value2", column = @Column(name = "answer0_value2"))
    })
    private YesOrNoElaborateRollRollAnswer internelAnswer0 = new YesOrNoElaborateRollRollAnswer();
    @Transient
    private final YesOrNoElaborateRollRollQuestion answer0 =
        new YesOrNoElaborateRollRollQuestion(
            "Har du været fysisk aktiv i dag?",
            "Hvor mange minutter?",
            "Hvornår på dagen?",
            internelAnswer0
        );
    public YesOrNoElaborateRollRollQuestion getAnswer0() { return answer0; }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "answer1_timestamp")),
        @AttributeOverride(name = "value1", column = @Column(name = "answer1_value1"))
    })
    private YesOrNoElaborateRollAnswer internelAnswer1 = new YesOrNoElaborateRollAnswer();
    @Transient
    private final YesOrNoElaborateRollQuestion answer1 =
        new YesOrNoElaborateRollQuestion(
            "Har du været ude i dagslys?",
            "Hvornår på dagen?",
            internelAnswer1
        );
    public YesOrNoElaborateRollQuestion getAnswer1() { return answer1; }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "answer2_timestamp")),
        @AttributeOverride(name = "value1", column = @Column(name = "answer2_value1")),
        @AttributeOverride(name = "value2", column = @Column(name = "answer2_value2"))
    })
    private YesOrNoElaborateComboboxRollAnswer internelAnswer2 = new YesOrNoElaborateComboboxRollAnswer();
    @Transient
    private final YesOrNoElaborateComboboxRollQuestion answer2 =
        new YesOrNoElaborateComboboxRollQuestion(
            "Har du drukket koffeinholdige drikke i dag?",
            "Hvilke drikke (flere kan vælges)",
            new String[]{"Monster", "Kaffe", "Sodavand"},
            "Hvornår på dagen indtager du den sidste drik?",
            internelAnswer2
        );
    public YesOrNoElaborateComboboxRollQuestion getAnswer2() { return answer2; }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "answer3_timestamp")),
        @AttributeOverride(name = "value1", column = @Column(name = "answer3_value1")),
        @AttributeOverride(name = "value2", column = @Column(name = "answer3_value2"))
    })
    private YesOrNoElaborateRollComboboxAnswer internelAnswer3 = new YesOrNoElaborateRollComboboxAnswer();
    @Transient
    private final YesOrNoElaborateRollComboboxQuestion answer3 =
        new YesOrNoElaborateRollComboboxQuestion(
            "Har du drukket alkohol?",
            "Hvornår på dagen drak du den sidste genstand?",
            "Hvor mange genstande har du ca. drukket i løbet af dagen?",
            new String[]{"1", "2", "3"},
            internelAnswer3
        );
    public YesOrNoElaborateRollComboboxQuestion getAnswer3() { return answer3; }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "answer4_timestamp")),
        @AttributeOverride(name = "value1", column = @Column(name = "answer4_value1"))
    })
    private YesOrNoElaborateRollAnswer internelAnswer4 = new YesOrNoElaborateRollAnswer();
    @Transient
    private final YesOrNoElaborateRollQuestion answer4 =
        new YesOrNoElaborateRollQuestion(
            "Har du sovet i løbet af dagen?",
            "Hvornår på dagen?",
            internelAnswer4
        );
    public YesOrNoElaborateRollQuestion getAnswer4() { return answer4; }

    @Transient
    private final GenericQuestion<?>[] allAnswers = new GenericQuestion<?>[] {
        answer0, answer1, answer2, answer3, answer4
    };

    public GenericQuestion<?>[] getAnswers() { return allAnswers; }

    @Override
    public SurveyType getType() { return SurveyType.evening; }
}
