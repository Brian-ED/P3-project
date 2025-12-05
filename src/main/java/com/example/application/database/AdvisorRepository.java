package com.example.application.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.application.database.ClDiDB.AdvisorRow;

// Spring automatically generates all methods here based on their names... somehow
@Repository
public interface AdvisorRepository extends JpaRepository<AdvisorRow, Long> { }
