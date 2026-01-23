package com.aahzi.collegedata.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course_cutoffs")
@Getter
@Setter
public class CourseCutoff extends BaseEntity {

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
}
