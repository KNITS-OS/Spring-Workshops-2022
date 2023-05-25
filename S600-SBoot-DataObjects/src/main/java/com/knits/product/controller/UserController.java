package com.knits.product.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.knits.product.dto.UserDto;
import com.knits.product.dto.Views;
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

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(value = "id") Long id) {
        log.debug("REST request to get User: {}", id);
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @JsonView(Views.UserDetails.class)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.debug("REST request to get all Users");
        List<UserDto> userDtoList = userService.findAll();
        return ResponseEntity.ok(userDtoList);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        log.debug("REST request to create User");
        UserDto createdUserDto = userService.save(user);
        return ResponseEntity.ok(createdUserDto);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        log.debug("REST request to update User");
        UserDto updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> partialUpdateUser(
            @PathVariable("id") Long id,
            @RequestBody UserDto user
    ) {
        log.debug("REST request to partially update User");
        user.setId(id);
        UserDto updatedUserDto = userService.partialUpdate(user);
        return ResponseEntity.ok(updatedUserDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        log.debug("REST request to delete User: {}", id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
