package com.aahzi.collegedata.service;

import com.aahzi.collegedata.dto.CollegeCourseDataDTO;
import com.aahzi.collegedata.entity.CollegeCourseData;
import com.aahzi.collegedata.repository.CollegeCourseDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
            List<CollegeCourseDataDTO> result =importRawData(rawData);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
