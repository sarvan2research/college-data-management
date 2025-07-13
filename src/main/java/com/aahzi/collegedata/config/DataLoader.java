package com.aahzi.collegedata.config;

import com.aahzi.collegedata.service.CollegeCourseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private CollegeCourseDataService dataService;
    
    @Value("${app.data.import-on-startup:true}")
    private boolean importOnStartup;
    
    @Override
    public void run(String... args) throws Exception {
        if (importOnStartup) {
            System.out.println("Starting data import on startup...");
            dataService.loadDataFromFile();
            long count = dataService.getDataCount();
            System.out.println("Data import completed. Total records: " + count);
        }
    }
}
