package ru.practicum.event.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.model.Category;
import ru.practicum.event.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.UpdateEventRequest;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.ForbiddenException;
import ru.practicum.utils.CheckCategoryService;
import ru.practicum.utils.CheckEventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.utils.LoggingUtils.logAndReturn;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {

    EventRepository eventRepository;
    EventMapper eventMapper;
    LocationRepository locationRepository;
    CheckEventService checkEventService;
    CheckCategoryService checkCategoryService;

    @Override
    public List<EventFullDto> getEvents(List<Long> users, List<String> states,
                                        List<Long> categories, String rangeStart,
                                        String rangeEnd, Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (users == null && states == null && categories == null && rangeStart == null && rangeEnd == null) {
            return eventRepository.findAll(pageable).stream().map(eventMapper::toFullDto).toList();
        } else {
            return eventRepository.findAllEventsByAdmin(users, states.stream().map(State::valueOf).toList(),
                    categories, LocalDateTime.parse(rangeStart, formatter),
                    LocalDateTime.parse(rangeEnd, formatter), pageable).stream().map(eventMapper::toFullDto).toList();
        }
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(UpdateEventRequest updateEventRequest, Long eventId) {
        if (updateEventRequest == null) {
            throw new ForbiddenException("Cannot publish the event: request body is missing.");
        }
        Event event = checkEventService.checkEvent(eventId);

        if (event.getState().equals(State.PUBLISHED) || event.getState().equals(State.CANCELED)) {
            throw new ConflictException("Only pending events can be published");
        }

        if (updateEventRequest.getStateAction() != null) {
            if (updateEventRequest.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                event.setState(State.PUBLISHED);
            } else {
                throw new ForbiddenException("Cannot publish the event because it's not in the right state: PUBLISHED");
            }
        }
        if (updateEventRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventRequest.getAnnotation());
        }
        if (updateEventRequest.getCategory() != null) {
            Category category = checkCategoryService.checkCategory(updateEventRequest.getCategory());
            category.setId(updateEventRequest.getCategory());
            event.setCategory(category);
        }
        if (updateEventRequest.getDescription() != null) {
            event.setDescription(updateEventRequest.getDescription());
        }
        if (updateEventRequest.getEventDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime eventDate = LocalDateTime.parse(updateEventRequest.getEventDate(), formatter);
            event.setEventDate(eventDate);
        }
        if (updateEventRequest.getLocation() != null) {
            Double lat = updateEventRequest.getLocation().getLat();
            Double lon = updateEventRequest.getLocation().getLon();
            Location location = locationRepository.findByLatAndLon(lat,
                            updateEventRequest.getLocation().getLon())
                    .orElseGet(() -> locationRepository.save(Location.builder().lon(lon).lat(lat).build()));
            location.setLat(lat);
            location.setLon(lon);
            event.setLocation(location);
        }
        if (updateEventRequest.getPaid() != null) {
            event.setPaid(updateEventRequest.getPaid());
        }
        if (updateEventRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventRequest.getParticipantLimit());
        }
        if (updateEventRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventRequest.getRequestModeration());
        }
        if (updateEventRequest.getTitle() != null) {
            event.setTitle(updateEventRequest.getTitle());
        }

        return logAndReturn(eventMapper.toFullDto(eventRepository.save(event)),
                dto -> log.info("Event updated successfully: {}", dto)
        );
    }

}
