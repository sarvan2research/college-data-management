package com.aahzi.collegedata.config;

import com.aahzi.collegedata.service.CutOffDataYearlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CutOffDataYearlyLoader implements CommandLineRunner {

    private final CutOffDataYearlyService service;

    @Value("${app.cutoff.file-path}")
    private String dataFilePath;

    @Value("${app.cutoff.import-on-startup:true}")
    private boolean importOnStartup;

    @Autowired
    public CutOffDataYearlyLoader(CutOffDataYearlyService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        if (importOnStartup) {
            System.out.println("Starting cutoff data yearly import on startup from: " + dataFilePath);
            service.processDataHelper(dataFilePath);
            System.out.println("Cutoff data yearly import process completed.");
        }
    }
}
