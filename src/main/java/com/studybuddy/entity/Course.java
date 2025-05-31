package com.studybuddy.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "courses")
    private Set<StudyUser> students = new HashSet<>();

    public Course() {}
    public Course(String name) {
        this.name = name;
    }

    public Set<StudyUser> getStudents() {
        return students;
    }

    public void setStudents(Set<StudyUser> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
