package com.aahzi.collegedata.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_seq")
    @SequenceGenerator(name = "base_seq", sequenceName = "base_sequence", allocationSize = 50)
    protected Long id;

    @Column(name = "time_created", updatable = false)
    protected LocalDateTime timeCreated;

    @Column(name = "time_updated")
    protected LocalDateTime timeUpdated;

    @Version
    protected Integer version;

    @PrePersist
    protected void onCreate() {
        timeCreated = LocalDateTime.now();
        timeUpdated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        timeUpdated = LocalDateTime.now();
    }
}
