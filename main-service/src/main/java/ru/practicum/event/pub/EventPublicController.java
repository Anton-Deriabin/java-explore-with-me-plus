package ru.practicum.event.pub;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class EventPublicController {

    EventPublicService eventPublicService;

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam String text,
                                         @RequestParam List<Long> categories,
                                         @RequestParam Boolean paid,
                                         @RequestParam String rangeStart,
                                         @RequestParam String rangeEnd,
                                         @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                         @RequestParam String sort,
                                         @RequestParam(defaultValue = "0") Integer from,
                                         @RequestParam(defaultValue = "10") Integer size) {

        return eventPublicService.getEvents(text,categories,paid,rangeStart,rangeEnd,onlyAvailable,sort,from,size);

    }


}
