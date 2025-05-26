package com.studybuddy.dto;

import java.time.LocalDateTime;

public class SessionDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String type;
    private String reflection;
    private String duration;

    public SessionDTO(LocalDateTime startTime, LocalDateTime endTime, String type, String reflection, String duration) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.reflection = reflection;
        this.duration = duration;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
