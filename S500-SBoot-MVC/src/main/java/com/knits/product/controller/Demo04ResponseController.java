package com.knits.product.controller;

import com.knits.product.dto.UserDto;
import com.knits.product.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class Demo04ResponseController {



    @GetMapping(value = "/users/{id}", produces = {"application/json"})
    public ResponseEntity<UserDto> getUserById(@PathVariable(value = "id", required = true) final Long id) {

        log.debug("REST request to get User : {}", id);


        return ResponseEntity
                .ok()
                .body(mockUser(id));
    }


    @GetMapping(value = "/users/all", produces = {"application/json"})
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.debug("REST request to get all Users");

        return ResponseEntity
                .ok()
                .body(List.of(mockUser(1L)));
    }


    @PostMapping(value = "/users", produces = {"application/json"}, consumes = { "application/json"})
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDTO) {
        log.debug("REST request to createUser User ");
        return ResponseEntity
                .ok()
                .body(userDTO);
    }

    @PutMapping(value = "/users", produces = {"application/json"}, consumes = { "application/json"})
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDTO) {
        log.debug("REST request to updateUser User ");
        return ResponseEntity
                .ok()
                .body(userDTO);
    }

    @PatchMapping(value = "/users/{id}",  produces = {"application/json"}, consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserDto> partialUpdateUser(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody UserDto userDTO){
        log.debug("REST request to updateUser User ");
        userDTO.setId(id);
        return ResponseEntity
                .ok()
                .body(userDTO);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete User : {}", id);
        return ResponseEntity.noContent().build();
    }

    private UserDto mockUser (Long id){
        UserDto userFound = new UserDto();
        userFound.setId(id);
        userFound.setFirstName("Stefano");
        userFound.setLastName("Fiorenza");
        userFound.setActive(true);
        userFound.setLogin("stefano.fiorenza");
        userFound.setPassword(null);
        userFound.setEmail("stefano.fiorenza@email.it");
        return userFound;
    }





    @RequestMapping(value ="/domain/demo/employee/json/res/http", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes= MediaType.APPLICATION_JSON_VALUE)
    public HttpServletResponse echoReceivedAsLowerApiHttp(@RequestBody UserDto user, HttpServletResponse response) throws IOException, IOException {
        response.getOutputStream().write(HttpUtils.serializeUser(user));
        return response;
    }

    @RequestMapping(value ="/domain/demo/employee/json/res/os", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes= MediaType.APPLICATION_JSON_VALUE)
    public OutputStream echoReceivedAsLowerApiOs(@RequestBody UserDto userDto) {
        ByteArrayOutputStream bos=null;
        try{
            bos=new ByteArrayOutputStream();
            bos.write(HttpUtils.serializeUser(userDto));
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            IOUtils.closeQuietly(bos);
        }
        return bos;
    }


    @RequestMapping(value ="/domain/demo/employee/res/bytes", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes= MediaType.APPLICATION_JSON_VALUE)
    public byte[] echoReceivedAsLowerApiBytes(@RequestBody UserDto user) {
        return HttpUtils.serializeUser(user);
    }




}
