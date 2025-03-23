package ru.practicum.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.EndpointHitDto;
import ru.practicum.StatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.utils.LoggingUtils.logAndReturn;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    @Transactional
    public EndpointHitDto saveHit(EndpointHitDto endpointHitDto) {
        return logAndReturn(StatsMapper.mapToEndpointHitDto(
                        statsRepository.save(StatsMapper.mapToEndpointHit(endpointHitDto))),
                savedEndpoint -> log.info("запрос c id = {} - cохранен", savedEndpoint.getId()));
    }

    @Override
    public List<StatsDto> getStats(String start, String end, List<String> uris, boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<StatsDto> stats = statsRepository.findStats(startTime, endTime, uris);
        if (unique) {
            stats = stats.stream()
                    .map(stat -> {
                        long uniqueHits = statsRepository.countDistinctByAppAndUriAndTimestampBetween(stat.getApp(),
                                stat.getUri(), startTime, endTime);
                        return new StatsDto(stat.getApp(), stat.getUri(), uniqueHits);
                    })
                    .collect(Collectors.toList());
        }

        return logAndReturn(stats, result -> log.info("Статистика собрана в количестве - {}",
                result.size()));
    }
}
