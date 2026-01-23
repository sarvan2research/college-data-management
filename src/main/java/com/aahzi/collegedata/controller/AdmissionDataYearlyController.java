package com.aahzi.collegedata.controller;

import com.aahzi.collegedata.dto.AdmissionDataYearlyDTO;
import com.aahzi.collegedata.model.EligibleCollegeResponse;
import com.aahzi.collegedata.model.ErrorResponse;
import com.aahzi.collegedata.model.StudentEligibilityRequest;
import com.aahzi.collegedata.service.AdmissionDataYearlyService;
import com.aahzi.collegedata.service.StudentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/college-data")
@CrossOrigin(origins = { "https://internal.aahzi.com", "http://localhost:5000", "https://aahzi.com",
        "https://aahzi-2026.netlify.app" })
public class AdmissionDataYearlyController {

    @Autowired
    private AdmissionDataYearlyService dataService;

    @Autowired
    private StudentDataService studentDataService;

    @PostMapping("/import")
    public ResponseEntity<ImportResponse> importData(@RequestBody ImportRequest request) {
        try {
            List<AdmissionDataYearlyDTO> importedData = dataService.importRawData(request.getRawData(), 2024);
            ImportResponse response = new ImportResponse();
            response.setMessage("Data imported successfully");
            response.setRecordsImported(importedData.size());
            response.setData(importedData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ImportResponse response = new ImportResponse();
            response.setMessage("Error importing data: " + e.getMessage());
            response.setRecordsImported(0);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/importFile")
    public ResponseEntity<ImportResponse> importData(@RequestParam("file") MultipartFile file) {
        try {
            List<AdmissionDataYearlyDTO> importedData = dataService.importFromFile(file);
            ImportResponse response = new ImportResponse();
            response.setMessage("Data imported successfully");
            response.setRecordsImported(importedData.size());
            response.setData(importedData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ImportResponse response = new ImportResponse();
            response.setMessage("Error importing data: " + e.getMessage());
            response.setRecordsImported(0);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdmissionDataYearlyDTO>> getAllData() {
        List<AdmissionDataYearlyDTO> data = dataService.getAllData();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/college/{code}")
    public ResponseEntity<List<AdmissionDataYearlyDTO>> getDataByCollegeCode(@PathVariable String code) {
        List<AdmissionDataYearlyDTO> data = dataService.getDataByCollegeCode(code);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<AdmissionDataYearlyDTO>> getDataByYear(@PathVariable Integer year) {
        List<AdmissionDataYearlyDTO> data = dataService.getDataByYear(year);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/college/{code}/year/{year}")
    public ResponseEntity<List<AdmissionDataYearlyDTO>> getDataByCollegeCodeAndYear(
            @PathVariable String code, @PathVariable Integer year) {
        List<AdmissionDataYearlyDTO> data = dataService.getDataByCollegeCodeAndYear(code, year);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/colleges")
    public ResponseEntity<List<String>> getDistinctCollegeCodes() {
        List<String> codes = dataService.getDistinctCollegeCodes();
        return ResponseEntity.ok(codes);
    }

    @GetMapping("/years")
    public ResponseEntity<List<Integer>> getDistinctYears() {
        List<Integer> years = dataService.getDistinctYears();
        return ResponseEntity.ok(years);
    }

    @GetMapping("/search/college")
    public ResponseEntity<List<AdmissionDataYearlyDTO>> searchByCollegeName(@RequestParam String name) {
        List<AdmissionDataYearlyDTO> data = dataService.searchByCollegeName(name);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/search/course")
    public ResponseEntity<List<AdmissionDataYearlyDTO>> searchByCourseName(@RequestParam String name) {
        List<AdmissionDataYearlyDTO> data = dataService.searchByCourseName(name);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/search/details")
    public ResponseEntity<List<AdmissionDataYearlyDTO>> searchByDetails(
            @RequestParam String collegeName,
            @RequestParam String courseName,
            @RequestParam String community) {
        List<AdmissionDataYearlyDTO> data = dataService.searchByDetails(collegeName, courseName, community);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdmissionDataYearlyDTO> getDataById(@PathVariable Long id) {
        Optional<AdmissionDataYearlyDTO> data = dataService.getDataById(id);
        return data.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/count")
    public ResponseEntity<DataCountResponse> getDataCount() {
        long count = dataService.getDataCount();
        DataCountResponse response = new DataCountResponse();
        response.setTotalRecords(count);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reload")
    public ResponseEntity<ImportResponse> reloadDataFromFile() {
        try {
            dataService.loadDataFromFile();
            long count = dataService.getDataCount();
            ImportResponse response = new ImportResponse();
            response.setMessage("Data reloaded from file successfully");
            response.setRecordsImported((int) count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ImportResponse response = new ImportResponse();
            response.setMessage("Error reloading data: " + e.getMessage());
            response.setRecordsImported(0);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/eligible-colleges")
    public ResponseEntity<?> getEligibleColleges(@RequestBody StudentEligibilityRequest request) {
        try {
            List<EligibleCollegeResponse> eligibleColleges = studentDataService.getEligibleColleges(request);
            return ResponseEntity.ok(eligibleColleges);
        } catch (Exception e) {
            throw new CustomException("Error fetching eligible colleges: " + e.getMessage());
        }
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    public class CustomException extends RuntimeException {
        public CustomException(String message) {
            super(message);
        }
    }

    // Request/Response classes
    public static class ImportRequest {
        private String rawData;

        public String getRawData() {
            return rawData;
        }

        public void setRawData(String rawData) {
            this.rawData = rawData;
        }
    }

    public static class ImportResponse {
        private String message;
        private int recordsImported;
        private List<AdmissionDataYearlyDTO> data;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getRecordsImported() {
            return recordsImported;
        }

        public void setRecordsImported(int recordsImported) {
            this.recordsImported = recordsImported;
        }

        public List<AdmissionDataYearlyDTO> getData() {
            return data;
        }

        public void setData(List<AdmissionDataYearlyDTO> data) {
            this.data = data;
        }
    }

    public static class DataCountResponse {
        private long totalRecords;

        public long getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(long totalRecords) {
            this.totalRecords = totalRecords;
        }
    }
}
