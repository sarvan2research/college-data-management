package com.aahzi.collegedata.service;

import com.aahzi.collegedata.dto.AdmissionDataYearlyDTO;
import com.aahzi.collegedata.entity.AdmissionDataYearly;
import com.aahzi.collegedata.repository.AdmissionDataYearlyRepository;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class AdmissionDataYearlyService {

    @Autowired
    private AdmissionDataYearlyRepository repository;

    @Autowired
    private AdmissionDataYearlyParserService parserService;

    @Autowired
    private DataPersistenceService persistenceService;

    public List<AdmissionDataYearlyDTO> importRawData(String rawData, Integer admissionYear) {
        List<AdmissionDataYearly> parsedData = parserService.parseRawData(rawData, admissionYear);

        // Save new data
        List<AdmissionDataYearly> savedData = repository.saveAll(parsedData);

        // Persist to file
        persistenceService.saveDataToFile(savedData);

        return savedData.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<AdmissionDataYearlyDTO> getAllData() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AdmissionDataYearlyDTO> getDataByCollegeCode(String collegeCode) {
        return repository.findByCollegeCode(collegeCode).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AdmissionDataYearlyDTO> getDataByYear(Integer admissionYear) {
        return repository.findByAdmissionYear(admissionYear).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AdmissionDataYearlyDTO> getDataByCollegeCodeAndYear(String collegeCode, Integer admissionYear) {
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

    public List<AdmissionDataYearlyDTO> searchByCollegeName(String name) {
        return repository.findByCollegeNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AdmissionDataYearlyDTO> searchByCourseName(String name) {
        return repository.findByCourseNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<AdmissionDataYearlyDTO> getDataById(Long id) {
        return repository.findById(id).map(this::convertToDTO);
    }

    public long getDataCount() {
        return repository.count();
    }

    public void loadDataFromFile() {
        List<AdmissionDataYearly> data = persistenceService.loadDataFromFile();
        if (!data.isEmpty()) {
            repository.deleteAll();
            repository.saveAll(data);
            log.info("Data loaded from file to database: {} records", data.size());
        }
    }

    private AdmissionDataYearlyDTO convertToDTO(AdmissionDataYearly entity) {
        AdmissionDataYearlyDTO dto = new AdmissionDataYearlyDTO();
        dto.setId(entity.getId());
        dto.setCollegeCode(entity.getCollegeCode());
        dto.setCollegeName(entity.getCollegeName());
        dto.setCourseCode(entity.getCourseCode());
        dto.setCourseName(entity.getCourseName());
        dto.setAdmissionYear(entity.getAdmissionYear());

        // Cutoff fields
        dto.setCutOffOC(entity.getCutOffOC());
        dto.setCutOffBC(entity.getCutOffBC());
        dto.setCutOffBCM(entity.getCutOffBCM());
        dto.setCutOffMBC(entity.getCutOffMBC());
        dto.setCutOffMBCDNC(entity.getCutOffMBCDNC());
        dto.setCutOffMBCV(entity.getCutOffMBCV());
        dto.setCutOffSC(entity.getCutOffSC());
        dto.setCutOffST(entity.getCutOffST());
        dto.setCutOffSCA(entity.getCutOffSCA());

        // Rank fields
        dto.setRankOC(entity.getRankOC());
        dto.setRankBC(entity.getRankBC());
        dto.setRankBCM(entity.getRankBCM());
        dto.setRankMBC(entity.getRankMBC());
        dto.setRankMBCDNC(entity.getRankMBCDNC());
        dto.setRankMBCV(entity.getRankMBCV());
        dto.setRankSC(entity.getRankSC());
        dto.setRankST(entity.getRankST());
        dto.setRankSCA(entity.getRankSCA());

        return dto;
    }

    public List<AdmissionDataYearlyDTO> importFromFile(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String rawData = reader.lines().collect(Collectors.joining("\n"));
            Integer year = extractYearFromFilename(file.getOriginalFilename());
            if (year == null)
                year = 2024;

            return importRawData(rawData, year);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processDataHelper(String path) {
        java.io.File fileOrDir = new java.io.File(path);
        if (!fileOrDir.exists()) {
            log.warn("Path does not exist: {}", path);
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
            log.warn("Unsupported file or directory: {}", path);
        }
    }

    public void processRankDataHelper(String path) {
        java.io.File fileOrDir = new java.io.File(path);
        if (!fileOrDir.exists()) {
            log.warn("Path does not exist: {}", path);
            return;
        }

        if (fileOrDir.isDirectory()) {
            java.io.File[] files = fileOrDir.listFiles();
            if (files != null) {
                for (java.io.File file : files) {
                    if (file.isFile() && (file.getName().endsWith(".json") || file.getName().endsWith(".txt"))) {
                        loadRankDataFromRawFile(file);
                    }
                }
            }
        } else if (fileOrDir.isFile()) {
            loadRankDataFromRawFile(fileOrDir);
        }
    }

    private void loadDataFromRawFile(java.io.File file) {
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            Integer admissionYear = extractYearFromFilename(file.getName());
            if (admissionYear == null)
                admissionYear = 2024;

            List<AdmissionDataYearly> entities = parserService.parseRawData(content, admissionYear);

            if (!entities.isEmpty()) {
                repository.saveAll(entities);
                log.info("Imported {} records from {} for year {}",
                        entities.size(), file.getName(), admissionYear);
            }
        } catch (Exception e) {
            log.error("Error loading data from file {}: {}", file.getName(), e.getMessage(), e);
        }
    }

    private void loadRankDataFromRawFile(java.io.File file) {
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            Integer admissionYear = extractYearFromFilename(file.getName());
            if (admissionYear == null)
                admissionYear = 2024;

            List<AdmissionDataYearly> rankEntities = parserService.parseRankRawData(content, admissionYear);

            if (!rankEntities.isEmpty()) {
                int count = 0;
                for (AdmissionDataYearly rankData : rankEntities) {
                    upsertRankData(rankData);
                    count++;
                }
                log.info("Processed {} rank records from {} for year {}",
                        count, file.getName(), admissionYear);
            }
        } catch (Exception e) {
            log.error("Error loading rank data from file {}: {}", file.getName(), e.getMessage(), e);
        }
    }

    private void upsertRankData(AdmissionDataYearly rankData) {
        Optional<AdmissionDataYearly> existingOpt = repository.findByAdmissionYearAndCollegeCodeAndCourseCode(
                rankData.getAdmissionYear(), rankData.getCollegeCode(), rankData.getCourseCode());

        AdmissionDataYearly entityToSave;
        if (existingOpt.isPresent()) {
            entityToSave = existingOpt.get();
        } else {
            entityToSave = rankData;
        }

        // Update rank fields
        entityToSave.setRankOC(rankData.getRankOC());
        entityToSave.setRankBC(rankData.getRankBC());
        entityToSave.setRankBCM(rankData.getRankBCM());
        entityToSave.setRankMBC(rankData.getRankMBC());
        entityToSave.setRankMBCDNC(rankData.getRankMBCDNC());
        entityToSave.setRankMBCV(rankData.getRankMBCV());
        entityToSave.setRankSC(rankData.getRankSC());
        entityToSave.setRankST(rankData.getRankST());
        entityToSave.setRankSCA(rankData.getRankSCA());

        repository.save(entityToSave);
    }

    private Integer extractYearFromFilename(String filename) {
        if (filename == null)
            return null;
        try {
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
