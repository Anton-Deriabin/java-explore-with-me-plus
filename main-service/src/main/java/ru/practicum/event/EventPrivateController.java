package ru.practicum.event;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.*;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusResultDto;
import ru.practicum.request.dto.RequestStatusUpdateDto;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class EventPrivateController {
    private final String eventIdPath = "/{eventId}";
    private final String requestsPath = "/requests";
    private final EventService eventService;

    @GetMapping()
    public List<EventDto> findEventsByUserId(@PathVariable Long userId,
                                             @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                             @Positive @RequestParam(defaultValue = "10") Integer size) {
        return eventService.findEventsByUserId(userId, from, size);
    }

    @GetMapping(eventIdPath)
    public EventDto findById(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventService.findById(userId, eventId);
    }

    @GetMapping(eventIdPath + requestsPath)
    public List<RequestDto> findRequestsByEventId(@PathVariable Long userId,
                                                  @PathVariable Long eventId) {
        return eventService.findRequestsByEventId(userId, eventId);
    }

    @PostMapping()
    public EventDto saveEvent(@Valid @RequestBody EventCreateDto eventCreateDto,
                              @PathVariable Long userId) {
        return eventService.saveEvent(eventCreateDto, userId);
    }

    @PatchMapping(eventIdPath)
    public EventDto updateEvent(@Valid @RequestBody EventUpdateDto eventUpdateDto,
                                @PathVariable Long userId,
                                @PathVariable Long eventId) {
        return eventService.updateEvent(eventUpdateDto, userId, eventId);
    }

    @PatchMapping(eventIdPath + requestsPath)
    public RequestStatusResultDto updateRequestStatus(
            @RequestBody RequestStatusUpdateDto requestDto,
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        return eventService.updateRequestStatus(requestDto, userId, eventId);
    }
}
