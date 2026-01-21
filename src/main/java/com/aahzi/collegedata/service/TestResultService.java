package com.aahzi.collegedata.service;

import com.aahzi.collegedata.entity.PersonalityAssessment;
import com.aahzi.collegedata.entity.SubjectInterest;
import com.aahzi.collegedata.entity.TestResult;
import com.aahzi.collegedata.repository.TestResultRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestResultService {

    private final TestResultRepository repository;

    public TestResultService(TestResultRepository repository) {
        this.repository = repository;
    }

    public TestResult saveTestResult(TestResultRequest request) {
        PersonalityAssessment personalityAssessment = new PersonalityAssessment(
                getDouble(request.getPersonalityScores(), "Logical Thinking"),
                getDouble(request.getPersonalityScores(), "Communication Skills"),
                getDouble(request.getPersonalityScores(), "Analytical Skills"),
                getDouble(request.getPersonalityScores(), "Attitude"),
                getDouble(request.getPersonalityScores(), "Maths"),
                getDouble(request.getPersonalityScores(), "Computer Science"));

        SubjectInterest subjectInterest = new SubjectInterest(

                getDouble(request.getSubjectScores(), "CSE"),
                getDouble(request.getSubjectScores(), "AI/DS Engineering"), // Maps to aids
                getDouble(request.getSubjectScores(), "Biomedical Engineering"),
                getDouble(request.getSubjectScores(), "Chemical Engineering"),
                getDouble(request.getSubjectScores(), "Civil Engineering"),
                getDouble(request.getSubjectScores(), "ECE"),
                getDouble(request.getSubjectScores(), "EEE"),
                getDouble(request.getSubjectScores(), "IT"),
                getDouble(request.getSubjectScores(), "Mechanical Engineering"),
                getDouble(request.getSubjectScores(), "Mechatronics Engineering"));

        Double personalityTotal = request.getPersonalityScores().values().stream()
                .mapToDouble(val -> {
                    if (val instanceof Number)
                        return ((Number) val).doubleValue();
                    return 0.0;
                }).sum();

        Double subjectTotal = request.getSubjectScores().values().stream()
                .mapToDouble(val -> {
                    if (val instanceof Number)
                        return ((Number) val).doubleValue();
                    return 0.0;
                }).sum();

        TestResult testResult = new TestResult(
                request.getStudentId(),
                request.getName(),
                request.getEmail(),
                request.getMobileNumber(),
                request.getTimestamp(),
                request.getTimeUsed(),
                personalityAssessment,
                subjectInterest,
                personalityTotal,
                subjectTotal);
        return repository.save(testResult);
    }

    public java.util.List<TestResult> searchTestResults(String query) {
        return repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMobileNumberContaining(query,
                query, query);
    }

    public java.util.List<TestResult> getAllTestResults() {
        return repository.findAll(org.springframework.data.domain.Sort
                .by(org.springframework.data.domain.Sort.Direction.DESC, "timestamp"));
    }

    private Double getDouble(Map<String, Object> map, String key) {
        if (map == null || !map.containsKey(key)) {
            return 0.0;
        }
        Object val = map.get(key);
        if (val instanceof Number) {
            return ((Number) val).doubleValue();
        }
        return 0.0;
    }

    public static class TestResultRequest {
        private String studentId;
        private String name;
        private String email;
        private String mobileNumber;
        private String timestamp;
        private String timeUsed;
        private Map<String, Object> answers;
        private Map<String, Object> personalityScores;
        private Map<String, Object> subjectScores;

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getTimeUsed() {
            return timeUsed;
        }

        public void setTimeUsed(String timeUsed) {
            this.timeUsed = timeUsed;
        }

        public Map<String, Object> getAnswers() {
            return answers;
        }

        public void setAnswers(Map<String, Object> answers) {
            this.answers = answers;
        }

        public Map<String, Object> getPersonalityScores() {
            return personalityScores;
        }

        public void setPersonalityScores(Map<String, Object> personalityScores) {
            this.personalityScores = personalityScores;
        }

        public Map<String, Object> getSubjectScores() {
            return subjectScores;
        }

        public void setSubjectScores(Map<String, Object> subjectScores) {
            this.subjectScores = subjectScores;
        }
    }
}
