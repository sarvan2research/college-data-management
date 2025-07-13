package com.aahzi.collegedata.service;

import com.aahzi.collegedata.entity.CollegeCourseData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DataParserServiceTest {

    private DataParserService dataParserService;

    @BeforeEach
    public void setup() {
        dataParserService = new DataParserService();
    }

    @Test
    public void testParseDataLine() {
        String line = "2::University Departments of Anna University Chennai - ACT Campus Sardar Patel Road Guindy Chennai 600 025::FS::FOOD TECHNOLOGY (SS)::184.5::180::182::180::::::171.5::::";
        CollegeCourseData data = dataParserService.parseDataLine(line);
        assertNotNull(data);
        assertEquals("2", data.getCollegeCode());
        assertEquals("University Departments of Anna University Chennai - ACT Campus Sardar Patel Road Guindy Chennai 600 025", data.getCollegeName());
        // Add more assertions as needed
    }

    @Test
    public void testParseDataLineWithInvalidFormat() {
        String line = "Invalid line format";
        CollegeCourseData data = dataParserService.parseDataLine(line);
        assertEquals(null, data);
    }

    @Test
    public void testParseDataLineWithEmptyLine() {
        String line = "";
        CollegeCourseData data = dataParserService.parseDataLine(line);
        assertEquals(null, data);
    }
}
