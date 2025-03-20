package ru.practicum.event.admin;


import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class EventAdminController {

    EventAdminService eventAdminService;

    @GetMapping
    public List<EventDto> getEvents(@RequestParam List <Long> users, @RequestParam List <String> states,
                                    @RequestParam List <Long> categories, @RequestParam String rangeStart,
                                    @RequestParam String rangeEnd, @PositiveOrZero @RequestParam Integer from,
                                    @Positive @RequestParam Integer size) {

        return eventAdminService.getEvents(users,states,categories,rangeStart,rangeEnd,from,size);


    }
}
