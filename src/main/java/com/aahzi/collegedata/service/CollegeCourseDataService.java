package com.aahzi.collegedata.service;

import com.aahzi.collegedata.dto.CollegeCourseDataDTO;
import com.aahzi.collegedata.entity.CollegeCourseData;
import com.aahzi.collegedata.repository.CollegeCourseDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollegeCourseDataService {

    @Autowired
    private CollegeCourseDataRepository repository;

    @Autowired
    private DataParserService parserService;

    @Autowired
    private DataPersistenceService persistenceService;

    public List<CollegeCourseDataDTO> importRawData(String rawData) {
        List<CollegeCourseData> parsedData = parserService.parseRawData(rawData);

        // Clear existing data
        repository.deleteAll();

        // Save new data
        List<CollegeCourseData> savedData = repository.saveAll(parsedData);

        // Persist to file
        persistenceService.saveDataToFile(savedData);

        return savedData.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<CollegeCourseDataDTO> getAllData() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CollegeCourseDataDTO> getDataByCollegeCode(String collegeCode) {
        return repository.findByCollegeCode(collegeCode).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CollegeCourseDataDTO> getDataByYear(Integer admissionYear) {
        return repository.findByAdmissionYear(admissionYear).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CollegeCourseDataDTO> getDataByCollegeCodeAndYear(String collegeCode, Integer admissionYear) {
        return repository.findByCollegeCodeAndAdmissionYear(collegeCode, admissionYear).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<String> getDistinctCollegeCodes() {
        return repository.findDistinctCollegeCodes();
    }

    public List<Integer> getDistinctYears() {
        return repository.findDistinctAdmissionYears();
    }

    public List<CollegeCourseDataDTO> searchByCollegeName(String name) {
        return repository.findByCollegeNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CollegeCourseDataDTO> searchByCourseName(String name) {
        return repository.findByCourseNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CollegeCourseDataDTO> getDataById(Long id) {
        return repository.findById(id).map(this::convertToDTO);
    }

    public long getDataCount() {
        return repository.count();
    }

    public void loadDataFromFile() {
        List<CollegeCourseData> data = persistenceService.loadDataFromFile();
        if (!data.isEmpty()) {
            repository.deleteAll();
            repository.saveAll(data);
            System.out.println("Data loaded from file to database: " + data.size() + " records");
        }
    }

    private CollegeCourseDataDTO convertToDTO(CollegeCourseData entity) {
        CollegeCourseDataDTO dto = new CollegeCourseDataDTO();
        dto.setId(entity.getId());
        dto.setCollegeCode(entity.getCollegeCode());
        dto.setCollegeName(entity.getCollegeName());
        dto.setCourseCode(entity.getCourseCode());
        dto.setCourseName(entity.getCourseName());
        dto.setAdmissionYear(entity.getAdmissionYear());
        dto.setCutOffOC(entity.getCutOffOC());
        dto.setCutOffBC(entity.getCutOffBC());
        dto.setCutOffBCM(entity.getCutOffBCM());
        dto.setCutOffMBC(entity.getCutOffMBC());
        dto.setCutOffMBCDNC(entity.getCutOffMBCDNC());
        dto.setCutOffMBCV(entity.getCutOffMBCV());
        dto.setCutOffSC(entity.getCutOffSC());
        dto.setCutOffST(entity.getCutOffST());
        dto.setCutOffSCA(entity.getCutOffSCA());
        return dto;
    }

    public List<CollegeCourseDataDTO> importFromFile(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String rawData = reader.lines().collect(Collectors.joining("\n"));
            List<CollegeCourseDataDTO> result = importRawData(rawData);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadDataFromJsonResource(String resourcePath) {
        try {
            ClassPathResource resource = new ClassPathResource(resourcePath);
            if (!resource.exists()) {
                System.out.println("Resource not found: " + resourcePath);
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            CollegeDataImportRoot root = mapper.readValue(resource.getInputStream(), CollegeDataImportRoot.class);

            if (root != null && root.getColleges() != null) {
                List<CollegeCourseData> entities = root.getColleges().stream()
                        .map(item -> {
                            CollegeCourseData data = new CollegeCourseData();
                            data.setCollegeCode(item.getTneaCode());
                            data.setCollegeName(item.getCollegeName());
                            data.setDistrict(parserService.extractDistrict(item.getCollegeName()));
                            // Set defaults for other fields to avoid null issues if necessary, or leave
                            // null
                            data.setAdmissionYear(2025); // Default year
                            return data;
                        })
                        .collect(Collectors.toList());

                repository.saveAll(entities);
                System.out.println("Imported " + entities.size() + " colleges from JSON resource: " + resourcePath);
            }
        } catch (Exception e) {
            System.err.println("Error loading college data from JSON resource: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadCutoffData(String cutoffPath, String masterPath) {
        try {
           
            ClassPathResource masterResource = new ClassPathResource(masterPath);
            if (!masterResource.exists()) {
                System.out.println("Master resource not found: " + masterPath);
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            CollegeDataImportRoot masterRoot = mapper.readValue(masterResource.getInputStream(),
                    CollegeDataImportRoot.class);
            java.util.Map<String, String> collegeNameMap = new java.util.HashMap<>();
            if (masterRoot != null && masterRoot.getColleges() != null) {
                for (CollegeImportItem item : masterRoot.getColleges()) {
                    collegeNameMap.put(item.getTneaCode(), item.getCollegeName());
                }
            }

            ClassPathResource cutoffResource = new ClassPathResource(cutoffPath);
            if (!cutoffResource.exists()) {
                System.out.println("Cutoff resource not found: " + cutoffPath);
                return;
            }
            CutoffAnalysisRoot cutoffRoot = mapper.readValue(cutoffResource.getInputStream(), CutoffAnalysisRoot.class);

            if (cutoffRoot != null && cutoffRoot.getColleges() != null) {
                List<CollegeCourseData> entities = new java.util.ArrayList<>();
                for (CutoffCollegeItem collegeItem : cutoffRoot.getColleges()) {
                    String code = collegeItem.getCollegeCode();
                    String realName = collegeNameMap.getOrDefault(code, collegeItem.getCollegeName()); // Fallback to
                                                                                                       // name in cutoff
                                                                                                       // file
                    String district = parserService.extractDistrict(realName);

                    if (collegeItem.getCourseWiseCutoff() != null) {
                        for (CutoffCourseItem courseItem : collegeItem.getCourseWiseCutoff()) {
                            CollegeCourseData data = new CollegeCourseData();
                            data.setCollegeCode(code);
                            data.setCollegeName(realName);
                            data.setDistrict(district);
                            data.setCourseCode(courseItem.getBranchCode());
                            data.setCourseName(courseItem.getBranchName());
                            data.setAdmissionYear(2025); 

                            if (courseItem.getCommunityWiseCutoff() != null) {
                                for (CutoffCommunityItem commItem : courseItem.getCommunityWiseCutoff()) {
                                    java.math.BigDecimal cutoffVal = commItem.getCutoff() != null
                                            ? commItem.getCutoff().getMinMark()
                                            : null;
                                    if (cutoffVal != null) {
                                        switch (commItem.getCommunity().toUpperCase()) {
                                            case "OC":
                                                data.setCutOffOC(cutoffVal);
                                                break;
                                            case "BC":
                                                data.setCutOffBC(cutoffVal);
                                                break;
                                            case "BCM":
                                                data.setCutOffBCM(cutoffVal);
                                                break;
                                            case "MBC":
                                                data.setCutOffMBC(cutoffVal);
                                                break;
                                            case "SC":
                                                data.setCutOffSC(cutoffVal);
                                                break;
                                            case "SCA":
                                                data.setCutOffSCA(cutoffVal);
                                                break;
                                            case "ST":
                                                data.setCutOffST(cutoffVal);
                                                break;
                                        }
                                    }
                                }
                            }
                            entities.add(data);
                        }
                    }
                }

                if (!entities.isEmpty()) {
                    repository.deleteAll(); 
                    repository.saveAll(entities);
                    System.out.println("Imported " + entities.size() + " course cutoff records from " + cutoffPath);
                }
            }

        } catch (Exception e) {
            System.err.println("Error loading cutoff data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CutoffAnalysisRoot {
        @JsonProperty("colleges")
        private List<CutoffCollegeItem> colleges;

        public List<CutoffCollegeItem> getColleges() {
            return colleges;
        }

        @SuppressWarnings("unused")
        public void setColleges(List<CutoffCollegeItem> colleges) {
            this.colleges = colleges;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CutoffCollegeItem {
        @JsonProperty("college_code")
        private String collegeCode;
        @JsonProperty("college_name")
        private String collegeName;
        @JsonProperty("course_wise_cutoff")
        private List<CutoffCourseItem> courseWiseCutoff;

        public String getCollegeCode() {
            return collegeCode;
        }

        @SuppressWarnings("unused")
        public void setCollegeCode(String collegeCode) {
            this.collegeCode = collegeCode;
        }

        public String getCollegeName() {
            return collegeName;
        }

        @SuppressWarnings("unused")
        public void setCollegeName(String collegeName) {
            this.collegeName = collegeName;
        }

        public List<CutoffCourseItem> getCourseWiseCutoff() {
            return courseWiseCutoff;
        }

        @SuppressWarnings("unused")
        public void setCourseWiseCutoff(List<CutoffCourseItem> courseWiseCutoff) {
            this.courseWiseCutoff = courseWiseCutoff;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CutoffCourseItem {
        @JsonProperty("branch_code")
        private String branchCode;
        @JsonProperty("branch_name")
        private String branchName;
        @JsonProperty("community_wise_cutoff")
        private List<CutoffCommunityItem> communityWiseCutoff;

        public String getBranchCode() {
            return branchCode;
        }

        @SuppressWarnings("unused")
        public void setBranchCode(String branchCode) {
            this.branchCode = branchCode;
        }

        public String getBranchName() {
            return branchName;
        }

        @SuppressWarnings("unused")
        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public List<CutoffCommunityItem> getCommunityWiseCutoff() {
            return communityWiseCutoff;
        }

        @SuppressWarnings("unused")
        public void setCommunityWiseCutoff(List<CutoffCommunityItem> communityWiseCutoff) {
            this.communityWiseCutoff = communityWiseCutoff;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CutoffCommunityItem {
        @JsonProperty("community")
        private String community;
        @JsonProperty("cutoff")
        private CutoffDetails cutoff;

        public String getCommunity() {
            return community;
        }

        @SuppressWarnings("unused")
        public void setCommunity(String community) {
            this.community = community;
        }

        public CutoffDetails getCutoff() {
            return cutoff;
        }

        @SuppressWarnings("unused")
        public void setCutoff(CutoffDetails cutoff) {
            this.cutoff = cutoff;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CutoffDetails {
        @JsonProperty("min_mark")
        private java.math.BigDecimal minMark;

        public java.math.BigDecimal getMinMark() {
            return minMark;
        }

        @SuppressWarnings("unused")
        public void setMinMark(java.math.BigDecimal minMark) {
            this.minMark = minMark;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CollegeDataImportRoot {
        @JsonProperty("colleges")
        private List<CollegeImportItem> colleges;

        public List<CollegeImportItem> getColleges() {
            return colleges;
        }

        @SuppressWarnings("unused")
        public void setColleges(List<CollegeImportItem> colleges) {
            this.colleges = colleges;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CollegeImportItem {
        @JsonProperty("tnea_code")
        private String tneaCode;
        @JsonProperty("college_name")
        private String collegeName;

        public String getTneaCode() {
            return tneaCode;
        }

        @SuppressWarnings("unused")
        public void setTneaCode(String tneaCode) {
            this.tneaCode = tneaCode;
        }

        public String getCollegeName() {
            return collegeName;
        }

        @SuppressWarnings("unused")
        public void setCollegeName(String collegeName) {
            this.collegeName = collegeName;
        }
    }
}
