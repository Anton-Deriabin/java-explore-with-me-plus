package ru.practicum.event.admin;

import org.springframework.stereotype.Service;
import ru.practicum.event.dto.EventDto;

import java.util.List;

@Service
public class EventAdminServiceImpl implements EventAdminService {

    @Override
    public List<EventDto> getEvents(List<Long> users, List<String> states,
                                    List<Long> categories, String rangeStart,
                                    String rangeEnd, Integer from, Integer size) {


        return List.of();
    }
}
