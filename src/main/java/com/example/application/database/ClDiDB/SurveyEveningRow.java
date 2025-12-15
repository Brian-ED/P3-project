package com.example.application.database.ClDiDB;

import java.time.ZonedDateTime;

import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateComboboxRollAnswer;
import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateRollAnswer;
import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateRollComboboxAnswer;
import com.example.application.database.ClDiDB.Answers.YesOrNoElaborateRollRollAnswer;
import com.example.application.database.ClDiDB.Questions.GenericQuestion;
import com.example.application.database.ClDiDB.Questions.YesOrNoElaborateComboboxRollQuestion;
import com.example.application.database.ClDiDB.Questions.YesOrNoElaborateRollComboboxQuestion;
import com.example.application.database.ClDiDB.Questions.YesOrNoElaborateRollQuestion;
import com.example.application.database.ClDiDB.Questions.YesOrNoElaborateRollRollQuestion;
import com.example.application.model.SurveyType;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

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
    public CitizenRow getOwner() {return owner;};
    public void setOwner(CitizenRow owner) {this.owner = owner;};

    @Column(nullable = false)
    private ZonedDateTime whenAnswered;
    public ZonedDateTime getWhenAnswered() {return whenAnswered;};
    public void setWhenAnswered(ZonedDateTime whenAnswered) {this.whenAnswered = whenAnswered;};

    //---------
    // Answers:
    @Embedded private YesOrNoElaborateRollRollAnswer     internelAnswer0 = new YesOrNoElaborateRollRollAnswer    (); @Transient final private YesOrNoElaborateRollRollQuestion     answer0 = new YesOrNoElaborateRollRollQuestion    ("Har du været fysisk aktiv i dag?"           , "Hvor mange minutter?", "Hvornår på dagen?"                                                                                                   , internelAnswer0); public YesOrNoElaborateRollRollQuestion  getAnswer0() {return answer0;} // AskMoreIfYesQuestion("Har du været fysisk aktiv i dag?", RollQuestion("Hvor mange minutter?"), RollQuestion("Hvornår på dagen?")),
    @Embedded private YesOrNoElaborateRollAnswer         internelAnswer1 = new YesOrNoElaborateRollAnswer        (); @Transient final private YesOrNoElaborateRollQuestion         answer1 = new YesOrNoElaborateRollQuestion        ("Har du været ude i dagslys?"                , "Hvornår på dagen?"                                                                                                                           , internelAnswer1); public YesOrNoElaborateRollQuestion          getAnswer1() {return answer1;} // AskMoreIfYesQuestion("Har du været ude i dagslys?", RollQuestion("Hvornår på dagen?")),
    @Embedded private YesOrNoElaborateComboboxRollAnswer internelAnswer2 = new YesOrNoElaborateComboboxRollAnswer(); @Transient final private YesOrNoElaborateComboboxRollQuestion answer2 = new YesOrNoElaborateComboboxRollQuestion("Har du drukket koffeinholdige drikke i dag?", "Hvilke drikke (flere kan vælges)", new String[]{"Energidrik", "Kaffe", "Te", "Sodavand", "Andet"}, "Hvornår på dagen indtager du den sidste drik?"             , internelAnswer2); public YesOrNoElaborateComboboxRollQuestion  getAnswer2() {return answer2;} // AskMoreIfYesQuestion("Har du drukket koffeinholdige drikke i dag?", ComboBoxQuestion ("Hvilke drikke (flere kan vælges)", "Monster", "Kaffe", "Sodavand"), RollQuestion("Hvornår på dagen indtager du den sidste drik?")),
    @Embedded private YesOrNoElaborateRollComboboxAnswer internelAnswer3 = new YesOrNoElaborateRollComboboxAnswer(); @Transient final private YesOrNoElaborateRollComboboxQuestion answer3 = new YesOrNoElaborateRollComboboxQuestion("Har du drukket alkohol?"                    , "Hvornår på dagen drak du den sidste genstand?", "Hvor mange genstande har du ca. drukket i løbet af dagen?", new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", }     , internelAnswer3); public YesOrNoElaborateRollComboboxQuestion  getAnswer3() {return answer3;} // AskMoreIfYesQuestion("Har du drukket alkohol?", RollQuestion ("Hvornår på dagen drak du den sidste genstand?"), ComboBoxQuestion("Hvor mange genstande har du ca. drukket i løbet af dagen?", "1", "2", "3")),
    @Embedded private YesOrNoElaborateRollAnswer         internelAnswer4 = new YesOrNoElaborateRollAnswer        (); @Transient final private YesOrNoElaborateRollQuestion         answer4 = new YesOrNoElaborateRollQuestion        ("Har du sovet i løbet af dagen?"             , "Hvornår på dagen?"                                                                                                                           , internelAnswer4); public YesOrNoElaborateRollQuestion          getAnswer4() {return answer4;} // AskMoreIfYesQuestion("Har du sovet i løbet af dagen?", RollQuestion("Hvornår på dagen?"))

    @Transient final private GenericQuestion<?>[] allAnswers = new GenericQuestion<?>[] {answer0, answer1, answer2, answer3, answer4};

    public GenericQuestion<?>[] getAnswers() {
        return allAnswers;
    }
	@Override
	public SurveyType getType() {
		return SurveyType.evening;
	}
}
