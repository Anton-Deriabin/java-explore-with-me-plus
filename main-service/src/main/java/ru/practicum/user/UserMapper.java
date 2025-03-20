package ru.practicum.user;

import lombok.experimental.UtilityClass;
import ru.practicum.user.dto.UserDto;

@UtilityClass
public class UserMapper {

    public static User mapDtoToUser(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    public static UserDto mapUserToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
