package com.aahzi.collegedata.service;

import com.aahzi.collegedata.dto.CollegeImportDTO;
import com.aahzi.collegedata.dto.CommunityImportDTO;
import com.aahzi.collegedata.dto.CourseImportDTO;
import com.aahzi.collegedata.dto.CutoffStatsDTO;
import com.aahzi.collegedata.entity.CollegeCutoff;
import com.aahzi.collegedata.entity.CommunityCutoff;
import com.aahzi.collegedata.entity.CourseCutoff;
import com.aahzi.collegedata.entity.CutoffStats;
import com.aahzi.collegedata.model.CutoffSearchResult;
import com.aahzi.collegedata.repository.CollegeCutoffRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CutoffAnalysisOnAllotmentService {

    private final CollegeCutoffRepository repository;
    private final ObjectMapper objectMapper;

    public CutoffAnalysisOnAllotmentService(CollegeCutoffRepository repository) {
        this.repository = repository;
        this.objectMapper = new ObjectMapper();
    }

    public void loadDataFromResource(String resourcePath) {
        try {
            ClassPathResource resource = new ClassPathResource(resourcePath);
            if (!resource.exists()) {
                log.warn("Cutoff Analysis data resource not found: {}", resourcePath);
                return;
            }

            var rootNode = objectMapper.readTree(resource.getInputStream());
            List<CollegeImportDTO> dtos = new ArrayList<>();

            // Check if JSON has "colleges" wrapper
            if (rootNode.has("colleges")) {
                var collegesNode = rootNode.get("colleges");
                if (collegesNode.isArray()) {
                    dtos = Arrays.asList(objectMapper.treeToValue(collegesNode, CollegeImportDTO[].class));
                }
            } else if (rootNode.isArray()) {
                // Direct array of colleges
                dtos = Arrays.asList(objectMapper.treeToValue(rootNode, CollegeImportDTO[].class));
            } else {
                // Single college object
                dtos.add(objectMapper.treeToValue(rootNode, CollegeImportDTO.class));
            }

            if (dtos.isEmpty()) {
                log.warn("No college cutoff data found in {}", resourcePath);
                return;
            }

            processImports(dtos);
            log.info("Loaded {} college cutoff records from {}", dtos.size(), resourcePath);

        } catch (IOException e) {
            log.error("Error loading cutoff data from {}: {}", resourcePath, e.getMessage(), e);
        }
    }

    private void processImports(List<CollegeImportDTO> imports) {
        List<CollegeCutoff> entities = imports.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        repository.saveAll(entities);
    }

    public CollegeCutoff saveCutoffData(CollegeCutoff entity) {
        return repository.save(entity);
    }

    public CutoffSearchResult getCutoff(String collegeCode, String courseCode, String community) {
        String trimmedCollegeCode = collegeCode != null ? collegeCode.trim() : "";
        String trimmedCourseCode = courseCode != null ? courseCode.trim() : "";
        String trimmedCommunity = community != null ? community.trim() : "";

        // Normalize community for flexible matching (e.g., MBC/DNC -> MBC_DNC)
        String normalizedCommunity = trimmedCommunity.replace("/", "_");

        Optional<CollegeCutoff> collegeOpt = repository.findByCollegeCode(trimmedCollegeCode);

        if (collegeOpt.isEmpty()) {
            log.warn("College not found with code: '{}' (original: '{}')", trimmedCollegeCode, collegeCode);
            throw new RuntimeException("College not found with code: " + trimmedCollegeCode);
        }

        CollegeCutoff college = collegeOpt.get();

        CourseCutoff course = college.getCourseWiseCutoff().stream()
                .filter(c -> c.getBranchCode().trim().equalsIgnoreCase(trimmedCourseCode))
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Course not found with code: '{}' in college: '{}'", trimmedCourseCode,
                            trimmedCollegeCode);
                    return new RuntimeException("Course not found with code: " + trimmedCourseCode);
                });

        CommunityCutoff communityCutoff = course.getCommunityWiseCutoff().stream()
                .filter(c -> {
                    String cComm = c.getCommunity().trim().replace("/", "_");
                    return cComm.equalsIgnoreCase(normalizedCommunity);
                })
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Community not found: '{}' (normalized: '{}') in college: '{}', course: '{}'",
                            trimmedCommunity, normalizedCommunity, trimmedCollegeCode, trimmedCourseCode);
                    return new RuntimeException("Community not found: " + trimmedCommunity);
                });

        return new CutoffSearchResult(
                college.getCollegeCode(),
                college.getCollegeName(),
                course.getBranchCode(),
                course.getBranchName(),
                communityCutoff.getCommunity(),
                communityCutoff.getCutoff());
    }

    private CollegeCutoff mapToEntity(CollegeImportDTO dto) {
        CollegeCutoff college = new CollegeCutoff();
        college.setCollegeCode(dto.getCollegeCode());
        college.setCollegeName(dto.getCollegeName());
        college.setGeneralCategoryCutoff(mapToEmbeddable(dto.getGeneralCategoryCutoff()));

        if (dto.getCourseWiseCutoff() != null) {
            List<CourseCutoff> courses = dto.getCourseWiseCutoff().stream()
                    .map(this::mapToCourseEntity)
                    .collect(Collectors.toList());
            college.setCourseWiseCutoff(courses);
        }
        return college;
    }

    private CourseCutoff mapToCourseEntity(CourseImportDTO dto) {
        CourseCutoff course = new CourseCutoff();
        course.setBranchCode(dto.getBranchCode());
        course.setBranchName(dto.getBranchName());
        course.setOverallCutoff(mapToEmbeddable(dto.getOverallCutoff()));

        if (dto.getCommunityWiseCutoff() != null) {
            List<CommunityCutoff> communities = dto.getCommunityWiseCutoff().stream()
                    .map(this::mapToCommunityEntity)
                    .collect(Collectors.toList());
            course.setCommunityWiseCutoff(communities);
        }
        return course;
    }

    private CommunityCutoff mapToCommunityEntity(CommunityImportDTO dto) {
        CommunityCutoff community = new CommunityCutoff();
        community.setCommunity(dto.getCommunity());
        community.setCutoff(mapToEmbeddable(dto.getCutoff()));
        return community;
    }

    private CutoffStats mapToEmbeddable(CutoffStatsDTO dto) {
        if (dto == null)
            return null;
        CutoffStats stats = new CutoffStats();
        stats.setMinMark(dto.getMinMark());
        stats.setMaxMark(dto.getMaxMark());
        stats.setMinRank(dto.getMinRank());
        stats.setMaxRank(dto.getMaxRank());
        stats.setTotalAdmissions(dto.getTotalAdmissions());
        return stats;
    }
}
