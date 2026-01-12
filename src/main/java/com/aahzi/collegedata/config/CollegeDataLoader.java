package com.aahzi.collegedata.config;

import com.aahzi.collegedata.service.CutOffDataYearlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/*
TODO sarvan
This class currently load values from cutoff_analysis_result and college-data
Its been already getting from another class.
*/
@Component
public class CollegeDataLoader implements CommandLineRunner {

    @Autowired
    private CutOffDataYearlyService dataService;

    @Value("${app.data.import-on-startup:true}")
    private boolean importOnStartup;

    @Override
    public void run(String... args) throws Exception {
        if (importOnStartup) {
            System.out.println("Starting college data import on startup...");
            //dataService.loadCutoffData("data/cutoff_analysis_result.json", "data/college-data.json");
            //long count = dataService.getDataCount();
            //System.out.println("College Data import completed. Total records: " + count);
        }
    }
}
