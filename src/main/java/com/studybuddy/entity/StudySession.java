package com.studybuddy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class StudySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Start time is required")

    private LocalDateTime startTime;
    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @NotBlank(message = "Type is required")
    private String type;
    @Size(max = 1000, message = "Reflection can't be longer than 1000 characters")
    private String reflection;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private StudyUser user;

    public StudySession() {
    }

    public StudySession(LocalDateTime startTime, LocalDateTime endTime, String type, String reflection, StudyUser user) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.reflection = reflection;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartTime(LocalDateTime startTime) {
        return this.startTime;
    }

    public LocalDateTime getEndTime(LocalDateTime endTime) {
        return this.endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReflection() {
        return reflection;
    }

    public void setReflection(String reflection) {
        this.reflection = reflection;
    }

    public StudyUser getUser() {
        return user;
    }

    public void setUser(StudyUser user) {
        this.user = user;
    }

    public long getDurationHours() {
        if (startTime != null && endTime != null) {
            return java.time.Duration.between(startTime, endTime).toHours();
        }
        return 0;
    }

    public long getDurationMinutes() {
        if (startTime != null && endTime != null) {
            return java.time.Duration.between(startTime, endTime).toMinutesPart();
        }
        return 0;
    }
}
