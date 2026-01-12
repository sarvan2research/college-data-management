package com.aahzi.collegedata.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(LocalDateTime timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
