package com.aahzi.collegedata.repository;

import com.aahzi.collegedata.entity.CollegeCutoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollegeCutoffRepository extends JpaRepository<CollegeCutoff, Long> {
    Optional<CollegeCutoff> findByCollegeCode(String collegeCode);
}
