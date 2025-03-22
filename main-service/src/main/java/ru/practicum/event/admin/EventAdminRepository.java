package ru.practicum.event.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.event.Event;
import ru.practicum.event.State;

import java.time.LocalDateTime;
import java.util.List;

public interface EventAdminRepository extends JpaRepository<Event,Long> {

    @EntityGraph(attributePaths = {"initiator", "category"})
    @Query("SELECT e FROM Event e " +
            "WHERE e.initiator.id IN :users " +
            "AND e.state IN :states " +
            "AND e.category.id IN :categories " +
            "AND e.eventDate > :rangeStart " +
            "AND e.eventDate < :rangeEnd ")
    Page<Event> findEvents(@Param("users") List<Long> users, @Param("states") List<State> states,
                           @Param("categories") List<Long> categories,
                           @Param("rangeStart") LocalDateTime rangeStart,
                           @Param("rangeEnd") LocalDateTime end,
                           Pageable pageable);
}
