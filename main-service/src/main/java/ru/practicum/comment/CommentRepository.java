package ru.practicum.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c " +
            "JOIN FETCH c.author " +
            "JOIN FETCH c.event " +
            "WHERE c.event.id = :eventId")
    List<Comment> findCommentsByEventId(@Param("eventId") Long eventId);
}
