package ru.practicum.comment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.dto.CommentCreateDto;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.utils.CheckEventService;
import ru.practicum.utils.CheckUserService;

import java.util.List;

import static ru.practicum.utils.LoggingUtils.logAndReturn;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {
    CheckUserService checkUserService;
    CommentRepository commentRepository;
    CheckEventService checkEventService;
    CommentMapper commentMapper;

    @Override
    public List<CommentDto> findCommentsByEventId(Long eventId) {
        return logAndReturn(commentRepository.findCommentsByEventId(eventId).stream()
                        .map(commentMapper::toDto)
                        .toList(),
                comments -> log.info("Found {} comments for event with id={}",
                comments.size(), eventId)
        );
    }

    @Override
    @Transactional
    public CommentDto createComment(CommentCreateDto commentCreateDto, Long userId, Long eventId) {
        return logAndReturn(commentMapper.toDto(commentRepository.save(Comment.builder()
                        .text(commentCreateDto.getText())
                        .created(commentCreateDto.getCreated())
                        .author(checkUserService.checkUser(userId))
                        .event(checkEventService.checkEvent(eventId))
                        .build())),
                savedComment -> log.info("Comment created successfully: {}",
                        savedComment)
        );
    }
}
