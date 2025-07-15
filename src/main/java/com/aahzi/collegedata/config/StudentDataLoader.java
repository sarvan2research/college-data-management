package com.aahzi.collegedata.config;

import com.aahzi.collegedata.service.StudentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StudentDataLoader implements CommandLineRunner {
    
    @Autowired
    private StudentDataService dataService;
    
    @Value("${app.student.import-on-startup:true}")
    private boolean importOnStartup;
    
    @Override
    public void run(String... args) throws Exception {
        if (importOnStartup) {
            System.out.println("Starting student data import on startup...");
            dataService.loadDataFromFile();
            long count = dataService.getStudentDataCount();
            System.out.println("Student Data import completed. Total records: " + count);
        }
    }
}
