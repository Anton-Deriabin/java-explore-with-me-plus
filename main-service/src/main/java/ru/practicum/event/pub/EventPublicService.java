package ru.practicum.event.pub;

import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

public interface EventPublicService {

    List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid, String rangeStart,
                                  String rangeEnd, Boolean onlyAvailable, String sort, Integer from,
                                  Integer size);
}
