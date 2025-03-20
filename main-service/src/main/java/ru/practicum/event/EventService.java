package ru.practicum.event;

import ru.practicum.event.dto.*;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusResultDto;
import ru.practicum.request.dto.RequestStatusUpdateDto;

import java.util.List;

public interface EventService {
    EventDto saveEvent(EventCreateDto eventCreateDto, Long userId);

    List<EventDto> findEventsByUserId(Long userId, Integer from, Integer size);

    EventDto findById(Long eventId, Long id);

    EventDto updateEvent(EventUpdateDto eventUpdateDto, Long eventId, Long id);

    List<RequestDto> findRequestsByEventId(Long userId, Long eventId);

    RequestStatusResultDto updateRequestStatus(RequestStatusUpdateDto requestDto, Long userId, Long eventId);
}

