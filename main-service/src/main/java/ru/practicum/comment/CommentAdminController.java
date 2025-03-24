package ru.practicum.comment;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentCreateDto;
import ru.practicum.comment.dto.CommentDto;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/comments")
public class CommentAdminController {
    private final CommentService commentService;

    @PatchMapping("/{commentId}")
    public CommentDto updateComment(@Valid @RequestBody CommentCreateDto newCommentDto,
                                    @PathVariable Long commentId) {
        return commentService.updateCommentByAdmin(newCommentDto, commentId);
    }

    @GetMapping("/{eventId}")
    public List<CommentDto> findCommentsByEventId(@RequestParam Long eventId) {
        return commentService.findCommentsByEventId(eventId);
    }

    @GetMapping("/{commentId}")
    public CommentDto getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentByAdmin(commentId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteCommentByAdmin(commentId);
    }
}