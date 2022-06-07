package com.knits.product.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = Demo01ControllerHandlerMatching.class)
public class Demo01ControllerHandlerMatchingTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetSomeResourceAsStringByPathMapping() throws Exception {
        mockMvc.perform(get("/basic/path"))
                .andExpect(status().isOk())
                .andExpect(content().string("value from uri path mapping"));
    }

    @Test
    public void testGetSomeResourceAsStringByPathMappingPost() throws Exception {

        mockMvc.perform(post("/basic/path")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("value from uri path mapping method POST"));
    }

    @Test
    public void getSomeResourceAsStringByPathMappingWithHeader() throws Exception {

        mockMvc.perform(get("/basic/path/headers").header("key","val"))
                .andExpect(status().isOk())
                .andExpect(content().string("value from uri path mapping method Header key=val"));
    }

    @Test
    public void getSomeResourceAsStringByPathMappingWithMultipleHeader() throws Exception {

        mockMvc.perform(get("/basic/path/multiple/headers")
                        .header("key1","val1")
                        .header("key2","val2"))
                .andExpect(status().isOk())
                .andExpect(content().string("value from uri path mapping method multiple Headers key1=val1, key2=val2"));
    }

    @Test
    public void getSomeResourceAsStringAcceptHeader() throws Exception {

        mockMvc.perform(get("/basic/path/headers/accept")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("value from uri path mapping method Accept Headers"));
    }

    @Test
    public void  getSomeResourceAsStringProducer() throws Exception {

        mockMvc.perform(get("/basic/path/headers/produces")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("value from uri path mapping method Produce json"));

    }
}
