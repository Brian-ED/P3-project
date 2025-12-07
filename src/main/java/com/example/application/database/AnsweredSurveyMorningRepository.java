package com.example.application.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.application.database.ClDiDB.AnsweredSurveyMorningRow;
import com.example.application.database.ClDiDB.CitizenRow;

@Repository
public interface AnsweredSurveyMorningRepository extends JpaRepository<AnsweredSurveyMorningRow, Long> {
    List<AnsweredSurveyMorningRow> findByOwner(CitizenRow owner);
}
