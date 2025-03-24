package ru.practicum.comment;

import ru.practicum.comment.dto.CommentCreateDto;
import ru.practicum.comment.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentCreateDto comment, Long userId, Long itemId);

    List<CommentDto> findCommentsByEventId(Long eventId);
}
