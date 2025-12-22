package com.example.application.database.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.application.database.ClDiDB.CitizenRow;
import com.example.application.database.ClDiDB.SurveyEveningRow;

@Repository
public interface AnsweredSurveyEveningRepository extends JpaRepository<SurveyEveningRow, UUID> {
    List<SurveyEveningRow> findByOwner(CitizenRow owner);
    Optional<SurveyEveningRow> findByID(UUID id);
}
