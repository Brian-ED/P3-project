package com.example.application.database.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.application.database.ClDiDB.AdvisorRow;

// Spring automatically generates all methods here based on their names... somehow
@Repository
public interface AdvisorRepository extends JpaRepository<AdvisorRow, Long> {
    List<AdvisorRow> findByFullName(String fullName);
    Optional<AdvisorRow> findOneByFullName(String fullName);
}
