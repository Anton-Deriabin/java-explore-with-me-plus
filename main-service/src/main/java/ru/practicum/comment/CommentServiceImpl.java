package ru.practicum.comment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.dto.CommentCreateDto;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.exception.ForbiddenException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.utils.CheckEventService;
import ru.practicum.utils.CheckUserService;

import java.time.LocalDateTime;
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

    @Transactional
    @Override
    public CommentDto updateCommentByAdmin(CommentCreateDto updateDto, Long commentId) {
        Comment comment = getCommentOrThrow(commentId);

        comment.setText(updateDto.getText());
        comment.setCreated(LocalDateTime.now());

        return logAndReturn(
                commentMapper.toDto(commentRepository.save(comment)),
                updatedComment -> log.info("Comment with id={} updated by admin", commentId)
        );
    }

    @Override
    public CommentDto getCommentByAdmin(Long commentId) {
        return logAndReturn(
                commentMapper.toDto(getCommentOrThrow(commentId)),
                comment -> log.info("Retrieved comment with id={} (admin view)", commentId)
        );
    }

    @Transactional
    @Override
    public void deleteCommentByAdmin(Long commentId) {
        Comment comment = getCommentOrThrow(commentId);
        commentRepository.delete(comment);
        log.info("Comment with id={} deleted by admin", commentId);
    }

    @Transactional
    @Override
    public CommentDto updateCommentByUser(Long commentId, Long userId, CommentCreateDto updateDto) {
        Comment comment = getCommentOrThrow(commentId);
        checkUserIsAuthor(comment, userId);

        comment.setText(updateDto.getText());
        comment.setCreated(LocalDateTime.now());

        return logAndReturn(
                commentMapper.toDto(commentRepository.save(comment)),
                updatedComment -> log.info("Comment {} updated by user {}", commentId, userId)
        );
    }

    @Transactional
    @Override
    public void deleteCommentByUser(Long commentId, Long userId) {
        Comment comment = getCommentOrThrow(commentId);
        checkUserIsAuthor(comment, userId);

        commentRepository.delete(comment);
        log.info("Comment {} deleted by user {}", commentId, userId);
    }

    @Override
    public CommentDto getCommentByUser(Long commentId, Long userId) {
        Comment comment = getCommentOrThrow(commentId);
        checkUserIsAuthor(comment, userId);

        return logAndReturn(
                commentMapper.toDto(comment),
                c -> log.info("User {} accessed comment {}", userId, commentId)
        );
    }

    private Comment getCommentOrThrow(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id=" + commentId + " not found"));
    }

    private void checkUserIsAuthor(Comment comment, Long userId) {
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ForbiddenException("User " + userId + " is not author of comment " + comment.getId());
        }
    }
}
