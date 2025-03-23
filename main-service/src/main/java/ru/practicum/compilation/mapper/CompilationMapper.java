package ru.practicum.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.category.model.Category;
import ru.practicum.compilation.Compilation;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.event.mapper.EventMapper;


@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface CompilationMapper {


}
