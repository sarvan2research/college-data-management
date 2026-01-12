package com.aahzi.collegedata.config;

import com.aahzi.collegedata.service.CutoffAnalysisOnAllotmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CutoffOnAllotmentDataLoader implements CommandLineRunner {

    private final CutoffAnalysisOnAllotmentService cutoffService;

    @Value("${app.cutoff.file-path:cutoff_analysis_data.json}")
    private String cutoffFilePath;

    @Value("${app.cutoff.import-on-startup:true}")
    private boolean importOnStartup;

    @Autowired
    public CutoffOnAllotmentDataLoader(CutoffAnalysisOnAllotmentService cutoffService) {
        this.cutoffService = cutoffService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (importOnStartup) {
            System.out.println("Starting cutoff analysis data import on startup...");
            cutoffService.loadDataFromResource(cutoffFilePath);
            // We could log count here if the service returned it or if we added a count
            // method
            System.out.println("Cutoff analysis data import process initiated.");
        }
    }
}
