package ru.practicum.request.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.request.RequestStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.validation.FuturePlusTwoHours;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDto {
    Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @FuturePlusTwoHours
    String created;

    Long event;

    Long requester;

    RequestStatus status;
}
