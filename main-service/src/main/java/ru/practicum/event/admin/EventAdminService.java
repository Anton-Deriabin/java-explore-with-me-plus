package ru.practicum.event.admin;


import ru.practicum.event.dto.EventDto;

import java.util.List;

public interface EventAdminService {

    List<EventDto> getEvents(List<Long> users, List<String> states,
                             List<Long> categories, String rangeStart,
                             String rangeEnd, Integer from,
                             Integer size);
}
