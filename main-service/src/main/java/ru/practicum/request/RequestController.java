package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class RequestController {
    private final String cancelPath = "/{requestId}/cancel";
    private final RequestService requestService;

    @GetMapping()
    public List<ParticipationRequestDto> findRequestsByUserId(@PathVariable Long userId) {
        return requestService.findRequestsByUserId(userId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto saveRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        return requestService.saveRequest(userId, eventId);
    }

    @PatchMapping(cancelPath)
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        return requestService.cancelRequest(userId, requestId);
    }
}
