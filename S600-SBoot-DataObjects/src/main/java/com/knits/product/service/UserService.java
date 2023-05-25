package com.knits.product.service;

import com.knits.product.dto.UserDto;
import com.knits.product.exception.ExceptionCodes;
import com.knits.product.exception.UserException;
import com.knits.product.mapper.UserMapper;
import com.knits.product.model.User;
import com.knits.product.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing {@link com.knits.product.model.User}.
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Save a user.
     *
     * @param userDto the userDto to save.
     * @return the persisted entity.
     */
    public UserDto save(UserDto userDto) {
        log.debug("Request to save User: {}", userDto);
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    /**
     * Get all the users.
     *
     * @return the list of entities.
     */
    public List<UserDto> findAll() {
        log.debug("Request to get all Users");
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    /**
     * Get a user by the ID.
     *
     * @param id the ID of the user.
     * @return the user.
     */
    public UserDto findById(Long id) {
        log.debug("Request User by ID: {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() ->
                new UserException("User#" + id + " not found", ExceptionCodes.USER_NOT_FOUND));
        return userMapper.toDto(user);
    }

    /**
     * Update a user.
     *
     * @param userDto the userDto to update.
     * @return the updated user.
     */
    public UserDto update(UserDto userDto) {
        log.debug("Request to update User: {}", userDto);
        User user = userRepository.findById(userDto.getId()).orElseThrow(() ->
                new UserException("User#" + userDto.getId() + " not found", ExceptionCodes.USER_NOT_FOUND));
        userMapper.update(user, userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto partialUpdate(UserDto userDto) {
        log.debug("Request to update User: {}", userDto);
        User user = userRepository.findById(userDto.getId()).orElseThrow(() ->
                new UserException("User#" + userDto.getId() + " not found", ExceptionCodes.USER_NOT_FOUND));
        userMapper.partialUpdate(user, userDto);
        return userMapper.toDto(userRepository.save(user));
    }


    /**
     * Delete a user by the ID.
     *
     * @param id the ID of the user to delete.
     */
    public void delete(Long id) {
        log.debug("Delete User by ID: {}", id);
        userRepository.deleteById(id);
    }
}
