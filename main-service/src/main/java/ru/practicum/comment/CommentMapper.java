package ru.practicum.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.comment.dto.CommentCreateDto;
import ru.practicum.comment.dto.CommentDto;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    Comment toEntity(CommentCreateDto commentCreateDto);

    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "author", source = "author.id")
    CommentDto toDto(Comment comment);
}
