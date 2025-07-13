package com.aahzi.collegedata.service;

import com.aahzi.collegedata.entity.CollegeCourseData;
import com.aahzi.collegedata.entity.StudentData;
import com.aahzi.collegedata.model.EligibleCollegeResponse;
import com.aahzi.collegedata.model.StudentEligibilityRequest;
import com.aahzi.collegedata.repository.CollegeCourseDataRepository;
import com.aahzi.collegedata.repository.StudentDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentDataService {

    @Autowired
    private StudentDataRepository studentDataRepository;

    @Autowired
    private CollegeCourseDataRepository collegeCourseDataRepository;

    @Autowired
    private DataPersistenceService dataPersistenceService;

    public void saveStudentData(StudentData studentData) {
        studentDataRepository.save(studentData);
        //dataPersistenceService.saveStudentDataToFile(studentData);
    }

    public List<EligibleCollegeResponse> getEligibleColleges(StudentEligibilityRequest studentEligibilityRequest) {
        StudentData studentData=saveStudentData(studentEligibilityRequest);
        BigDecimal cutoff = (studentData.getPhysics().add(studentData.getChemistry()).divide(BigDecimal.valueOf(2))).add(studentData.getMaths());
        System.out.println("Cutoff of given: "+ cutoff);
        BigDecimal maxCutOff=cutoff.add(BigDecimal.valueOf(5));
        BigDecimal minCutOff=cutoff.subtract(BigDecimal.valueOf(5));
        System.out.println("max:min cutoff: "+ maxCutOff+":"+minCutOff);
        List<CollegeCourseData> eligibleColleges=collegeCourseDataRepository.findEligibleCollegesWithCutoffRange(studentData.getCommunity(),"CS",studentData.getDistrict(), minCutOff,maxCutOff);
        return getEligibleCollegeResponses(eligibleColleges);
    }

    private static List<EligibleCollegeResponse> getEligibleCollegeResponses(List<CollegeCourseData> eligibleColleges) {
        List<EligibleCollegeResponse> response = new ArrayList<>();
        for (CollegeCourseData college : eligibleColleges) {
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
        studentData.setCourse(studentEligibilityRequest.getCourse());
        studentData.setDistrict(studentEligibilityRequest.getDistrict());
        studentData.setMaths(studentEligibilityRequest.getMathsMarks());
        studentData.setChemistry(studentEligibilityRequest.getChemistryMarks());
        studentData.setPhysics(studentEligibilityRequest.getPhysicsMarks());

        studentDataRepository.save(studentData);
        //TODO sarvan
        //dataPersistenceService.saveStudentDataToFile(studentData);
        return studentData;
    }

}
