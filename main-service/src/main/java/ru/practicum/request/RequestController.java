package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.RequestDto;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class RequestController {
    private final String cancelPath = "/{requestId}/cancel";
    private final RequestService requestService;

    @GetMapping()
    public List<RequestDto> findRequestsByUserId(@PathVariable Long userId) {
        return requestService.findEventsByUserId(userId);
    }

    @PostMapping()
    public RequestDto saveRequest(@PathVariable Long userId, @RequestParam Integer eventId) {
        return requestService.saveRequest(userId, eventId);
    }

    @PatchMapping(cancelPath)
    public RequestDto cancelRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        return requestService.cancelRequest(userId, requestId);
    }
}
