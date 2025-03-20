package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.*;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusResultDto;
import ru.practicum.request.dto.RequestStatusUpdateDto;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    @Override
    public List<EventDto> findEventsByUserId(Long userId, Integer from, Integer size) {
        return List.of();
    }

    @Override
    public EventDto findById(Long eventId, Long id) {
        return null;
    }

    @Override
    @Transactional
    public EventDto saveEvent(EventCreateDto eventCreateDto, Long userId) {
        return null;
    }

    @Override
    @Transactional
    public EventDto updateEvent(EventUpdateDto eventUpdateDto, Long eventId, Long id) {
        return null;
    }

    @Override
    public List<RequestDto> findRequestsByEventId(Long userId, Long eventId) {
        return List.of();
    }

    @Override
    @Transactional
    public RequestStatusResultDto updateRequestStatus(RequestStatusUpdateDto requestDto, Long userId, Long eventId) {
        return null;
    }
}
