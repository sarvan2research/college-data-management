package com.aahzi.collegedata.service;

import com.aahzi.collegedata.dto.CollegeRootDTO;
import com.aahzi.collegedata.dto.CollegeWrapperDTO;
import com.aahzi.collegedata.dto.CollegeInfoDTO;
import com.aahzi.collegedata.dto.CourseInfoDTO;
import com.aahzi.collegedata.dto.HostelInfoDTO;
import com.aahzi.collegedata.entity.CollegeDetails;
import com.aahzi.collegedata.entity.CourseDetails;
import com.aahzi.collegedata.entity.HostelDetails;
import com.aahzi.collegedata.repository.CollegeDetailsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CollegeDetailsService {

    private final CollegeDetailsRepository repository;
    private final ObjectMapper objectMapper;

    public CollegeDetailsService(CollegeDetailsRepository repository) {
        this.repository = repository;
        this.objectMapper = new ObjectMapper();
    }

    public void loadDataFromResource(String resourcePath) {
        try {
            ClassPathResource resource = new ClassPathResource(resourcePath);
            if (!resource.exists()) {
                log.warn("College Details data resource not found: {}", resourcePath);
                return;
            }

            try (InputStream inputStream = resource.getInputStream()) {
                CollegeRootDTO rootDTO = objectMapper.readValue(inputStream, CollegeRootDTO.class);
                if (rootDTO != null && rootDTO.getColleges() != null) {
                    processImports(rootDTO.getColleges());
                    log.info("Loaded {} college details records from {}",
                            rootDTO.getColleges().size(), resourcePath);
                }
            }
        } catch (IOException e) {
            log.error("Error loading college details data from {}: {}", resourcePath, e.getMessage(), e);
        }
    }

    private void processImports(List<CollegeWrapperDTO> imports) {
        List<CollegeDetails> entities = imports.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        repository.saveAll(entities);
    }

    private CollegeDetails mapToEntity(CollegeWrapperDTO dto) {
        CollegeDetails entity = new CollegeDetails();
        CollegeInfoDTO info = dto.getCollegeDetails();

        if (info != null) {
            entity.setCollegeCode(info.getCollegeCode());
            entity.setCollegeName(info.getCollegeName());
            entity.setDeanPrincipal(info.getDeanPrincipal());
            entity.setAddress(info.getAddress());
            entity.setTaluk(info.getTaluk());
            entity.setDistrict(info.getDistrict());
            entity.setPincode(info.getPincode());
            entity.setPhoneFax(info.getPhoneFax());
            entity.setEmailId(info.getEmailId());
            entity.setWebsite(info.getWebsite());
            entity.setAntiRaggingPhone(info.getAntiRaggingPhone());
            entity.setPlacementPercentage(info.getPlacementPercentage());
            entity.setBankAccountNo(info.getBankAccountNo());
            entity.setBankName(info.getBankName());
            entity.setDistanceFromDistrictHqKm(info.getDistanceFromDistrictHqKm());
            entity.setNearestRailwayStation(info.getNearestRailwayStation());
            entity.setDistanceFromRailwayStationKm(info.getDistanceFromRailwayStationKm());
            entity.setMinorityStatus(info.getMinorityStatus());
            entity.setAutonomousStatus(info.getAutonomousStatus());
        }

        if (dto.getCourseDetails() != null) {
            List<CourseDetails> courses = dto.getCourseDetails().stream()
                    .map(courseDto -> mapToCourseEntity(courseDto, entity))
                    .collect(Collectors.toList());
            entity.setCourseDetails(courses);
        }

        if (dto.getHostelDetails() != null) {
            List<HostelDetails> hostels = dto.getHostelDetails().stream()
                    .map(hostelDto -> mapToHostelEntity(hostelDto, entity))
                    .collect(Collectors.toList());
            entity.setHostelDetails(hostels);
        }

        return entity;
    }

    private CourseDetails mapToCourseEntity(CourseInfoDTO dto, CollegeDetails parent) {
        CourseDetails entity = new CourseDetails();
        entity.setSlNo(dto.getSlNo());
        entity.setBranchCode(dto.getBranchCode());
        entity.setApprovedIntake(dto.getApprovedIntake());
        entity.setYearOfStarting(dto.getYearOfStarting());
        entity.setNbaAccredited(dto.getNbaAccredited());
        entity.setAccreditationValidUpto(dto.getAccreditationValidUpto());
        entity.setCollegeDetails(parent);
        return entity;
    }

    private HostelDetails mapToHostelEntity(HostelInfoDTO dto, CollegeDetails parent) {
        HostelDetails entity = new HostelDetails();
        entity.setFacilityType(dto.getFacilityType());
        entity.setBoys(dto.getBoys());
        entity.setGirls(dto.getGirls());
        entity.setDescription(dto.getDescription());
        entity.setCollegeDetails(parent);
        return entity;
    }
}
