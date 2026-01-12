package com.aahzi.collegedata.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "course_cutoffs")
public class CourseCutoff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String branchCode;
    private String branchName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minMark", column = @Column(name = "overall_min_mark")),
            @AttributeOverride(name = "maxMark", column = @Column(name = "overall_max_mark")),
            @AttributeOverride(name = "minRank", column = @Column(name = "overall_min_rank")),
            @AttributeOverride(name = "maxRank", column = @Column(name = "overall_max_rank")),
            @AttributeOverride(name = "totalAdmissions", column = @Column(name = "overall_total_admissions"))
    })
    private CutoffStats overallCutoff;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_cutoff_id")
    private List<CommunityCutoff> communityWiseCutoff = new ArrayList<>();

    public CourseCutoff() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public CutoffStats getOverallCutoff() {
        return overallCutoff;
    }

    public void setOverallCutoff(CutoffStats overallCutoff) {
        this.overallCutoff = overallCutoff;
    }

    public List<CommunityCutoff> getCommunityWiseCutoff() {
        return communityWiseCutoff;
    }

    public void setCommunityWiseCutoff(List<CommunityCutoff> communityWiseCutoff) {
        this.communityWiseCutoff = communityWiseCutoff;
    }
}
