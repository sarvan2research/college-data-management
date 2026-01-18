package com.aahzi.collegedata.service;

import com.aahzi.collegedata.entity.AdmissionDataYearly;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class AdmissionDataYearlyParserService {

    public List<AdmissionDataYearly> parseRawData(String rawData, Integer admissionYear) {
        List<AdmissionDataYearly> dataList = new ArrayList<>();

        if (rawData == null || rawData.trim().isEmpty()) {
            return dataList;
        }

        String[] lines = rawData.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty())
                continue;

            try {
                AdmissionDataYearly data = parseDataLine(line, admissionYear);
                if (data != null) {
                    dataList.add(data);
                }
            } catch (Exception e) {
                log.error("Error parsing line: {} - {}", line, e.getMessage());
            }
        }

        return dataList;
    }

    public AdmissionDataYearly parseDataLine(String line, Integer admissionYear) {
        String[] parts = line.split("::", -1);

        if (parts.length < 12) {
            log.warn("Invalid line format (expected at least 12 parts): {}", line);
            return null;
        }

        try {
            AdmissionDataYearly data = new AdmissionDataYearly();

            data.setCollegeCode(cleanString(parts[0]));
            data.setCollegeName(cleanString(parts[1]));
            data.setDistrict(extractDistrict(cleanString(parts[1])));
            data.setCourseCode(cleanString(parts[2]));
            data.setCourseName(cleanString(parts[3]).toUpperCase());
            data.setAdmissionYear(admissionYear);
            data.setCutOffOC(parseBigDecimal(parts[4]));
            data.setCutOffBC(parseBigDecimal(parts[5]));
            data.setCutOffBCM(parseBigDecimal(parts[6]));
            data.setCutOffMBC(parseBigDecimal(parts[7]));
            data.setCutOffMBCDNC(parseBigDecimal(parts[8]));
            data.setCutOffMBCV(parseBigDecimal(parts[9]));
            data.setCutOffSC(parseBigDecimal(parts[10]));
            data.setCutOffST(parseBigDecimal(parts[11]));

            if (parts.length > 12) {
                data.setCutOffSCA(parseBigDecimal(parts[12]));
            }

            return data;
        } catch (Exception e) {
            log.error("Error parsing data line: {} - {}", line, e.getMessage(), e);
            return null;
        }
    }

    public List<AdmissionDataYearly> parseRankRawData(String rawData, Integer admissionYear) {
        List<AdmissionDataYearly> dataList = new ArrayList<>();

        if (rawData == null || rawData.trim().isEmpty()) {
            return dataList;
        }

        String[] lines = rawData.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty())
                continue;

            try {
                AdmissionDataYearly data = parseRankDataLine(line, admissionYear);
                if (data != null) {
                    dataList.add(data);
                }
            } catch (Exception e) {
                log.error("Error parsing rank line: {} - {}", line, e.getMessage());
            }
        }

        return dataList;
    }

    public AdmissionDataYearly parseRankDataLine(String line, Integer admissionYear) {
        String[] parts = line.split("::", -1);

        if (parts.length < 12) {
            log.warn("Invalid rank line format (expected at least 12 parts): {}", line);
            return null;
        }

        try {
            AdmissionDataYearly data = new AdmissionDataYearly();

            data.setCollegeCode(cleanString(parts[0]));
            data.setCollegeName(cleanString(parts[1]));
            data.setDistrict(extractDistrict(cleanString(parts[1])));
            data.setCourseCode(cleanString(parts[2]));
            data.setCourseName(cleanString(parts[3]).toUpperCase());
            data.setAdmissionYear(admissionYear);
            data.setRankOC(parseInteger(parts[4]));
            data.setRankBC(parseInteger(parts[5]));
            data.setRankBCM(parseInteger(parts[6]));
            data.setRankMBC(parseInteger(parts[7]));
            data.setRankMBCDNC(parseInteger(parts[8]));
            data.setRankMBCV(parseInteger(parts[9]));
            data.setRankSC(parseInteger(parts[10]));
            data.setRankST(parseInteger(parts[11]));

            if (parts.length > 12) {
                data.setRankSCA(parseInteger(parts[12]));
            }

            return data;
        } catch (Exception e) {
            log.error("Error parsing rank data line: {} - {}", line, e.getMessage(), e);
            return null;
        }
    }

    public String extractDistrict(String collegeName) {
        // Remove any trailing punctuation or numbers
        collegeName = collegeName.replaceAll("[.,-][^\\w]*$", "");

        // Check if the college name contains a word that is followed by "Taluk &
        // District" or "Taluk and District" or "(Tk & Dt)" or "(DT)" or "(Dist)" or
        // "Distict" or "District" or "Dt." or "Dist"
        Pattern pattern = Pattern.compile(
                "(\\w+)\\s*(?:Taluk\\s*(?:&|and)\\s*District|\\(Tk\\s*&\\s*Dt\\)|\\(DT\\)|\\(Dist\\)|Distict|District|Dt\\.|Dist)",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(collegeName);
        if (matcher.find()) {
            return matcher.group(1);
        }

        // If not, try to extract the last word that is not a number
        pattern = Pattern.compile("\\b([a-zA-Z]+)\\b[^a-zA-Z]*$");
        matcher = pattern.matcher(collegeName);
        if (matcher.find()) {
            return matcher.group(1);
        }

        // If still not found, return null
        return null;
    }

    private String cleanString(String str) {
        return str != null ? str.trim() : null;
    }

    private Integer parseInteger(String str) {
        if (str == null || str.trim().isEmpty() || "0".equals(str.trim())) {
            return null;
        }
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private BigDecimal parseBigDecimal(String str) {
        if (str == null || str.trim().isEmpty() || "***".equals(str.trim())) {
            return BigDecimal.valueOf(80);
        }
        try {
            return new BigDecimal(str.trim());
        } catch (NumberFormatException e) {
            log.debug("Error parsing BigDecimal value: {}", str);
            return null;
        }
    }
}
