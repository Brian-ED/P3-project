package com.example.application.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.application.database.ClDiDB.AnsweredSurveyEveningTableRow;
import com.example.application.database.ClDiDB.CitizenRow;

@Repository
public interface AnsweredSurveyEveningRepository extends JpaRepository<AnsweredSurveyEveningTableRow, Long> {
    List<AnsweredSurveyEveningTableRow> findByOwner(CitizenRow owner);
}
