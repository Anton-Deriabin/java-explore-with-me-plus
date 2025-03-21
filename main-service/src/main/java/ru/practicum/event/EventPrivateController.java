package ru.practicum.event;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.*;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.EventRequestStatusUpdateRequest;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class EventPrivateController {
    private final String eventIdPath = "/{eventId}";
    private final String requestsPath = "/requests";
    private final EventService eventService;

    @GetMapping()
    public List<EventShortDto> findEventsByUserId(@PathVariable Long userId,
                                             @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                             @Positive @RequestParam(defaultValue = "10") Integer size) {
        return eventService.findEventsByUserId(userId, from, size);
    }

    @GetMapping(eventIdPath)
    public EventFullDto findById(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventService.findById(userId, eventId);
    }

    @GetMapping(eventIdPath + requestsPath)
    public List<ParticipationRequestDto> findRequestsByEventId(@PathVariable Long userId,
                                                               @PathVariable Long eventId) {
        return eventService.findRequestsByEventId(userId, eventId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto saveEvent(@Valid @RequestBody NewEventDto newEventDto,
                              @PathVariable Long userId) {
        return eventService.saveEvent(newEventDto, userId);
    }

    @PatchMapping(eventIdPath)
    public EventFullDto updateEvent(@Valid @RequestBody UpdateEventUserRequest updateEventUserRequest,
                                @PathVariable Long userId,
                                @PathVariable Long eventId) {
        return eventService.updateEvent(updateEventUserRequest, userId, eventId);
    }

    @PatchMapping(eventIdPath + requestsPath)
    public EventRequestStatusUpdateResult updateRequestStatus(
            @RequestBody EventRequestStatusUpdateRequest requestDto,
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        return eventService.updateRequestStatus(requestDto, userId, eventId);
    }
}
