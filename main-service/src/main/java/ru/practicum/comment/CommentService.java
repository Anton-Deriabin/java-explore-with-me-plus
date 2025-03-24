package ru.practicum.comment;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.dto.CommentCreateDto;
import ru.practicum.comment.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentCreateDto comment, Long userId, Long itemId);

    List<CommentDto> findCommentsByEventId(Long eventId);

    @Transactional
    CommentDto updateCommentByAdmin(CommentCreateDto updateDto, Long commentId);

    CommentDto getCommentByAdmin(Long commentId);

    @Transactional
    void deleteCommentByAdmin(Long commentId);

    @Transactional
    CommentDto updateCommentByUser(Long commentId, Long userId, CommentCreateDto updateDto);

    @Transactional
    void deleteCommentByUser(Long commentId, Long userId);

    CommentDto getCommentByUser(Long commentId, Long userId);
}
