package ru.practicum.request;

import ru.practicum.request.dto.RequestDto;

import java.util.List;

public interface RequestService {
    List<RequestDto> findEventsByUserId(Long userId);

    RequestDto saveRequest(Long userId, Integer eventId);

    RequestDto cancelRequest(Long userId, Long requestId);
}
