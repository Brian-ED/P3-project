package com.example.application.database;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.application.database.ClDiDB.AdvisorRow;
import com.example.application.database.ClDiDB.AnsweredSurveyEveningTableRow;
import com.example.application.database.ClDiDB.AnsweredSurveyMorningRow;
import com.example.application.database.ClDiDB.CitizenRow;
import com.example.application.database.ClDiDB.YesOrNoElaborateIntAnswer;
import com.example.application.model.Answer;
import com.example.application.model.AnswerPayloads.YesOrNoElaborateIntPayload;
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
            Answer<?>[] answers = new Answer[DynamicSurvey.morningSurvey.length];

            // TODO Handle every single answer

            for (var j : answers) { assert j != null;}

            answeredSurveys[i] = new AnsweredSurvey(answers, SurveyType.morning, m.getWhenAnswered());
        }
        for (int i = 0; i<evenings.size(); i++)  {
            AnsweredSurveyEveningTableRow e = evenings.get(i);
            Answer<?>[] answers = new Answer[DynamicSurvey.eveningSurvey.length];

            // TODO Handle every single answer

            answers[0] = new YesOrNoElaborateIntAnswer(
                new YesOrNoElaborateIntPayload(e.getAnswer1YesNo(), e.getAnswer1Elaborate())
            );
            for (var j : answers) { assert j != null;}

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
