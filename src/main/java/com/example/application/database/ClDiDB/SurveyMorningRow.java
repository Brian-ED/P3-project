package com.example.application.database.ClDiDB;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.example.application.database.ClDiDB.Answers.ComboBoxAnswer;
import com.example.application.database.ClDiDB.Answers.DurationAnswer;
import com.example.application.database.ClDiDB.Answers.RollAnswer;
import com.example.application.database.ClDiDB.Answers.TextFieldAnswer;
import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateComboboxAnswer;
import com.example.application.database.ClDiDB.Questions.ComboBoxQuestion;
import com.example.application.database.ClDiDB.Questions.GenericQuestion;
import com.example.application.database.ClDiDB.Questions.RollQuestion;
import com.example.application.database.ClDiDB.Questions.RollQuestionShort;
import com.example.application.database.ClDiDB.Questions.TextFieldQuestion;
import com.example.application.database.ClDiDB.Questions.YesOrNoElaborateComboboxQuestion;
import com.example.application.model.SurveyType;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table
public final class SurveyMorningRow implements Survey {
    @Id
    @GeneratedValue
    private UUID id;
    public UUID getID() {
        return id;
    }

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CitizenRow owner;
    public CitizenRow getOwner() {return owner;};
    public void setOwner(CitizenRow owner) {this.owner = owner;};

    @Column(nullable = false)
    private ZonedDateTime whenAnswered;
    public ZonedDateTime getWhenAnswered() {return whenAnswered;};
    public void setWhenAnswered(ZonedDateTime whenAnswered) {this.whenAnswered = whenAnswered;};
    public DurationAnswer getAnswer4Value() {
        return internelAnswer4;
    }
    //---------
    // Answers:
    @Embedded private YesOrNoElaborateComboboxAnswer internelAnswer0 = new YesOrNoElaborateComboboxAnswer(); @Transient final private YesOrNoElaborateComboboxQuestion answer0 = new YesOrNoElaborateComboboxQuestion("Tager du nogen gange sovemedicin eller melatonin piller?"     , "Hvad tager du?", new String[]{"Sovemedicin", "Melatonin"}, internelAnswer0); public YesOrNoElaborateComboboxQuestion getAnswer0() {return answer0;}
    @Embedded private TextFieldAnswer                 internelAnswer1 = new TextFieldAnswer                (); @Transient final private TextFieldQuestion                 answer1 = new TextFieldQuestion                ("Hvad foretog du dig de sidste par timer inden du gik i seng?"                               , internelAnswer1); public TextFieldQuestion                 getAnswer1() {return answer1;}
    @Embedded private RollAnswer                     internelAnswer2 = new RollAnswer                    (); @Transient final private RollQuestion                 answer2 = new RollQuestion                ("I går gik jeg i seng klokken:"                                                                                                , internelAnswer2); public RollQuestion                 getAnswer2() {return answer2;}
    @Embedded private RollAnswer                     internelAnswer3 = new RollAnswer                    (); @Transient final private RollQuestion                     answer3 = new RollQuestion                    ("Jeg slukkede lyset klokken:"                                                                                                  , internelAnswer3); public RollQuestion                     getAnswer3() {return answer3;}
    @Embedded private DurationAnswer                 internelAnswer4 = new DurationAnswer                (); @Transient final private RollQuestionShort                 answer4 = new RollQuestionShort                ("Efter jeg slukkede lyset, sov jeg ca. efter:"                                                                                 , internelAnswer4); public RollQuestionShort                 getAnswer4() {return answer4;}
    @Embedded private ComboBoxAnswer                 internelAnswer5 = new ComboBoxAnswer                (); @Transient final private ComboBoxQuestion                 answer5 = new ComboBoxQuestion                ("Jeg vågnede cirka x gange i løbet af natten:"                 , new String[] {"1", "2", "3"}                                  , internelAnswer5); public ComboBoxQuestion                 getAnswer5() {return answer5;}
    @Embedded private DurationAnswer                 internelAnswer6 = new DurationAnswer                (); @Transient final private RollQuestionShort                 answer6 = new RollQuestionShort                ("Jeg var sammenlagt vågen i cirka x minutter i løbet af natten"                                                                , internelAnswer6); public RollQuestionShort                 getAnswer6() {return answer6;}
    @Embedded private RollAnswer                     internelAnswer7 = new RollAnswer                    (); @Transient final private RollQuestion                     answer7 = new RollQuestion                    ("I morges vågnede jeg klokken:"                                                                                                , internelAnswer7); public RollQuestion                     getAnswer7() {return answer7;}
    @Embedded private RollAnswer                     internelAnswer8 = new RollAnswer                    (); @Transient final private RollQuestion                     answer8 = new RollQuestion                    ("Jeg stod op klokken:"                                                                                                         , internelAnswer8); public RollQuestion                     getAnswer8() {return answer8;}

    @Transient final private GenericQuestion<?>[] allAnswers = new GenericQuestion[] {
        answer0, answer1, answer2,
        answer3, answer4, answer5,
        answer6, answer7, answer8,
    };

    public GenericQuestion<?>[] getAnswers() {
        return allAnswers;
    }
	@Override
	public SurveyType getType() {
        return SurveyType.morning;
	}
}
