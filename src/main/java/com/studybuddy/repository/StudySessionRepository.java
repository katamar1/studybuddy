package com.studybuddy.repository;

import com.studybuddy.entity.StudySession;
import com.studybuddy.entity.StudyUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudySessionRepository extends CrudRepository<StudySession, Long> {

    List<StudySession> findByUser(StudyUser user);


}
