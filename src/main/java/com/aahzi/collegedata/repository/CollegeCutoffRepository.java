package com.aahzi.collegedata.repository;

import com.aahzi.collegedata.entity.CollegeCutoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CollegeCutoffRepository extends JpaRepository<CollegeCutoff, Long> {
    Optional<CollegeCutoff> findByCollegeCode(String collegeCode);

    List<CollegeCutoff> findByCollegeNameContainingIgnoreCase(String collegeName);
}
