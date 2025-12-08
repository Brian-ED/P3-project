package com.example.application.database.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.application.database.ClDiDB.CitizenRow;
import com.example.application.database.ClDiDB.SurveyEveningRow;

@Repository
public interface AnsweredSurveyEveningRepository extends JpaRepository<SurveyEveningRow, Long> {
    List<SurveyEveningRow> findByOwner(CitizenRow owner);
}
