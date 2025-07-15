package com.aahzi.collegedata.service;

import com.aahzi.collegedata.entity.CollegeCourseData;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DataParserService {
    
    public List<CollegeCourseData> parseRawData(String rawData) {
        List<CollegeCourseData> dataList = new ArrayList<>();
        
        if (rawData == null || rawData.trim().isEmpty()) {
            return dataList;
        }
        
        String[] lines = rawData.split("\n");
        
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            
            try {
                CollegeCourseData data = parseDataLine(line);
                if (data != null) {
                    dataList.add(data);
                }
            } catch (Exception e) {
                System.err.println("Error parsing line: " + line);
                System.err.println("Error: " + e.getMessage());
            }
        }
        
        return dataList;
    }
    
    public CollegeCourseData parseDataLine(String line) {
        String[] parts = line.split("::", -1);
        
        if (parts.length < 12) {
            System.err.println("Invalid line format (expected at least 12 parts): " + line);
            return null;
        }
        
        try {
            CollegeCourseData data = new CollegeCourseData();
            
            data.setCollegeCode(cleanString(parts[0]));
            data.setCollegeName(cleanString(parts[1]));
            data.setDistrict(extractDistrict(cleanString(parts[1])));
            data.setCourseCode(cleanString(parts[2]));
            data.setCourseName(cleanString(parts[3]).toUpperCase());
            data.setAdmissionYear(2024);
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
            System.err.println("Error parsing data line: " + line);
            e.printStackTrace();
            return null;
        }
    }

    private String extractDistrict(String collegeName) {
        // Remove any trailing punctuation or numbers
        collegeName = collegeName.replaceAll("[.,-][^\\w]*$", "");

        // Check if the college name contains a word that is followed by "Taluk & District" or "Taluk and District" or "(Tk & Dt)" or "(DT)" or "(Dist)" or "Distict" or "District" or "Dt." or "Dist"
        Pattern pattern = Pattern.compile("(\\w+)\\s*(?:Taluk\\s*(?:&|and)\\s*District|\\(Tk\\s*&\\s*Dt\\)|\\(DT\\)|\\(Dist\\)|Distict|District|Dt\\.|Dist)", Pattern.CASE_INSENSITIVE);
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

    private boolean isDistrictName(String name) {
        // Implement a simple check to determine if the name is a district name
        // For example, check if it's not a number and has a certain length
        try {
            Integer.parseInt(name);
            return false; // If it's a number, it's likely not a district name
        } catch (NumberFormatException e) {
            return true; // If it's not a number, it could be a district name
        }
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
            System.out.println("Error parsing data line: " + str);
            return null;
        }
    }
}
