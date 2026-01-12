package com.aahzi.collegedata.service;

import com.aahzi.collegedata.entity.CutOffDataYearly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CutOffDataYearlyParserServiceTest {

    private CutOffDataYearlyParserService cutOffDataYearlyParserService;

    @BeforeEach
    public void setup() {
        cutOffDataYearlyParserService = new CutOffDataYearlyParserService();
    }

    @Test
    public void testParseDataLine() {
        String line = "2::University Departments of Anna University Chennai - ACT Campus Sardar Patel Road Guindy Chennai 600 025::FS::FOOD TECHNOLOGY (SS)::184.5::180::182::180::::::171.5::::";
        CutOffDataYearly data = cutOffDataYearlyParserService.parseDataLine(line, 2024);
        assertNotNull(data);
        assertEquals("2", data.getCollegeCode());
        assertEquals(
                "University Departments of Anna University Chennai - ACT Campus Sardar Patel Road Guindy Chennai 600 025",
                data.getCollegeName());
        // Add more assertions as needed
    }

    @Test
    public void testParseDataLineWithInvalidFormat() {
        String line = "Invalid line format";
        CutOffDataYearly data = cutOffDataYearlyParserService.parseDataLine(line, 2024);
        assertEquals(null, data);
    }

    @Test
    public void testParseDataLineWithEmptyLine() {
        String line = "";
        CutOffDataYearly data = cutOffDataYearlyParserService.parseDataLine(line, 2024);
        assertEquals(null, data);
    }
}
