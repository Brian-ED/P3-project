package com.example.application.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.database.ClDiDB.AdvisorRow;
import com.example.application.database.ClDiDB.CitizenRow;
import com.example.application.database.ClDiDB.Survey;
import com.example.application.database.ClDiDB.SurveyEveningRow;
import com.example.application.database.ClDiDB.SurveyMorningRow;
import com.example.application.database.repositories.AdvisorRepository;
import com.example.application.database.repositories.AnsweredSurveyEveningRepository;
import com.example.application.database.repositories.AnsweredSurveyMorningRepository;
import com.example.application.database.repositories.CitizenRepository;
import com.example.application.model.AnsweredEveningSurvey;
import com.example.application.model.AnsweredMorningSurvey;
import com.example.application.model.AnsweredSurvey;
import com.example.application.model.Citizen;
import com.example.application.model.DatabaseControler;
import com.example.application.model.SleepAdvisor;

import jakarta.transaction.Transactional;

@Service
@RestController

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
    @Transactional
    public Optional<Citizen> getCitizenById(UUID id) {
        Optional<CitizenRow> maybeRow = citizensRepo.findById(id);
        return maybeRow.map(row -> citizenRowToCitizen(row));
    }

    private Citizen citizenRowToCitizen(CitizenRow row) {

        List<SurveyMorningRow> mornings = morningRepo.findByOwner(row);
        List<SurveyEveningRow> evenings = eveningRepo.findByOwner(row);
        List<AnsweredSurvey> answeredSurveys = new ArrayList<>();
        for (var m : mornings) {
            answeredSurveys.add(new AnsweredMorningSurvey(m.getID(), m.getAnswers(), m.getWhenAnswered()));
        }
        for (var e : evenings)  {
            answeredSurveys.add(new AnsweredEveningSurvey(e.getID(), e.getAnswers(), e.getWhenAnswered()));
        }
        Optional<SleepAdvisor> assignedAdvisor = row.getAssignedAdvisor().map(x -> new SleepAdvisor(row.getId(), x.getFullName()));
        Citizen citizen = new Citizen(row.getId(), row.getFullName(), answeredSurveys, assignedAdvisor);

        citizen.addUpdateListener(
            () -> {
                row.setFullName(citizen.getFullName());
                citizen
                    .getAssignedAdvisor()
                    .flatMap(advisor -> advisorsRepo.findOneByFullName(advisor.getFullName()))
                    .ifPresent(advisorRow -> row.setAssignedAdvisor(advisorRow));

                Integer newSurveysAmount = citizen.getSurveys().size() - mornings.size() - evenings.size();

                if (newSurveysAmount != 1 && newSurveysAmount != 0) {
                    throw new IllegalStateException("Can only append one survey at a time to a citizen, but "+newSurveysAmount +" were appended");
                }
                citizensRepo.save(row);
            }
        );

        return citizen;
    }

    @Override
    @Transactional
    public Citizen[] searchCitizensByName(String name) {
        List<CitizenRow> x = citizensRepo.findByFullName(name);
        Citizen[] r = new Citizen[x.size()];
        for (int i = 0; i<x.size(); i++) {
            r[i] = citizenRowToCitizen(x.get(i));
        }
        return r;
    }

    @Override
    @Transactional
    public Optional<Citizen> getCitizenByName(String name) {
        Optional<CitizenRow> maybeRow = citizensRepo.findOneByFullName(name);
        if (maybeRow.isEmpty()) {
            return Optional.empty();
        }
        CitizenRow row = maybeRow.orElseThrow();
        return Optional.of(citizenRowToCitizen(row));
    }

	@Override
    @Transactional
	public Citizen newCitizen(String username) {
        CitizenRow row = new CitizenRow();
        row.setFullName(username);
        citizensRepo.save(row);
        return citizenRowToCitizen(row);
	}

	@Override
    @Transactional
	public void saveSurvey(Survey survey) {
        switch (survey) {
            case SurveyMorningRow x -> morningRepo.saveAndFlush(x);
            case SurveyEveningRow x -> eveningRepo.saveAndFlush(x);
        };
	}

	@Override
    @Transactional
	public Optional<SleepAdvisor> getAdvisorByName(String username) {

        Optional<AdvisorRow> maybeRow = advisorsRepo.findOneByFullName(username);
        if (maybeRow.isEmpty()) {
            return Optional.empty();
        }
        AdvisorRow row = maybeRow.orElseThrow();
        return Optional.of(new SleepAdvisor(row.getID(), row.getFullName()));
	}

	@Override
    @Transactional
	public SleepAdvisor newAdvisor(String username) {
        AdvisorRow row = new AdvisorRow();
        row.setFullName(username);
        advisorsRepo.save(row);
        return new SleepAdvisor(row.getID(), row.getFullName());
	}

	@Override
    @Transactional
    public SleepAdvisor[] getAllAdvisors() {
        List<AdvisorRow> rows = advisorsRepo.findAll();
        SleepAdvisor[] advisors = new SleepAdvisor[rows.size()];
        for (int i=0; i<advisors.length; i++) {
            advisors[i] = new SleepAdvisor(rows.get(i).getID(), rows.get(i).getFullName());
        }
        return advisors;
    }

    // TODO Ideally this function is temporary, it should be removed and replaced by observer pattern to initialize DynamicSurvey
    @Override
    public Optional<CitizenRow> getCitizenRowById(UUID id) {
        return citizensRepo.findById(id);
    }
}
