package com.example.application.database.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.application.database.ClDiDB.CitizenRow;

// Spring automatically generates all methods here based on their names... somehow
@Repository
public interface CitizenRepository extends JpaRepository<CitizenRow, UUID> {
    List<CitizenRow> findByFullName(String fullName);
    Optional<CitizenRow> findOneByFullName(String fullName);
}
