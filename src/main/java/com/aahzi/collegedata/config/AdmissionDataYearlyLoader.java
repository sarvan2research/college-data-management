package com.aahzi.collegedata.config;

import com.aahzi.collegedata.service.AdmissionDataYearlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
@Order(1)
public class AdmissionDataYearlyLoader implements CommandLineRunner {

    private final AdmissionDataYearlyService service;

    @Value("${app.admission.base-path}")
    private String basePath;

    @Value("${app.admission.import-on-startup:true}")
    private boolean importOnStartup;

    @Autowired
    public AdmissionDataYearlyLoader(AdmissionDataYearlyService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        if (importOnStartup) {
            log.info("Starting consolidated admission data import from base path: {}", basePath);

            // 1. Process Cutoff data first to create records
            String cutoffPath = basePath + File.separator + "cutoff";
            log.info("Processing cutoff data from: {}", cutoffPath);
            service.processDataHelper(cutoffPath);

            // 2. Process Rank data second to update existing records (normalization/upsert)
            String rankPath = basePath + File.separator + "rank";
            log.info("Processing rank data from: {}", rankPath);
            service.processRankDataHelper(rankPath);

            log.info("Consolidated admission data import process completed.");
        }
    }
}
