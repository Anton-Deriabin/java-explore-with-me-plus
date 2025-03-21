package ru.practicum.event;

import ru.practicum.event.dto.*;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.EventRequestStatusUpdateRequest;

import java.util.List;

public interface EventService {
    EventFullDto saveEvent(NewEventDto newEventDto, Long userId);

    List<EventShortDto> findEventsByUserId(Long userId, Integer from, Integer size);

    EventFullDto findById(Long eventId, Long id);

    EventFullDto updateEvent(UpdateEventUserRequest updateEventUserRequest, Long eventId, Long id);

    List<ParticipationRequestDto> findRequestsByEventId(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequestStatus(EventRequestStatusUpdateRequest requestDto, Long userId, Long eventId);
}

