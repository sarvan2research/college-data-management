package com.aahzi.collegedata.service;

import com.aahzi.collegedata.entity.CutOffDataYearly;
import com.aahzi.collegedata.entity.StudentData;
import com.aahzi.collegedata.model.EligibleCollegeResponse;
import com.aahzi.collegedata.model.StudentEligibilityRequest;
import com.aahzi.collegedata.repository.CutOffDataYearlyRepository;
import com.aahzi.collegedata.repository.StudentDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StudentDataService {

    @Autowired
    private StudentDataRepository studentDataRepository;

    @Autowired
    private CutOffDataYearlyRepository cutOffDataYearlyRepository;

    @Autowired
    private StudentDataPersistenceService studentDataPersistenceService;

    public void saveStudentData(StudentData studentData) {
        studentDataRepository.save(studentData);
        studentDataPersistenceService.saveDataToFile(Arrays.asList(studentData));
    }

    public List<EligibleCollegeResponse> getEligibleColleges(StudentEligibilityRequest studentEligibilityRequest) {
        System.out.println(studentEligibilityRequest);
        StudentData studentData = saveStudentData(studentEligibilityRequest);
        BigDecimal cutoff = calculateCutoff(studentData);
        BigDecimal maxCutOff = cutoff.add(BigDecimal.valueOf(5));
        BigDecimal minCutOff = cutoff.subtract(BigDecimal.valueOf(5));
        return getEligibleCollegesRecursive(studentData, studentEligibilityRequest.getCourseCode(), maxCutOff, minCutOff);
    }

    private List<EligibleCollegeResponse> getEligibleCollegesRecursive(StudentData studentData, String courseCode, BigDecimal maxCutOff, BigDecimal minCutOff) {
        List<CutOffDataYearly> eligibleColleges = cutOffDataYearlyRepository.findEligibleCollegesWithCutoffRange(
                studentData.getCommunity(),
                courseCode,
                studentData.getDistrict(),
                minCutOff,
                maxCutOff
        );
        if (eligibleColleges.size() >= 10 || minCutOff.compareTo(BigDecimal.valueOf(80)) <= 0) {
            return getEligibleCollegeResponses(eligibleColleges);
        } else {
            BigDecimal newMinCutOff = minCutOff.subtract(BigDecimal.valueOf(5));
            return getEligibleCollegesRecursive(studentData, courseCode, maxCutOff, newMinCutOff);
        }
    }

    private BigDecimal calculateCutoff(StudentData studentData) {
        return (studentData.getPhysics().add(studentData.getChemistry()).divide(BigDecimal.valueOf(2))).add(studentData.getMaths());
    }

    private static List<EligibleCollegeResponse> getEligibleCollegeResponses(List<CutOffDataYearly> eligibleColleges) {
        List<EligibleCollegeResponse> response = new ArrayList<>();
        for (CutOffDataYearly college : eligibleColleges) {
            EligibleCollegeResponse eligibleCollegeResponse = new EligibleCollegeResponse();
            eligibleCollegeResponse.setCollegeName(college.getCollegeName());
            eligibleCollegeResponse.setCollegeCode(college.getCollegeCode());
            eligibleCollegeResponse.setDistrictName(college.getDistrict());
            eligibleCollegeResponse.setCourseName(college.getCourseName());
            response.add(eligibleCollegeResponse);
        }
        return response;
    }

    public StudentData saveStudentData(StudentEligibilityRequest studentEligibilityRequest) {
        StudentData studentData = new StudentData();
        studentData.setName(studentEligibilityRequest.getName());
        studentData.setMobileNumber(studentEligibilityRequest.getMobileNumber());
        studentData.setCommunity(studentEligibilityRequest.getCommunity());
        studentData.setCourse(studentEligibilityRequest.getCourseCode());
        studentData.setDistrict(studentEligibilityRequest.getDistrict());
        studentData.setMaths(studentEligibilityRequest.getMathsMarks());
        studentData.setChemistry(studentEligibilityRequest.getChemistryMarks());
        studentData.setPhysics(studentEligibilityRequest.getPhysicsMarks());

        studentDataRepository.save(studentData);
        studentDataPersistenceService.saveDataToFile(Arrays.asList(studentData));
        return studentData;
    }

    public long getStudentDataCount() {
        return studentDataRepository.count();
    }

    public void loadDataFromFile() {
        List<StudentData> data = studentDataPersistenceService.loadDataFromFile();
        if (!data.isEmpty()) {
            studentDataRepository.deleteAll();
            studentDataRepository.saveAll(data);
            System.out.println("Student Data loaded from file to database: " + data.size() + " records");
        }
    }

}
