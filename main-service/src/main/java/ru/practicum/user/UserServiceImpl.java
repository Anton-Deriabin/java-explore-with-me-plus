package ru.practicum.user;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;


    @Override
    public UserDto createUser(UserDto userDto) {
        return UserMapper.mapUserToDto(userRepository.save(UserMapper.mapDtoToUser(userDto)));
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        if (size.equals(0)) {
            return List.of();
        }
        Pageable pageable = PageRequest.of(from / size, size);
        if (ids == null) {
            return userRepository.findAll(pageable).stream().map(UserMapper::mapUserToDto).toList();
        } else
            return userRepository.findUsersByIds(ids, pageable).stream().map(UserMapper::mapUserToDto).toList();
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.delete(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d was not found", userId),
                        "The required object was not found.")));
    }
}
