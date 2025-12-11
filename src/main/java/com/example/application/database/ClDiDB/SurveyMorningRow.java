package com.example.application.database.ClDiDB;

import java.time.ZonedDateTime;

import com.example.application.database.ClDiDB.Answers.*;
import com.example.application.database.ClDiDB.Questions.*;
import com.example.application.model.SurveyType;

import jakarta.persistence.*;

@Entity
@Table
public final class SurveyMorningRow implements Survey {

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

    // Embedded answers with unique column names
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "answer0_timestamp")),
        @AttributeOverride(name = "value1", column = @Column(name = "answer0_value1"))
    })
    private YesOrNoElaborateComboboxAnswer internelAnswer0 = new YesOrNoElaborateComboboxAnswer();
    @Transient
    private final YesOrNoElaborateComboboxQuestion answer0 =
        new YesOrNoElaborateComboboxQuestion(
            "Tager du nogen gange sovemedicin eller melatonin piller?",
            "Hvad tager du?",
            new String[]{"Sovemedicin", "Melatonin"},
            internelAnswer0
        );
    public YesOrNoElaborateComboboxQuestion getAnswer0() { return answer0; }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "answer1_timestamp")),
        @AttributeOverride(name = "value1", column = @Column(name = "answer1_value1"))
    })
    private ComboBoxAnswer internelAnswer1 = new ComboBoxAnswer();
    @Transient
    private final ComboBoxQuestion answer1 =
        new ComboBoxQuestion(
            "Hvad foretog du dig de sidste par timer inden du gik i seng?",
            new String[]{"Ting", "Sager"},
            internelAnswer1
        );
    public ComboBoxQuestion getAnswer1() { return answer1; }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "answer2_timestamp")),
        @AttributeOverride(name = "value1", column = @Column(name = "answer2_value1"))
    })
    private RollAnswer internelAnswer2 = new RollAnswer();
    @Transient
    private final RollQuestion answer2 =
        new RollQuestion("I går gik jeg i seng klokken:", internelAnswer2);
    public RollQuestion getAnswer2() { return answer2; }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "answer3_timestamp")),
        @AttributeOverride(name = "value1", column = @Column(name = "answer3_value1"))
    })
    private RollAnswer internelAnswer3 = new RollAnswer();
    @Transient
    private final RollQuestion answer3 =
        new RollQuestion("Jeg slukkede lyset klokken:", internelAnswer3);
    public RollQuestion getAnswer3() { return answer3; }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "answer4_timestamp")),
        @AttributeOverride(name = "value1", column = @Column(name = "answer4_value1"))
    })
    private DurationAnswer internelAnswer4 = new DurationAnswer();
    @Transient
    private final DurationQuestion answer4 =
        new DurationQuestion("Efter jeg slukkede lyset, sov jeg ca. efter:", internelAnswer4);
    public DurationQuestion getAnswer4() { return answer4; }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "answer5_timestamp")),
        @AttributeOverride(name = "value1", column = @Column(name = "answer5_value1"))
    })
    private ComboBoxAnswer internelAnswer5 = new ComboBoxAnswer();
    @Transient
    private final ComboBoxQuestion answer5 =
        new ComboBoxQuestion("Jeg vågnede cirka x gange i løbet af natten:", new String[]{"1", "2", "3"}, internelAnswer5);
    public ComboBoxQuestion getAnswer5() { return answer5; }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "answer6_timestamp")),
        @AttributeOverride(name = "value1", column = @Column(name = "answer6_value1"))
    })
    private DurationAnswer internelAnswer6 = new DurationAnswer();
    @Transient
    private final DurationQuestion answer6 =
        new DurationQuestion("Jeg var sammenlagt vågen i cirka x minutter i løbet af natten", internelAnswer6);
    public DurationQuestion getAnswer6() { return answer6; }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "answer7_timestamp")),
        @AttributeOverride(name = "value1", column = @Column(name = "answer7_value1"))
    })
    private RollAnswer internelAnswer7 = new RollAnswer();
    @Transient
    private final RollQuestion answer7 =
        new RollQuestion("I morges vågnede jeg klokken:", internelAnswer7);
    public RollQuestion getAnswer7() { return answer7; }

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "answer8_timestamp")),
        @AttributeOverride(name = "value1", column = @Column(name = "answer8_value1"))
    })
    private RollAnswer internelAnswer8 = new RollAnswer();
    @Transient
    private final RollQuestion answer8 =
        new RollQuestion("Jeg stod op klokken:", internelAnswer8);
    public RollQuestion getAnswer8() { return answer8; }

    @Transient
    private final GenericQuestion<?>[] allAnswers = new GenericQuestion<?>[]{
        answer0, answer1, answer2, answer3, answer4, answer5, answer6, answer7, answer8
    };

    public GenericQuestion<?>[] getAnswers() { return allAnswers; }

    @Override
    public SurveyType getType() { return SurveyType.morning; }
}
