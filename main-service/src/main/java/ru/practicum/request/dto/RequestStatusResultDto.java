package ru.practicum.request.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestStatusResultDto {
    List<RequestDto> confirmedRequests;
    List<RequestDto> rejectedRequests;
}
