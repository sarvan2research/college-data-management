package com.aahzi.collegedata.config;

import com.aahzi.collegedata.service.CollegeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CollegeDetailsDataLoader implements CommandLineRunner {

    private final CollegeDetailsService service;

    @Value("${app.college-details.file-path:data/college_details.json}")
    private String dataFilePath;

    @Value("${app.college-details.import-on-startup:true}")
    private boolean importOnStartup;

    @Autowired
    public CollegeDetailsDataLoader(CollegeDetailsService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        if (importOnStartup) {
            System.out.println("Starting college details data import on startup...");
            service.loadDataFromResource(dataFilePath);
            System.out.println("College details data import process completed.");
        }
    }
}
