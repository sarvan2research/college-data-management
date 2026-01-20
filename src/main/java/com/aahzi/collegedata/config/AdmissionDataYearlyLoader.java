package com.aahzi.collegedata.config;

import com.aahzi.collegedata.service.AdmissionDataYearlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

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

            // 0. Convert 2025 JSON to TXT if needed
            convert2025JsonToTxtIfNeeded();

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

    private void convert2025JsonToTxtIfNeeded() {
        try {
            String targetFile = basePath + File.separator + "cutoff" + File.separator + "2025_CutoffData_V2025.txt";
            File txtFile = new File(targetFile);
            if (txtFile.exists()) {
                log.info("2025 TXT file already exists. Skipping conversion.");
                return;
            }

            String sourceFile = basePath + File.separator + "CutoffanalysisBasedOnAllotment2025.json";
            File jsonFile = new File(sourceFile);
            if (!jsonFile.exists()) {
                log.warn("2025 JSON file not found at {}. Skipping conversion.", sourceFile);
                return;
            }

            log.info("Converting 2025 JSON to TXT...");
            ObjectMapper mapper = new ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(jsonFile);
            com.fasterxml.jackson.databind.JsonNode colleges = root.get("colleges");

            List<String> lines = new ArrayList<>();
            if (colleges != null && colleges.isArray()) {
                for (com.fasterxml.jackson.databind.JsonNode college : colleges) {
                    String cCode = getNodeText(college, "college_code");
                    String cName = getNodeText(college, "college_name");

                    com.fasterxml.jackson.databind.JsonNode courses = college.get("course_wise_cutoff");
                    if (courses != null && courses.isArray()) {
                        for (com.fasterxml.jackson.databind.JsonNode course : courses) {
                            String bCode = getNodeText(course, "branch_code");
                            String bName = getNodeText(course, "branch_name");

                            Map<String, String> cutoffs = new HashMap<>();
                            com.fasterxml.jackson.databind.JsonNode communities = course.get("community_wise_cutoff");
                            if (communities != null && communities.isArray()) {
                                for (com.fasterxml.jackson.databind.JsonNode comm : communities) {
                                    String commName = getNodeText(comm, "community").toUpperCase();
                                    if ("MBC/DNC".equals(commName))
                                        commName = "MBC_DNC";
                                    if ("MBCV".equals(commName))
                                        commName = "MBC_V";

                                    com.fasterxml.jackson.databind.JsonNode cutNode = comm.get("cutoff");
                                    if (cutNode != null) {
                                        String minMark = getNodeText(cutNode, "min_mark");
                                        cutoffs.put(commName, minMark);
                                    }
                                }
                            }

                            StringBuilder sb = new StringBuilder();
                            sb.append(cCode).append("::").append(cName).append("::")
                                    .append(bCode).append("::").append(bName).append("::")
                                    .append(cutoffs.getOrDefault("OC", "")).append("::")
                                    .append(cutoffs.getOrDefault("BC", "")).append("::")
                                    .append(cutoffs.getOrDefault("BCM", "")).append("::")
                                    .append(cutoffs.getOrDefault("MBC", "")).append("::")
                                    .append(cutoffs.getOrDefault("MBC_DNC", "")).append("::")
                                    .append(cutoffs.getOrDefault("MBC_V", "")).append("::")
                                    .append(cutoffs.getOrDefault("SC", "")).append("::")
                                    .append(cutoffs.getOrDefault("ST", "")).append("::")
                                    .append(cutoffs.getOrDefault("SCA", ""));

                            lines.add(sb.toString());
                        }
                    }
                }
            }

            txtFile.getParentFile().mkdirs();
            Files.write(txtFile.toPath(), lines);
            log.info("Converted {} lines to {}", lines.size(), targetFile);

        } catch (Exception e) {
            log.error("Error during 2025 JSON conversion", e);
        }
    }

    private String getNodeText(com.fasterxml.jackson.databind.JsonNode node, String field) {
        if (node.has(field) && !node.get(field).isNull()) {
            return node.get(field).asText();
        }
        return "";
    }
}
