package com.aahzi.collegedata.service;

import com.aahzi.collegedata.entity.AdmissionDataYearly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdmissionDataYearlyParserServiceTest {

    private AdmissionDataYearlyParserService admissionDataYearlyParserService;

    @BeforeEach
    public void setup() {
        admissionDataYearlyParserService = new AdmissionDataYearlyParserService();
    }

    @Test
    public void testParseDataLine() {
        String line = "2::University Departments of Anna University Chennai - ACT Campus Sardar Patel Road Guindy Chennai 600 025::FS::FOOD TECHNOLOGY (SS)::184.5::180::182::180::::::171.5::::";
        AdmissionDataYearly data = admissionDataYearlyParserService.parseDataLine(line, 2024);
        assertNotNull(data);
        assertEquals("2", data.getCollegeCode());
        assertEquals(
                "University Departments of Anna University Chennai - ACT Campus Sardar Patel Road Guindy Chennai 600 025",
                data.getCollegeName());
    }

    @Test
    public void testParseRankDataLine() {
        String line = "1::University Departments of Anna University Chennai - CEG Campus::CS::COMPUTER SCIENCE AND ENGINEERING::100::200::300::400::500::600::700::800::900";
        AdmissionDataYearly data = admissionDataYearlyParserService.parseRankDataLine(line, 2024);
        assertNotNull(data);
        assertEquals("1", data.getCollegeCode());
        assertEquals(100, data.getRankOC());
        assertEquals(200, data.getRankBC());
        assertEquals(300, data.getRankBCM());
        assertEquals(400, data.getRankMBC());
        assertEquals(500, data.getRankMBCDNC());
        assertEquals(600, data.getRankMBCV());
        assertEquals(700, data.getRankSC());
        assertEquals(800, data.getRankST());
        assertEquals(900, data.getRankSCA());
    }

    @Test
    public void testParseDataLineWithInvalidFormat() {
        String line = "Invalid line format";
        AdmissionDataYearly data = admissionDataYearlyParserService.parseDataLine(line, 2024);
        assertEquals(null, data);
    }

    @Test
    public void testParseDataLineWithEmptyLine() {
        String line = "";
        AdmissionDataYearly data = admissionDataYearlyParserService.parseDataLine(line, 2024);
        assertEquals(null, data);
    }
}
