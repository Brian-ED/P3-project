package com.example.application.database;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.application.database.ClDiDB.AdvisorRow;
import com.example.application.database.ClDiDB.AnsweredSurveyEveningTableRow;
import com.example.application.database.ClDiDB.AnsweredSurveyMorningRow;
import com.example.application.database.ClDiDB.CitizenRow;
import com.example.application.database.ClDiDB.ComboBoxAnswer;
import com.example.application.database.ClDiDB.DurationAnswer;
import com.example.application.database.ClDiDB.RollAnswer;
import com.example.application.database.ClDiDB.YesOrNoElaborateComboboxAnswer;
import com.example.application.database.ClDiDB.YesOrNoElaborateComboboxRollAnswer;
import com.example.application.database.ClDiDB.YesOrNoElaborateRollAnswer;
import com.example.application.database.ClDiDB.YesOrNoElaborateRollComboboxAnswer;
import com.example.application.database.ClDiDB.YesOrNoElaborateRollRollAnswer;
import com.example.application.model.Answer;
import com.example.application.model.AnswerPayload.ComboBoxPayload;
import com.example.application.model.AnswerPayload.DurationPayload;
import com.example.application.model.AnswerPayload.RollPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateComboboxRollPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollComboboxPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollPayload;
import com.example.application.model.AnswerPayload.YesOrNoElaborateRollRollPayload;
import com.example.application.model.AnsweredSurvey;
import com.example.application.model.Citizen;
import com.example.application.model.DatabaseControler;
import com.example.application.model.DynamicSurvey;
import com.example.application.model.SleepAdvisor;
import com.example.application.model.SurveyType;

@Service
public class PostgreSQLDatabaseControler implements DatabaseControler {

    private final AnsweredSurveyMorningRepository morningRepo;
    private final AnsweredSurveyEveningRepository eveningRepo;
    private final CitizenRepository citizensRepo;
    private final AdvisorRepository advisorsRepo;

    public PostgreSQLDatabaseControler(
        AnsweredSurveyMorningRepository morningRepo,
        AnsweredSurveyEveningRepository eveningRepo,
        CitizenRepository citizensRepo,
        AdvisorRepository advisorsRepo
    ) {
        this.morningRepo = morningRepo;
        this.eveningRepo = eveningRepo;
        this.citizensRepo = citizensRepo;
        this.advisorsRepo = advisorsRepo;
    }

    private Citizen CitizenRowToCitizen(CitizenRow row) {
        AdvisorRow advisorRow = row.getAssignedAdvisor();
        SleepAdvisor advisor = new SleepAdvisor(advisorRow.getFullName());
        List<AnsweredSurveyMorningRow> mornings = row.getMorningSurveys();
        List<AnsweredSurveyEveningTableRow> evenings = row.getEveningSurveys();
        AnsweredSurvey[] answeredSurveys = new AnsweredSurvey[mornings.size()+evenings.size()];
        for (int i = 0; i<mornings.size(); i++) {
            AnsweredSurveyMorningRow m = mornings.get(i);
            Answer<?>[] answers = new Answer[] {
                new YesOrNoElaborateComboboxAnswer() {{answer(new YesOrNoElaborateComboboxPayload (m.getAnswer0YesNo           (), m.getAnswer0Elaborate()));}}, // AskMoreIfYesQuestion("Tager du nogen gange sovemedicin eller melatonin piller", ComboBoxQuestion("Hvad tager du?", "Sovemedicin", "Melatonin")),
                new ComboBoxAnswer                () {{answer(new ComboBoxPayload                 (m.getAnswer1WhichIsSelected ()                         ));}}, // ComboBoxQuestion("Hvad foretog du dig de sidste par timer inden du gik i seng?", "Ting", "Sager"),
                new RollAnswer                    () {{answer(new RollPayload                     (m.getAnswer2Roll            ()                         ));}}, // RollQuestion("I går gik jeg i seng klokken:"),
                new RollAnswer                    () {{answer(new RollPayload                     (m.getAnswer3Roll            ()                         ));}}, // RollQuestion("Jeg slukkede lyset klokken:"),
                new DurationAnswer                () {{answer(new DurationPayload                 (m.getAnswer4Minutes         ()                         ));}}, // RollQuestion("Efter jeg slukkede lyset, sov jeg ca. efter:"),
                new ComboBoxAnswer                () {{answer(new ComboBoxPayload                 (m.getAnswer5WhichIsSelected ()                         ));}}, // ComboBoxQuestion("Jeg vågnede cirka x gange i løbet af natten:", "1", "2", "3"),
                new DurationAnswer                () {{answer(new DurationPayload                 (m.getAnswer6Minutes         ()                         ));}}, // RollQuestion("Jeg var sammenlagt vågen i cirka x minutter i løbet af natten"),
                new RollAnswer                    () {{answer(new RollPayload                     (m.getAnswer7Roll            ()                         ));}}, // RollQuestion("I morges vågnede jeg klokken:"),
                new RollAnswer                    () {{answer(new RollPayload                     (m.getAnswer8Roll            ()                         ));}}, // RollQuestion("Og jeg stod op klokken:")
            };
            assert answers.length == DynamicSurvey.morningSurvey.length;

            answeredSurveys[i] = new AnsweredSurvey(answers, SurveyType.morning, m.getWhenAnswered());
        }
        for (int i = 0; i<evenings.size(); i++)  {
            AnsweredSurveyEveningTableRow e = evenings.get(i);
            Answer<?>[] answers = new Answer[] {
                new YesOrNoElaborateRollRollAnswer     () {{answer(new YesOrNoElaborateRollRollPayload     (e.getAnswer0YesNo(), e.getAnswer0Timestamp1      (), e.getAnswer0Timestamp2      () ));}}, // AskMoreIfYesQuestion("Har du været fysisk aktiv i dag?", RollQuestion("Hvor mange minutter?"), RollQuestion("Hvornår på dagen?")),
                new YesOrNoElaborateRollAnswer         () {{answer(new YesOrNoElaborateRollPayload         (e.getAnswer1YesNo(), e.getAnswer1Timestamp       ()                                 ));}}, // AskMoreIfYesQuestion("Har du været ude i dagslys?", RollQuestion("Hvornår på dagen?")),
                new YesOrNoElaborateComboboxRollAnswer () {{answer(new YesOrNoElaborateComboboxRollPayload (e.getAnswer2YesNo(), e.getAnswer2WhichIsSelected (), e.getAnswer2Timestamp       () ));}}, // AskMoreIfYesQuestion("Har du drukket koffeinholdige drikke i dag?", ComboBoxQuestion ("Hvilke drikke (flere kan vælges)", "Monster", "Kaffe", "Sodavand"), RollQuestion("Hvornår på dagen indtager du den sidste drik?")),
                new YesOrNoElaborateRollComboboxAnswer () {{answer(new YesOrNoElaborateRollComboboxPayload (e.getAnswer3YesNo(), e.getAnswer3Timestamp       (), e.getAnswer3WhichIsSelected () ));}}, // AskMoreIfYesQuestion("Har du drukket alkohol?", RollQuestion("Hvornår på dagen drak du den sidste genstand?"), ComboBoxQuestion("Hvor mange genstande har du ca. drukket i løbet af dagen?", "1", "2", "3")),
                new YesOrNoElaborateRollAnswer         () {{answer(new YesOrNoElaborateRollPayload         (e.getAnswer4YesNo(), e.getAnswer4Timestamp       ()                                 ));}}, // AskMoreIfYesQuestion("Har du sovet i løbet af dagen?", RollQuestion("Hvornår på dagen"))
            };
            assert answers.length == DynamicSurvey.eveningSurvey.length;

            answeredSurveys[mornings.size() + i] = new AnsweredSurvey(answers, SurveyType.morning, e.getWhenAnswered());
        }

        return new Citizen(row.getFullName(), row.getId(), answeredSurveys, Optional.of(advisor));
    }

    @Override
    public Citizen[] searchCitizensByName(String name) {
        List<CitizenRow> x = citizensRepo.findByFullName(name);
        Citizen[] r = new Citizen[x.size()];
        for (int i = 0; i<x.size(); i++) {
            r[i]= CitizenRowToCitizen(x.get(i));
        }
        return r;
    }

    @Override
    public Optional<Citizen> getCitizenByName(String name) {
        Optional<CitizenRow> maybeRow = citizensRepo.findOneByFullName(name);
        if (maybeRow.isEmpty()) {
            return Optional.empty();
        }
        CitizenRow row = maybeRow.orElseThrow();
        return Optional.of(CitizenRowToCitizen(row));
    }

	@Override
	public Citizen newCitizen(String username) {
        CitizenRow row = new CitizenRow(); // TODO this might not actually create a new row in the database, I am not sure
        row.setFullName(username);
        return CitizenRowToCitizen(row);
	}
}
