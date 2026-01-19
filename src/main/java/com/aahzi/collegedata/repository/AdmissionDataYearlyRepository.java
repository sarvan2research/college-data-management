package com.aahzi.collegedata.repository;

import com.aahzi.collegedata.entity.AdmissionDataYearly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdmissionDataYearlyRepository extends JpaRepository<AdmissionDataYearly, Long> {

        Optional<AdmissionDataYearly> findByAdmissionYearAndCollegeCodeAndCourseCode(
                        Integer admissionYear, String collegeCode, String courseCode);

        List<AdmissionDataYearly> findByCollegeCodeAndAdmissionYear(String collegeCode, Integer admissionYear);

        List<AdmissionDataYearly> findByCollegeCode(String collegeCode);

        List<AdmissionDataYearly> findByAdmissionYear(Integer admissionYear);

        List<AdmissionDataYearly> findByCollegeCodeAndCourseCode(String collegeCode, String courseCode);

        @Query("SELECT DISTINCT c.collegeCode FROM AdmissionDataYearly c ORDER BY c.collegeCode")
        List<String> findDistinctCollegeCodes();

        @Query("SELECT DISTINCT c.admissionYear FROM AdmissionDataYearly c ORDER BY c.admissionYear")
        List<Integer> findDistinctAdmissionYears();

        @Query("SELECT c FROM AdmissionDataYearly c WHERE c.collegeName LIKE %:name%")
        List<AdmissionDataYearly> findByCollegeNameContaining(@Param("name") String name);

        @Query("SELECT c FROM AdmissionDataYearly c WHERE c.courseName LIKE %:name%")
        List<AdmissionDataYearly> findByCourseNameContaining(@Param("name") String name);

        @Query("SELECT c FROM AdmissionDataYearly c WHERE " +
                        "LOWER(c.collegeName) LIKE LOWER(CONCAT('%', :collegeName, '%')) AND " +
                        "LOWER(c.courseName) LIKE LOWER(CONCAT('%', :courseName, '%')) AND " +
                        "c.admissionYear >= :startYear " +
                        "ORDER BY c.admissionYear DESC")
        List<AdmissionDataYearly> searchByCollegeAndCourseAndYearRange(
                        @Param("collegeName") String collegeName,
                        @Param("courseName") String courseName,
                        @Param("startYear") Integer startYear);

        @Query("SELECT c FROM AdmissionDataYearly c WHERE c.courseCode = :courseCode AND (:district IS NULL OR c.district = :district) AND CASE :community WHEN 'OC' THEN c.cutOffOC WHEN 'BC' THEN c.cutOffBC WHEN 'MBC' THEN c.cutOffMBC WHEN 'BCM' THEN c.cutOffBCM WHEN 'SC' THEN c.cutOffSC WHEN 'SCA' THEN c.cutOffSCA WHEN 'ST' THEN c.cutOffST END BETWEEN :minCutoff AND :maxCutoff ORDER BY CASE :community WHEN 'OC' THEN c.cutOffOC WHEN 'BC' THEN c.cutOffBC WHEN 'MBC' THEN c.cutOffMBC WHEN 'BCM' THEN c.cutOffBCM WHEN 'SC' THEN c.cutOffSC WHEN 'SCA' THEN c.cutOffSCA WHEN 'ST' THEN c.cutOffST END DESC")
        List<AdmissionDataYearly> findEligibleCollegesWithCutoffRange(@Param("community") String community,
                        @Param("courseCode") String courseCode,
                        @Param("district") String district,
                        @Param("minCutoff") BigDecimal minCutoff,
                        @Param("maxCutoff") BigDecimal maxCutoff);

}
