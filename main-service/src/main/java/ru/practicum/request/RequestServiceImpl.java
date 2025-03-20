package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.request.dto.RequestDto;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    @Override
    public List<RequestDto> findEventsByUserId(Long userId) {
        return List.of();
    }

    @Override
    @Transactional
    public RequestDto saveRequest(Long userId, Integer eventId) {
        return null;
    }

    @Override
    @Transactional
    public RequestDto cancelRequest(Long userId, Long requestId) {
        return null;
    }
}
