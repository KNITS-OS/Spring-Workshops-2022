package com.knits.product.controller;

import com.knits.product.exception.UserException;
import com.knits.product.model.Team;
import com.knits.product.model.User;
import com.knits.product.service.TeamService;
import com.knits.product.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
        log.debug("REST request to get User: {}", id);
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.debug("REST request to get all Users");
        List<User> userList = userService.findAll();
        return ResponseEntity.ok(userList);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.debug("REST request to create User");
        if (user == null) {
            throw new UserException("User data is missing");
        }
        User createdUser = userService.save(user);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        log.debug("REST request to update User");
        User updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> partialUpdateUser(
            @PathVariable("id") Long id,
            @RequestBody User user
    ) {
        log.debug("REST request to partially update User");
        user.setId(id);
        User updatedUser = userService.partialUpdate(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        log.debug("REST request to delete User: {}", id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
