package com.studybuddy.repository;

import com.studybuddy.dto.LeaderboardEntryDTO;
import com.studybuddy.entity.StudySession;
import com.studybuddy.entity.StudyUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudySessionRepository extends CrudRepository<StudySession, Long> {

    List<StudySession> findByUser(StudyUser user);

    @Query(value = """
    SELECT
        CASE WHEN u.anonymous = true THEN 'Anonymous' ELSE u.username END AS displayName,
        SUM(EXTRACT(EPOCH FROM (s.end_time - s.start_time)) / 60) AS totalMinutes
    FROM study_session s
    JOIN users u ON s.user_id = u.id
    GROUP BY u.id, u.username, u.anonymous
    ORDER BY totalMinutes DESC
    """, nativeQuery = true)
    List<LeaderboardEntryDTO> getRawLeaderboard();





}
