package com.aahzi.collegedata.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "community_cutoffs")
public class CommunityCutoff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String community;

    @Embedded
    private CutoffStats cutoff;

    public CommunityCutoff() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public CutoffStats getCutoff() {
        return cutoff;
    }

    public void setCutoff(CutoffStats cutoff) {
        this.cutoff = cutoff;
    }
}
