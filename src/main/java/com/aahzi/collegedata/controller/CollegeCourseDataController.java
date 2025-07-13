package com.aahzi.collegedata.controller;

import com.aahzi.collegedata.dto.CollegeCourseDataDTO;
import com.aahzi.collegedata.entity.CollegeCourseData;
import com.aahzi.collegedata.model.EligibleCollegeResponse;
import com.aahzi.collegedata.model.ErrorResponse;
import com.aahzi.collegedata.model.StudentEligibilityRequest;
import com.aahzi.collegedata.service.CollegeCourseDataService;
import com.aahzi.collegedata.service.StudentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/college-data")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173","https://aahzi.com"})
public class CollegeCourseDataController {
    
    @Autowired
    private CollegeCourseDataService dataService;

    @Autowired
    private StudentDataService studentDataService;

    @PostMapping("/import")
    public ResponseEntity<ImportResponse> importData(@RequestBody ImportRequest request) {
        try {
            List<CollegeCourseDataDTO> importedData = dataService.importRawData(request.getRawData());
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
            List<CollegeCourseDataDTO> importedData =  dataService.importFromFile(file);
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
    public ResponseEntity<List<CollegeCourseDataDTO>> getAllData() {
        List<CollegeCourseDataDTO> data = dataService.getAllData();
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/college/{code}")
    public ResponseEntity<List<CollegeCourseDataDTO>> getDataByCollegeCode(@PathVariable String code) {
        List<CollegeCourseDataDTO> data = dataService.getDataByCollegeCode(code);
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/year/{year}")
    public ResponseEntity<List<CollegeCourseDataDTO>> getDataByYear(@PathVariable Integer year) {
        List<CollegeCourseDataDTO> data = dataService.getDataByYear(year);
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/college/{code}/year/{year}")
    public ResponseEntity<List<CollegeCourseDataDTO>> getDataByCollegeCodeAndYear(
            @PathVariable String code, @PathVariable Integer year) {
        List<CollegeCourseDataDTO> data = dataService.getDataByCollegeCodeAndYear(code, year);
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
    public ResponseEntity<List<CollegeCourseDataDTO>> searchByCollegeName(@RequestParam String name) {
        List<CollegeCourseDataDTO> data = dataService.searchByCollegeName(name);
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/search/course")
    public ResponseEntity<List<CollegeCourseDataDTO>> searchByCourseName(@RequestParam String name) {
        List<CollegeCourseDataDTO> data = dataService.searchByCourseName(name);
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CollegeCourseDataDTO> getDataById(@PathVariable Long id) {
        Optional<CollegeCourseDataDTO> data = dataService.getDataById(id);
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
        
        public String getRawData() { return rawData; }
        public void setRawData(String rawData) { this.rawData = rawData; }
    }
    
    public static class ImportResponse {
        private String message;
        private int recordsImported;
        private List<CollegeCourseDataDTO> data;
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        
        public int getRecordsImported() { return recordsImported; }
        public void setRecordsImported(int recordsImported) { this.recordsImported = recordsImported; }
        
        public List<CollegeCourseDataDTO> getData() { return data; }
        public void setData(List<CollegeCourseDataDTO> data) { this.data = data; }
    }
    
    public static class DataCountResponse {
        private long totalRecords;
        
        public long getTotalRecords() { return totalRecords; }
        public void setTotalRecords(long totalRecords) { this.totalRecords = totalRecords; }
    }
}
