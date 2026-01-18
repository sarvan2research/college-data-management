package com.aahzi.collegedata.service;

import com.aahzi.collegedata.entity.AdmissionDataYearly;
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
public class DataPersistenceService {

    @Value("${app.data.file-path}")
    private String filePath;

    private final ObjectMapper objectMapper;

    public DataPersistenceService() {
        this.objectMapper = new ObjectMapper();
    }

    public void saveDataToFile(List<AdmissionDataYearly> data) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
            log.info("Data saved to file: {}", filePath);
        } catch (IOException e) {
            log.error("Error saving data to file {}: {}", filePath, e.getMessage(), e);
        }
    }

    public List<AdmissionDataYearly> loadDataFromFile() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                log.warn("Data file not found: {}", filePath);
                return List.of();
            }

            List<AdmissionDataYearly> data = objectMapper.readValue(file,
                    new TypeReference<List<AdmissionDataYearly>>() {
                    });
            log.info("Data loaded from file: {} ({} records)", filePath, data.size());
            return data;
        } catch (IOException e) {
            log.error("Error loading data from file {}: {}", filePath, e.getMessage(), e);
            return List.of();
        }
    }
}
