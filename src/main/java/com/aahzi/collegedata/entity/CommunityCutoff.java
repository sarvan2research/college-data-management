package com.aahzi.collegedata.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "community_cutoffs")
@Getter
@Setter
public class CommunityCutoff extends BaseEntity {

    private String community;

    @Embedded
    private CutoffStats cutoff;

    public CommunityCutoff() {
    }
}
