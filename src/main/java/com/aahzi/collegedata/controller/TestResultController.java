package com.aahzi.collegedata.controller;

import com.aahzi.collegedata.entity.TestResult;
import com.aahzi.collegedata.service.TestResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/test-results")
@CrossOrigin(origins = { "https://internal.aahzi.com/api/test-results", "https://aahzi.com", "http://localhost:5000", "https://aahzi-2026.netlify.app/api/test-results" })
public class TestResultController {

    private final TestResultService service;

    public TestResultController(TestResultService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TestResult> saveTestResult(@RequestBody TestResultService.TestResultRequest request) {
        TestResult savedResult = service.saveTestResult(request);
        return ResponseEntity.ok(savedResult);
    }

    @GetMapping
    public ResponseEntity<List<TestResult>> getAllTestResults() {
        return ResponseEntity.ok(service.getAllTestResults());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TestResult>> searchTestResults(@RequestParam String query) {
        return ResponseEntity.ok(service.searchTestResults(query));
    }
}
