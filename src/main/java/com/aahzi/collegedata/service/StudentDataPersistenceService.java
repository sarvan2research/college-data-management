package com.aahzi.collegedata.service;

import com.aahzi.collegedata.entity.CollegeCourseData;
import com.aahzi.collegedata.entity.StudentData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
            System.out.println("Student Data saved to file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving student data to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<StudentData> loadDataFromFile() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Student Data file not found: " + filePath);
                return List.of();
            }

            List<StudentData> data = objectMapper.readValue(file, new TypeReference<List<StudentData>>() {});
            System.out.println("Student Data loaded from file: " + filePath + " (" + data.size() + " records)");
            return data;
        } catch (IOException e) {
            System.err.println("Error loading student data from file: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
}
