package com.aahzi.collegedata.repository;

import com.aahzi.collegedata.entity.CutOffDataYearly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CutOffDataYearlyRepository extends JpaRepository<CutOffDataYearly, Long> {
    
   List<CutOffDataYearly> findByCollegeCodeAndAdmissionYear(String collegeCode, Integer admissionYear);
    
    List<CutOffDataYearly> findByCollegeCode(String collegeCode);
    
    List<CutOffDataYearly> findByAdmissionYear(Integer admissionYear);
    
    @Query("SELECT DISTINCT c.collegeCode FROM CutOffDataYearly c ORDER BY c.collegeCode")
    List<String> findDistinctCollegeCodes();
    
    @Query("SELECT DISTINCT c.admissionYear FROM CutOffDataYearly c ORDER BY c.admissionYear")
    List<Integer> findDistinctAdmissionYears();
    
    @Query("SELECT c FROM CutOffDataYearly c WHERE c.collegeName LIKE %:name%")
    List<CutOffDataYearly> findByCollegeNameContaining(@Param("name") String name);
    
    @Query("SELECT c FROM CutOffDataYearly c WHERE c.courseName LIKE %:name%")
    List<CutOffDataYearly> findByCourseNameContaining(@Param("name") String name);

// @Query("SELECT c FROM CollegeCourseData c WHERE c.courseCode = :courseCode AND CASE :community WHEN 'OC' THEN c.cutOffOC WHEN 'BC' THEN c.cutOffBC WHEN 'MBC' THEN c.cutOffMBC END <= :cutoff")
// List<CollegeCourseData> findEligibleColleges(@Param("community") String community,
//                                              @Param("courseCode") String courseCode,
//                                              @Param("cutoff") BigDecimal cutoff);

 @Query("SELECT c FROM CutOffDataYearly c WHERE c.courseCode = :courseCode AND (:district IS NULL OR c.district = :district) AND CASE :community WHEN 'OC' THEN c.cutOffOC WHEN 'BC' THEN c.cutOffBC WHEN 'MBC' THEN c.cutOffMBC WHEN 'BCM' THEN c.cutOffBCM WHEN 'SC' THEN c.cutOffSC WHEN 'SCA' THEN c.cutOffSCA WHEN 'ST' THEN c.cutOffST END BETWEEN :minCutoff AND :maxCutoff ORDER BY CASE :community WHEN 'OC' THEN c.cutOffOC WHEN 'BC' THEN c.cutOffBC WHEN 'MBC' THEN c.cutOffMBC WHEN 'BCM' THEN c.cutOffBCM WHEN 'SC' THEN c.cutOffSC WHEN 'SCA' THEN c.cutOffSCA WHEN 'ST' THEN c.cutOffST END DESC")
 List<CutOffDataYearly> findEligibleCollegesWithCutoffRange(@Param("community") String community,
                                                            @Param("courseCode") String courseCode,
                                                            @Param("district") String district,
                                                            @Param("minCutoff") BigDecimal minCutoff,
                                                            @Param("maxCutoff") BigDecimal maxCutoff);

}
