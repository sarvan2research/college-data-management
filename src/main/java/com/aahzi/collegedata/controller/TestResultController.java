package com.aahzi.collegedata.controller;

import com.aahzi.collegedata.entity.TestResult;
import com.aahzi.collegedata.service.TestResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-results")
@CrossOrigin(origins = "http://localhost:5000") 
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
}
