package com.aahzi.collegedata.config;

import com.aahzi.collegedata.service.StudentDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StudentDataLoader implements CommandLineRunner {

    @Autowired
    private StudentDataService dataService;

    @Value("${app.student.import-on-startup:true}")
    private boolean importOnStartup;

    @Override
    public void run(String... args) throws Exception {
        if (importOnStartup) {
            log.info("Starting student data import on startup...");
            dataService.loadDataFromFile();
            long count = dataService.getStudentDataCount();
            log.info("Student Data import completed. Total records: {}", count);
        }
    }
}
