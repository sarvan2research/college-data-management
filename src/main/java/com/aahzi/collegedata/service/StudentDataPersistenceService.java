package com.aahzi.collegedata.service;

import com.aahzi.collegedata.entity.StudentData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class StudentDataPersistenceService {

    @Value("${app.student.file-path}")
    private String filePath;

    private final ObjectMapper objectMapper;

    public StudentDataPersistenceService() {
        this.objectMapper = new ObjectMapper();
    }

    public void saveDataToFile(List<StudentData> studentData) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), studentData);
            log.info("Student Data saved to file: {}", filePath);
        } catch (IOException e) {
            log.error("Error saving student data to file {}: {}", filePath, e.getMessage(), e);
        }
    }

    public List<StudentData> loadDataFromFile() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                log.warn("Student Data file not found: {}", filePath);
                return List.of();
            }

            List<StudentData> data = objectMapper.readValue(file, new TypeReference<List<StudentData>>() {
            });
            log.info("Student Data loaded from file: {} ({} records)", filePath, data.size());
            return data;
        } catch (IOException e) {
            log.error("Error loading student data from file {}: {}", filePath, e.getMessage(), e);
            return List.of();
        }
    }
}
