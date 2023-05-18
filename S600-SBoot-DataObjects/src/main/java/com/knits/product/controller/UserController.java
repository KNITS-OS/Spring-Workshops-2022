package com.knits.product.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.knits.product.dto.views.Views;
import com.knits.product.exception.UserException;
import com.knits.product.service.UserService;
import com.knits.product.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@JsonView(Views.UserDetails.class)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users/{id}", produces = {"application/json"})
    public ResponseEntity<UserDto> getUserById(@PathVariable(value = "id", required = true) final Long id) {

        log.debug("REST request to get User : {}", id);
        UserDto userFound = userService.findById(id);
        return ResponseEntity
                .ok()
                .body(userFound);
    }


//    @GetMapping(value = "/users/all", produces = {"application/json"})
//    public ResponseEntity<List<UserDto>> getAllUsers() {
//        log.debug("REST request to get all Users");
//        return ResponseEntity
//                .ok()
//                .body(userService.findAll());
//    }


    @PostMapping(value = "/users", produces = {"application/json"}, consumes = { "application/json"})
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDTO) {
        log.debug("REST request to createUser User ");
        if (userDTO == null) {
            throw new UserException("User data are missing");
        }
        return ResponseEntity
                .ok()
                .body(userService.save(userDTO));
    }

    @PutMapping(value = "/users", produces = {"application/json"}, consumes = { "application/json"})
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDTO) {
        log.debug("REST request to updateUser User ");
        if (userDTO == null) {
            throw new UserException("User data are missing");
        }
        return ResponseEntity
                .ok()
                .body(userService.update(userDTO));
    }

    @PatchMapping(value = "/users/{id}",  produces = {"application/json"}, consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserDto> partialUpdateUser(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody UserDto userDTO){
        log.debug("REST request to updateUser User ");

        if (userDTO == null) {
            throw new UserException("User data are missing");
        }
        userDTO.setId(id);
        return ResponseEntity
                .ok()
                .body(userService.partialUpdate(userDTO));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete User : {}", id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.debug("REST request to get all Users");
        return ResponseEntity
                .ok()
                .body(userService.findAll());
    }

}
