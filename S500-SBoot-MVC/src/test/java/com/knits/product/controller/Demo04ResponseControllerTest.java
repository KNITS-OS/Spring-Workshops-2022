package com.knits.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knits.product.dto.Mocks;
import com.knits.product.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = Demo04ResponseController.class)
public class Demo04ResponseControllerTest {


    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper om = new ObjectMapper();

    @Test
    public void  getUserById( ) throws Exception{
        Long id=1L;
        UserDto expectedUser = Mocks.mockUser(id);

        ResultActions resultActions = mockMvc.perform(get("/api/users/"+id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        assertUserWithJsonPath(resultActions,expectedUser);
    }

    @Test
    public void getAllUsers() throws Exception{

        UserDto expectedUser = Mocks.mockUser(1L);

        ResultActions resultActions = mockMvc.perform(get("/api/users/all").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        assertUserWithJsonPathFromArray(resultActions,expectedUser);
    }


   // @PostMapping(value = "/users", produces = {"application/json"}, consumes = { "application/json"})
    @Test
    public void createUser() throws Exception{
        UserDto expectedUser = Mocks.mockUser(1L);

        ResultActions resultActions = mockMvc.perform(post("/api/users/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(expectedUser)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        assertUserWithJsonPath(resultActions,expectedUser);
    }




    //@PutMapping(value = "/users", produces = {"application/json"}, consumes = { "application/json"})
    @Test
    public void updateUser() throws Exception{

        UserDto expectedUser = Mocks.mockUser(1L);

        ResultActions resultActions = mockMvc.perform(put("/api/users/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(expectedUser)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        assertUserWithJsonPath(resultActions,expectedUser);
    }

   // @PatchMapping(value = "/users/{id}",  produces = {"application/json"}, consumes = { "application/json", "application/merge-patch+json" })
   @Test
   public void partialUpdateUser() throws Exception{

        Long id=1L;
        UserDto expectedUser = Mocks.mockUser(id);

        ResultActions resultActions = mockMvc.perform(patch("/api/users/"+id)
                       .accept(MediaType.APPLICATION_JSON)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(om.writeValueAsString(expectedUser)))
               .andExpect(status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
       assertUserWithJsonPath(resultActions,expectedUser);
    }

   // @DeleteMapping("/users/{id}")
   @Test
   public void  deleteUser() throws Exception{
       Long id=1L;
       ResultActions resultActions = mockMvc.perform(delete("/api/users/"+id))
               .andExpect(status().isNoContent());
    }


    private void assertUserWithJsonPath (ResultActions resultAction, UserDto expected) throws Exception {

        resultAction
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expected.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(expected.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(expected.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value(expected.getLogin()));
    }

    private void assertUserWithJsonPathFromArray (ResultActions resultAction, UserDto expected) throws Exception {

        resultAction
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(expected.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]firstName").value(expected.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]lastName").value(expected.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]login").value(expected.getLogin()));
    }

}
