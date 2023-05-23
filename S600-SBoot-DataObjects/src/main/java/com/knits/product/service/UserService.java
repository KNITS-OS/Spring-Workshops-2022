package com.knits.product.service;

import com.knits.product.exception.ExceptionCodes;
import com.knits.product.exception.UserException;
import com.knits.product.model.User;
import com.knits.product.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    /**
     * Save a user.
     *
     * @param user the entity to save.
     * @return the persisted entity.
     */
    public User save(User user) {
        log.debug("Request to save User: {}", user);
        return userRepository.save(user);
    }

    /**
     * Partially updates a user.
     *
     * @param user the entity to update partially.
     * @return the persisted entity.
     */
    public User partialUpdate(User user) {
        log.debug("Request to partially update User: {}", user);
        return userRepository.save(user);
    }

    public User update(User user) {
        log.debug("Request to partially update User: {}", user);
        return userRepository.save(user);
    }

    /**
     * Get all users.
     *
     * @return the list of users.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Get a user by the "id".
     *
     * @param id the id of the user.
     * @return the user.
     */
    public User findById(Long id) {
        log.debug("Request User by id: {}", id);
        return userRepository.findById(id).orElseThrow(() ->
                new UserException("User#" + id + " not found", ExceptionCodes.USER_NOT_FOUND));
    }

    /**
     * Delete a user by the "id".
     *
     * @param id the id of the user.
     */
    public void delete(Long id) {
        log.debug("Delete User by id: {}", id);
        userRepository.deleteById(id);
    }

}
