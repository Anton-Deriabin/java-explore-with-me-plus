package ru.practicum.comment;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentCreateDto;
import ru.practicum.comment.dto.CommentDto;

@RestController
@RequestMapping("/users/{userId}/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentController {
    final String eventIdHeader = "X-Sharer-Event-Id";
    final CommentService commentService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@Valid @RequestBody CommentCreateDto comment,
                                    @PathVariable Long userId,
                                    @RequestHeader(value = eventIdHeader) Long eventId) {
        return commentService.createComment(comment, userId, eventId);
    }

    @PatchMapping("/{commentId}")
    public CommentDto updateComment(
            @PathVariable Long userId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentCreateDto updateDto) {
        return commentService.updateCommentByUser(commentId, userId, updateDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable Long userId,
            @PathVariable Long commentId) {
        commentService.deleteCommentByUser(commentId, userId);
    }

    @GetMapping("/{commentId}")
    public CommentDto getComment(
            @PathVariable Long userId,
            @PathVariable Long commentId) {
        return commentService.getCommentByUser(commentId, userId);
    }
}
