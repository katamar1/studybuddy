package com.studybuddy.repository;


import java.util.List;
import java.util.Optional;

import com.studybuddy.entity.StudyUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<StudyUser, Long> {

    List<StudyUser> findByLastName(String lastName);

    Optional<StudyUser> findByUsername(String username);



    StudyUser findById(long id);
}