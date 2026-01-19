package com.aahzi.collegedata.controller;

import com.aahzi.collegedata.model.CutoffSearchResult;
import com.aahzi.collegedata.service.CutoffAnalysisOnAllotmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cutoff")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:5000", "https://aahzi.com" })
public class CutoffController {

    private final CutoffAnalysisOnAllotmentService cutoffService;
    private final com.aahzi.collegedata.service.AdmissionDataYearlyService admissionDataService;

    @Autowired
    public CutoffController(CutoffAnalysisOnAllotmentService cutoffService,
            com.aahzi.collegedata.service.AdmissionDataYearlyService admissionDataService) {
        this.cutoffService = cutoffService;
        this.admissionDataService = admissionDataService;
    }

    @GetMapping("/history")
    public ResponseEntity<com.aahzi.collegedata.model.HistoricalCutoffDTO> getHistoricalCutoff(
            @RequestParam String collegeCode,
            @RequestParam String branchCode,
            @RequestParam String community) {
        return ResponseEntity.ok(admissionDataService.getHistoricalCutoff(collegeCode, branchCode, community));
    }

    @GetMapping("/search")
    public ResponseEntity<CutoffSearchResult> searchCutoff(
            @RequestParam String collegeCode,
            @RequestParam String branchCode,
            @RequestParam String community) {
        try {
            CutoffSearchResult result = cutoffService.getCutoff(collegeCode, branchCode, community);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            // If data is not found, returning 404
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }
}
