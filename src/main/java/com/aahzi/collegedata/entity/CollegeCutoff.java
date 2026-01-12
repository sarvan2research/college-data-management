package com.aahzi.collegedata.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "college_cutoffs")
public class CollegeCutoff extends BaseEntity {

    @Column(unique = true)
    private String collegeCode;

    private String collegeName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minMark", column = @Column(name = "gen_min_mark")),
            @AttributeOverride(name = "maxMark", column = @Column(name = "gen_max_mark")),
            @AttributeOverride(name = "minRank", column = @Column(name = "gen_min_rank")),
            @AttributeOverride(name = "maxRank", column = @Column(name = "gen_max_rank")),
            @AttributeOverride(name = "totalAdmissions", column = @Column(name = "gen_total_admissions"))
    })
    private CutoffStats generalCategoryCutoff;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "college_cutoff_id")
    private List<CourseCutoff> courseWiseCutoff = new ArrayList<>();

    public CollegeCutoff() {
    }

    public String getCollegeCode() {
        return collegeCode;
    }

    public void setCollegeCode(String collegeCode) {
        this.collegeCode = collegeCode;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public CutoffStats getGeneralCategoryCutoff() {
        return generalCategoryCutoff;
    }

    public void setGeneralCategoryCutoff(CutoffStats generalCategoryCutoff) {
        this.generalCategoryCutoff = generalCategoryCutoff;
    }

    public List<CourseCutoff> getCourseWiseCutoff() {
        return courseWiseCutoff;
    }

    public void setCourseWiseCutoff(List<CourseCutoff> courseWiseCutoff) {
        this.courseWiseCutoff = courseWiseCutoff;
    }
}
