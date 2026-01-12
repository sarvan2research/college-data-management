package com.aahzi.collegedata.repository;

import com.aahzi.collegedata.entity.CollegeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollegeDetailsRepository extends JpaRepository<CollegeDetails, Long> {
    Optional<CollegeDetails> findByCollegeCode(String collegeCode);
}
