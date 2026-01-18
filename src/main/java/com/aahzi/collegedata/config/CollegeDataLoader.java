package com.aahzi.collegedata.config;

import com.aahzi.collegedata.service.AdmissionDataYearlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/*
TODO sarvan
This class currently load values from cutoff_analysis_result and college-data
Its been already getting from another class.
*/
@Slf4j
@Component
public class CollegeDataLoader implements CommandLineRunner {

    @Autowired
    private AdmissionDataYearlyService dataService;

    @Value("${app.data.import-on-startup:true}")
    private boolean importOnStartup;

    @Override
    public void run(String... args) throws Exception {
        if (importOnStartup) {
            log.info("Starting college data import on startup...");
            // dataService.loadCutoffData("data/cutoff_analysis_result.json",
            // "data/college-data.json");
            // long count = dataService.getDataCount();
            // log.info("College Data import completed. Total records: {}", count);
        }
    }
}
