package com.studybuddy.dto;

import java.util.ArrayList;
import java.util.List;

public class UserJoinFormDTO {
    private List<Long> clubIds = new ArrayList<>();
    private List<Long> courseIds = new ArrayList<>();

    public List<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Long> courseIds) {
        this.courseIds = courseIds;
    }

    public List<Long> getClubIds() {
        return clubIds;
    }

    public void setClubIds(List<Long> clubIds) {
        this.clubIds = clubIds;
    }

}

