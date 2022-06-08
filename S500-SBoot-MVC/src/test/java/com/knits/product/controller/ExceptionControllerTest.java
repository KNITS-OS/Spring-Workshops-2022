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

import static com.knits.product.controller.Demo04ResponseController.*;
import static com.knits.product.exception.ExceptionCodes.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest()
public class ExceptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper om = new ObjectMapper();

    @Test
    public void testExceptionManagement() throws Exception {

        testTemplate(duplicateUser,BAD_REQUEST.value(),USER_ALREADY_EXISTS);
        testTemplate(systemException,INTERNAL_SERVER_ERROR.value(),DB_TABLE_LOCKED);
        testTemplate(uncaughtException,INTERNAL_SERVER_ERROR.value(),UNMAPPED_EXCEPTION_CODE);

    }

    private void testTemplate (Long userId, int statusCode, int expectedCode) throws Exception{
        UserDto userToSave = Mocks.mockUser(userId);

        ResultActions resultActions = mockMvc.perform(post("/api/users/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(userToSave)))
                .andExpect(status().is(statusCode))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(expectedCode));
    }

}
