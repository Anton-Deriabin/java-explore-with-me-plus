package ru.practicum.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {
    @NotBlank
    private String name;
}