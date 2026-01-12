package com.aahzi.collegedata.service;

import com.aahzi.collegedata.dto.CutOffDataYearlyDTO;
import com.aahzi.collegedata.entity.CutOffDataYearly;
import com.aahzi.collegedata.repository.CutOffDataYearlyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CutOffDataYearlyService {

    @Autowired
    private CutOffDataYearlyRepository repository;

    @Autowired
    private CutOffDataYearlyParserService parserService;

    @Autowired
    private DataPersistenceService persistenceService;

    public List<CutOffDataYearlyDTO> importRawData(String rawData, Integer admissionYear) {
        List<CutOffDataYearly> parsedData = parserService.parseRawData(rawData, admissionYear);

        // Clear existing data (Be cautious if appending from multiple files)
        // repository.deleteAll();

        // Save new data
        List<CutOffDataYearly> savedData = repository.saveAll(parsedData);

        // Persist to file
        persistenceService.saveDataToFile(savedData);

        return savedData.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<CutOffDataYearlyDTO> getAllData() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CutOffDataYearlyDTO> getDataByCollegeCode(String collegeCode) {
        return repository.findByCollegeCode(collegeCode).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CutOffDataYearlyDTO> getDataByYear(Integer admissionYear) {
        return repository.findByAdmissionYear(admissionYear).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CutOffDataYearlyDTO> getDataByCollegeCodeAndYear(String collegeCode, Integer admissionYear) {
        return repository.findByCollegeCodeAndAdmissionYear(collegeCode, admissionYear).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<String> getDistinctCollegeCodes() {
        return repository.findDistinctCollegeCodes();
    }

    public List<Integer> getDistinctYears() {
        return repository.findDistinctAdmissionYears();
    }

    public List<CutOffDataYearlyDTO> searchByCollegeName(String name) {
        return repository.findByCollegeNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CutOffDataYearlyDTO> searchByCourseName(String name) {
        return repository.findByCourseNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CutOffDataYearlyDTO> getDataById(Long id) {
        return repository.findById(id).map(this::convertToDTO);
    }

    public long getDataCount() {
        return repository.count();
    }

    public void loadDataFromFile() {
        List<CutOffDataYearly> data = persistenceService.loadDataFromFile();
        if (!data.isEmpty()) {
            repository.deleteAll();
            repository.saveAll(data);
            System.out.println("Data loaded from file to database: " + data.size() + " records");
        }
    }

    private CutOffDataYearlyDTO convertToDTO(CutOffDataYearly entity) {
        CutOffDataYearlyDTO dto = new CutOffDataYearlyDTO();
        dto.setId(entity.getId());
        dto.setCollegeCode(entity.getCollegeCode());
        dto.setCollegeName(entity.getCollegeName());
        dto.setCourseCode(entity.getCourseCode());
        dto.setCourseName(entity.getCourseName());
        dto.setAdmissionYear(entity.getAdmissionYear());
        dto.setCutOffOC(entity.getCutOffOC());
        dto.setCutOffBC(entity.getCutOffBC());
        dto.setCutOffBCM(entity.getCutOffBCM());
        dto.setCutOffMBC(entity.getCutOffMBC());
        dto.setCutOffMBCDNC(entity.getCutOffMBCDNC());
        dto.setCutOffMBCV(entity.getCutOffMBCV());
        dto.setCutOffSC(entity.getCutOffSC());
        dto.setCutOffST(entity.getCutOffST());
        dto.setCutOffSCA(entity.getCutOffSCA());
        return dto;
    }

    public List<CutOffDataYearlyDTO> importFromFile(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String rawData = reader.lines().collect(Collectors.joining("\n"));
            // Defaulting to 2024 if not provided for now, or could try to extract from
            // original filename if available
            Integer year = extractYearFromFilename(file.getOriginalFilename());
            if (year == null)
                year = 2024;

            List<CutOffDataYearlyDTO> result = importRawData(rawData, year);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processDataHelper(String path) {
        java.io.File fileOrDir = new java.io.File(path);
        if (!fileOrDir.exists()) {
            System.out.println("Path does not exist: " + path);
            return;
        }

        if (fileOrDir.isDirectory()) {
            java.io.File[] files = fileOrDir.listFiles();
            if (files != null) {
                for (java.io.File file : files) {
                    if (file.isFile() && (file.getName().endsWith(".json") || file.getName().endsWith(".txt"))) {
                        loadDataFromRawFile(file);
                    }
                }
            }
        } else if (fileOrDir.isFile()) {
            loadDataFromRawFile(fileOrDir);
        } else {
            System.out.println("Unsupported file or directory: " + path);
        }
    }

    private void loadDataFromRawFile(java.io.File file) {
        try {
            // Read file content as String
            String content = new String(Files.readAllBytes(file.toPath()));

            // Extract admission year from filename (e.g., 2022_CuttData_V2025.txt -> 2022)
            Integer admissionYear = extractYearFromFilename(file.getName());
            if (admissionYear == null) {
                admissionYear = 2024; // Default if not found
                System.out.println(
                        "Could not extract year from filename " + file.getName() + ", using default: " + admissionYear);
            }

            // Parse using the existing parser service which handles raw text
            List<CutOffDataYearly> entities = parserService.parseRawData(content, admissionYear);

            if (!entities.isEmpty()) {
                repository.saveAll(entities);
                System.out.println("Imported " + entities.size() + " records from " + file.getName() + " for year "
                        + admissionYear);
            }
        } catch (Exception e) {
            System.err.println("Error loading data from file " + file.getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Integer extractYearFromFilename(String filename) {
        if (filename == null)
            return null;
        try {
            // Check if filename starts with 4 digits
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^(\\d{4})_");
            java.util.regex.Matcher matcher = pattern.matcher(filename);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        } catch (Exception e) {
            // Ignore
        }
        return null;
    }
}
