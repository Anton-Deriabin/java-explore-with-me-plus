package ru.practicum.user;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Creating user with name: {} and email: {}", userDto.getName(), userDto.getEmail());
        UserDto savedUser = UserMapper.mapUserToDto(userRepository.save(UserMapper.mapDtoToUser(userDto)));
        log.info("User created successfully: {}", savedUser);
        return savedUser;
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        log.info("Fetching users with ids={}, from={}, size={}", ids, from, size);
        if (size.equals(0)) {
            log.warn("Size is 0, returning empty list");
            return List.of();
        }
        Pageable pageable = PageRequest.of(from / size, size);
        List<UserDto> users;
        if (ids == null) {
            users = userRepository.findAll(pageable).stream().map(UserMapper::mapUserToDto).toList();
        } else {
            users = userRepository.findUsersByIds(ids, pageable).stream().map(UserMapper::mapUserToDto).toList();
        }
        log.info("Fetched {} users", users.size());
        return users;
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Attempting to delete user with id={}", userId);
        userRepository.delete(userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with id={} not found", userId);
                    return new NotFoundException(String.format("User with id=%d was not found", userId),
                            "The required object was not found.");
                }));
        log.info("User with id={} successfully deleted", userId);
    }
}
