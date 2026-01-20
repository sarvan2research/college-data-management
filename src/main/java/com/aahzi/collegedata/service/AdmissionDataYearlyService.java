package com.aahzi.collegedata.service;

import com.aahzi.collegedata.dto.AdmissionDataYearlyDTO;
import com.aahzi.collegedata.entity.*;
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

    public List<AdmissionDataYearlyDTO> searchByDetails(String collegeName, String courseName, String community) {
        return repository
                .searchByCollegeAndCourseAndYearRange(
                        collegeName, courseName, 2021)
                .stream()
                .filter(entity -> hasValidCutoff(entity, community))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private boolean hasValidCutoff(AdmissionDataYearly entity, String community) {
        if (community == null || community.trim().isEmpty()) {
            return true;
        }
        switch (community.toUpperCase().replace("/", "_")) {
            case "OC":
                return entity.getCutOffOC() != null;
            case "BC":
                return entity.getCutOffBC() != null;
            case "BCM":
                return entity.getCutOffBCM() != null;
            case "MBC":
                return entity.getCutOffMBC() != null;
            case "MBC_DNC":
                return entity.getCutOffMBCDNC() != null;
            case "MBC_V":
            case "MBCV":
                return entity.getCutOffMBCV() != null;
            case "SC":
                return entity.getCutOffSC() != null;
            case "SCA":
                return entity.getCutOffSCA() != null;
            case "ST":
                return entity.getCutOffST() != null;
            default:
                return true;
        }
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

    @Autowired
    private CutoffAnalysisOnAllotmentService cutoffAnalysisOnAllotmentService;

    public com.aahzi.collegedata.model.HistoricalCutoffDTO getHistoricalCutoff(String collegeCode, String courseCode,
            String community) {
        if (community == null || community.trim().isEmpty()) {
            throw new IllegalArgumentException("Community cannot be empty");
        }

        String trimmedCollegeCode = collegeCode != null ? collegeCode.trim() : "";
        String trimmedCourseCode = courseCode != null ? courseCode.trim() : "";
        String trimmedCommunity = community != null ? community.trim() : "";

        List<AdmissionDataYearly> allYearsData = repository.findByCollegeCodeAndCourseCode(trimmedCollegeCode,
                trimmedCourseCode);

        // Filter and map data for each year
        List<com.aahzi.collegedata.model.CutoffYearlyData> yearWiseData = allYearsData.stream()
                .filter(data -> data.getAdmissionYear() >= 2021 && data.getAdmissionYear() <= 2024) // We want
                                                                                                    // historical data
                .map(data -> {
                    com.aahzi.collegedata.model.CutoffYearlyData yearlyData = new com.aahzi.collegedata.model.CutoffYearlyData();
                    yearlyData.setYear(data.getAdmissionYear());
                    yearlyData.setAvailable(true);

                    String comm = trimmedCommunity.toUpperCase().replace("/", "_");

                    // Map based on community
                    if ("OC".equals(comm)) {
                        yearlyData.setCutoffMark(data.getCutOffOC());
                        yearlyData.setClosingRank(data.getRankOC());
                        yearlyData.setMaxMark(data.getCutOffOC());
                        yearlyData.setMinMark(data.getCutOffOC());
                        yearlyData.setMaxRank(data.getRankOC());
                        yearlyData.setMinRank(data.getRankOC());
                    } else if ("BC".equals(comm)) {
                        yearlyData.setCutoffMark(data.getCutOffBC());
                        yearlyData.setClosingRank(data.getRankBC());
                        yearlyData.setMaxMark(data.getCutOffBC());
                        yearlyData.setMinMark(data.getCutOffBC());
                        yearlyData.setMaxRank(data.getRankBC());
                        yearlyData.setMinRank(data.getRankBC());
                    } else if ("BCM".equals(comm)) {
                        yearlyData.setCutoffMark(data.getCutOffBCM());
                        yearlyData.setClosingRank(data.getRankBCM());
                        yearlyData.setMaxMark(data.getCutOffBCM());
                        yearlyData.setMinMark(data.getCutOffBCM());
                        yearlyData.setMaxRank(data.getRankBCM());
                        yearlyData.setMinRank(data.getRankBCM());
                    } else if ("MBC".equals(comm)) {
                        yearlyData.setCutoffMark(data.getCutOffMBC());
                        yearlyData.setClosingRank(data.getRankMBC());
                        yearlyData.setMaxMark(data.getCutOffMBC());
                        yearlyData.setMinMark(data.getCutOffMBC());
                        yearlyData.setMaxRank(data.getRankMBC());
                        yearlyData.setMinRank(data.getRankMBC());
                    } else if ("MBC_DNC".equals(comm)) { // Handle variation if any
                        yearlyData.setCutoffMark(data.getCutOffMBCDNC());
                        yearlyData.setClosingRank(data.getRankMBCDNC());
                        yearlyData.setMaxMark(data.getCutOffMBCDNC());
                        yearlyData.setMinMark(data.getCutOffMBCDNC());
                        yearlyData.setMaxRank(data.getRankMBCDNC());
                        yearlyData.setMinRank(data.getRankMBCDNC());
                    } else if ("MBC_V".equals(comm) || "MBCV".equals(comm)) {
                        yearlyData.setCutoffMark(data.getCutOffMBCV());
                        yearlyData.setClosingRank(data.getRankMBCV());
                        yearlyData.setMaxMark(data.getCutOffMBCV());
                        yearlyData.setMinMark(data.getCutOffMBCV());
                        yearlyData.setMaxRank(data.getRankMBCV());
                        yearlyData.setMinRank(data.getRankMBCV());
                    } else if ("SC".equals(comm)) {
                        yearlyData.setCutoffMark(data.getCutOffSC());
                        yearlyData.setClosingRank(data.getRankSC());
                        yearlyData.setMaxMark(data.getCutOffSC());
                        yearlyData.setMinMark(data.getCutOffSC());
                        yearlyData.setMaxRank(data.getRankSC());
                        yearlyData.setMinRank(data.getRankSC());
                    } else if ("SCA".equals(comm)) {
                        yearlyData.setCutoffMark(data.getCutOffSCA());
                        yearlyData.setClosingRank(data.getRankSCA());
                        yearlyData.setMaxMark(data.getCutOffSCA());
                        yearlyData.setMinMark(data.getCutOffSCA());
                        yearlyData.setMaxRank(data.getRankSCA());
                        yearlyData.setMinRank(data.getRankSCA());
                    } else if ("ST".equals(comm)) {
                        yearlyData.setCutoffMark(data.getCutOffST());
                        yearlyData.setClosingRank(data.getRankST());
                        yearlyData.setMaxMark(data.getCutOffST());
                        yearlyData.setMinMark(data.getCutOffST());
                        yearlyData.setMaxRank(data.getRankST());
                        yearlyData.setMinRank(data.getRankST());
                    }

                    if (yearlyData.getCutoffMark() == null && yearlyData.getClosingRank() == null) {
                        yearlyData.setAvailable(false);
                    }

                    return yearlyData;
                })
                .collect(Collectors.toList());

        // Fetch and append 2025 Data
        try {
            com.aahzi.collegedata.model.CutoffSearchResult result2025 = cutoffAnalysisOnAllotmentService
                    .getCutoff(collegeCode, courseCode, community);
            if (result2025 != null && result2025.getCutoffStats() != null) {
                com.aahzi.collegedata.model.CutoffYearlyData yearlyData2025 = new com.aahzi.collegedata.model.CutoffYearlyData();
                yearlyData2025.setYear(2025);
                yearlyData2025.setAvailable(true);

                com.aahzi.collegedata.entity.CutoffStats stats = result2025.getCutoffStats();

                // Helper to convert Double to BigDecimal safely
                java.math.BigDecimal maxMark = stats.getMaxMark() != null
                        ? java.math.BigDecimal.valueOf(stats.getMaxMark())
                        : null;
                java.math.BigDecimal minMark = stats.getMinMark() != null
                        ? java.math.BigDecimal.valueOf(stats.getMinMark())
                        : null;

                yearlyData2025.setCutoffMark(maxMark); // defaulting main field to max
                yearlyData2025.setClosingRank(stats.getMaxRank()); // defaulting main field to max

                yearlyData2025.setMinMark(minMark);
                yearlyData2025.setMaxMark(maxMark);
                yearlyData2025.setMinRank(stats.getMinRank());
                yearlyData2025.setMaxRank(stats.getMaxRank());

                yearWiseData.add(yearlyData2025);
            }
        } catch (Exception e) {
            log.warn("Could not fetch 2025 data for {} {} {}: {}", collegeCode, courseCode, community, e.getMessage());
        }

        com.aahzi.collegedata.model.HistoricalCutoffDTO result = new com.aahzi.collegedata.model.HistoricalCutoffDTO();
        result.setCollegeCode(collegeCode);
        result.setBranchCode(courseCode);
        result.setCommunity(community);
        result.setYearlyData(yearWiseData);

        return result;
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
