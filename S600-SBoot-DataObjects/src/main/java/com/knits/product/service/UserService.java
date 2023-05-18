package com.knits.product.service;

import com.knits.product.exception.ExceptionCodes;
import com.knits.product.exception.UserException;
import com.knits.product.model.User;
import com.knits.product.repository.UserRepository;
import com.knits.product.dto.UserDto;
import com.knits.product.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing {@link com.knits.product.model.User}.
 */
@Service
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    /**
     * Save a employee.
     *
     * @param userDTO the entity to save.
     * @return the persisted entity.
     */
    public UserDto save(UserDto userDTO) {
        log.debug("Request to save User : {}", userDTO);
       // userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userMapper.toEntity(userDTO);

        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * Partially updates a user.
     *
     * @param userDTO the entity to update partially.
     * @return the persisted entity.
     */
    public UserDto partialUpdate(UserDto userDTO) {
        log.debug("Request to partially update User : {}", userDTO);
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new UserException("User#" + userDTO.getId() + " not found"));
        userMapper.partialUpdate(user, userDTO);

        //TODO: manage User relationship to check updates
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * overrides fields, including nulls.
     *
     * @param userDTO the entity to update.
     * @return the persisted entity.
     */
    public UserDto update(UserDto userDTO) {
        log.debug("Request to update User : {}", userDTO);
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new UserException("User#" + userDTO.getId() + " not found"));
        userMapper.update(user, userDTO);

        //TODO: manage User relationship to check updates
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Get by the "id" user.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public UserDto findById(Long id) {

        log.debug("Request User by id : {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("User#" + id + " not found", ExceptionCodes.USER_NOT_FOUND));
        return userMapper.toDto(user);
    }

    /**
     * Delete the "id" user.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Delete User by id : {}", id);
        userRepository.deleteById(id);
    }

    /**
     * Get all the users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserDto> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("not yet implementes");
    }






}
