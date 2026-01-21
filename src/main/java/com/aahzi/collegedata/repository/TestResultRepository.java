package com.aahzi.collegedata.repository;

import com.aahzi.collegedata.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMobileNumberContaining(
            String name, String email, String mobileNumber);
}
