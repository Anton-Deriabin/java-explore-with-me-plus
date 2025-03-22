package ru.practicum.event.pub;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.event.EventRepository;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.mapper.EventMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {

    EventRepository eventRepository;
    EventMapper eventMapper;

    @Override
    public List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid,
                                         String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                         String sort, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);

        if (categories != null && categories.isEmpty()) {
            categories = null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return eventRepository.findEvents(text, paid, categories, LocalDateTime.parse(rangeStart, formatter),
                LocalDateTime.parse(rangeEnd, formatter), pageable).stream().map(eventMapper::toShortDto).toList();
    }
}
