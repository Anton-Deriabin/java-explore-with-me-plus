package ru.practicum.comment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentCreateDto;
import ru.practicum.comment.dto.CommentDto;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentController {
    final String eventPath = "/{eventId}";
    final String userIdHeader = "X-Sharer-User-Id";
    final CommentService commentService;

    @GetMapping(eventPath)
    public List<CommentDto> findCommentsByEventId(@PathVariable Long eventId) {
        return commentService.findCommentsByEventId(eventId);
    }

    @PostMapping()
    public CommentDto createComment(@RequestBody CommentCreateDto comment,
                                    @RequestHeader(value = userIdHeader, required = false) Long userId,
                                    @PathVariable Long itemId) {
        return commentService.createComment(comment, userId, itemId);
    }
}
