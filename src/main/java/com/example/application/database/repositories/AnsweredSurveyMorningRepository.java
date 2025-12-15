package com.example.application.database.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.application.database.ClDiDB.CitizenRow;
import com.example.application.database.ClDiDB.SurveyMorningRow;

@Repository
public interface AnsweredSurveyMorningRepository extends JpaRepository<SurveyMorningRow, UUID> {
    List<SurveyMorningRow> findByOwner(CitizenRow owner);
}
