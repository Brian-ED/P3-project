package com.example.application.database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.example.application.model.Citizen;
import com.example.application.model.DatabaseControler;
import com.example.application.model.SleepAdvisor;
import com.example.application.views.SleepStats.SleepEntry;

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
public Optional<Citizen> getCitizenById(Long id) {
    Optional<CitizenRow> maybeRow = citizensRepo.findById(id);
    return maybeRow.map(Citizen::new);
}

@Transactional
public List<SleepEntry> getSleepEntriesForCitizen(Citizen citizen) {
    List<SleepEntry> entries = new ArrayList<>();

    // Morning surveys
    morningRepo.findByOwner(citizen.getRow()).forEach(m ->
    entries.add(new SleepEntry(
        m.getWhenAnswered().toLocalDate(),
        m.getAnswer4Value().getAnswerInHours()
    ))
);


    // Evening surveys (if needed)
    eveningRepo.findByOwner(citizen.getRow()).forEach(e ->
        entries.add(new SleepEntry(
            e.getWhenAnswered().toLocalDate(),
            0.0 // or another relevant field if available
        ))
    );

    entries.sort((a, b) -> a.getDate().compareTo(b.getDate()));

    return entries;
}




    @Override
    @Transactional
    public Citizen[] searchCitizensByName(String name) {
        List<CitizenRow> x = citizensRepo.findByFullName(name);
        Citizen[] r = new Citizen[x.size()];
        for (int i = 0; i<x.size(); i++) {
            r[i]= new Citizen(x.get(i));
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
        return Optional.of(new Citizen(row));
    }

	@Override
    @Transactional
	public Citizen newCitizen(String username) {
        CitizenRow row = new CitizenRow();
        row.setFullName(username);
        citizensRepo.save(row);
        return new Citizen(row);

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
        return Optional.of(new SleepAdvisor(row));
	}

	@Override
    @Transactional
	public SleepAdvisor newAdvisor(String username) {
        AdvisorRow row = new AdvisorRow();
        row.setFullName(username);
        advisorsRepo.save(row);
        return new SleepAdvisor(row);
	}
    @Transactional
    public List<SleepAdvisor> getAllAdvisors() {
        List<AdvisorRow> rows = advisorsRepo.findAll();
        return rows.stream().map(SleepAdvisor::new).toList();
    }
    @Transactional

    public void saveCitizen(Citizen citizen) {
        // Get the CitizenRow from the database
        CitizenRow row = citizensRepo.findOneByFullName(citizen.getFullName())
                                    .orElseThrow();

        // Assign the advisor if present
        citizen.getAssignedAdvisor().ifPresent(advisor -> row.setAssignedAdvisor(advisor.getRow()));

        // Save the updated row
        citizensRepo.saveAndFlush(row);
    }
}
