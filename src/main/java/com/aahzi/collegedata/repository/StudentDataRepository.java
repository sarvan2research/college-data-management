package com.aahzi.collegedata.repository;

import com.aahzi.collegedata.entity.StudentData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDataRepository extends JpaRepository<StudentData, Long> {

}
