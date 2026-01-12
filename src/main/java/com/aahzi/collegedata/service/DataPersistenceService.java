package com.aahzi.collegedata.service;

import com.aahzi.collegedata.entity.CutOffDataYearly;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DataPersistenceService {
    
    @Value("${app.data.file-path}")
    private String filePath;
    
    private final ObjectMapper objectMapper;
    
    public DataPersistenceService() {
        this.objectMapper = new ObjectMapper();
    }
    
    public void saveDataToFile(List<CutOffDataYearly> data) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
            System.out.println("Data saved to file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public List<CutOffDataYearly> loadDataFromFile() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Data file not found: " + filePath);
                return List.of();
            }
            
            List<CutOffDataYearly> data = objectMapper.readValue(file, new TypeReference<List<CutOffDataYearly>>() {});
            System.out.println("Data loaded from file: " + filePath + " (" + data.size() + " records)");
            return data;
        } catch (IOException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
}
