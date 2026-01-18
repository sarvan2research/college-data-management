package com.aahzi.collegedata.config;

import com.aahzi.collegedata.service.AdmissionDataYearlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AdmissionDataYearlyService dataService;

    @Value("${app.data.import-on-startup:true}")
    private boolean importOnStartup;

    @Override
    public void run(String... args) throws Exception {
        if (importOnStartup) {
            log.info("Starting data import on startup...");
            dataService.loadDataFromFile();
            long count = dataService.getDataCount();
            log.info("Data import completed. Total records: {}", count);
        }
    }
}
