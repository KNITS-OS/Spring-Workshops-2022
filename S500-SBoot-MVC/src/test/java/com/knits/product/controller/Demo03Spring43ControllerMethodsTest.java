package com.knits.product.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = Demo03Spring43ControllerMethods.class)
public class Demo03Spring43ControllerMethodsTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void  getDataFromGetMethod() throws Exception {

		String expected= "getDataFromGetMethod";

		mockMvc.perform(get("/s43/"))
				.andExpect(status().isOk())
				.andExpect(content().string(expected));
	}

	@Test
	public void  getDataFromPostMethod() throws Exception  {
		String expected= "getDataFromPostMethod";
		mockMvc.perform(post("/s43/"))
				.andExpect(status().isOk())
				.andExpect(content().string(expected));
	}

	@Test
	public void  getDataFromPutMethod() throws Exception  {
		String expected= "getDataFromPutMethod";
		mockMvc.perform(put("/s43/"))
				.andExpect(status().isOk())
				.andExpect(content().string(expected));
	}

	@Test
	public void  deleteDataFromGetMethod() throws Exception  {
		String expected= "deleteDataFromGetMethod";
		mockMvc.perform(delete("/s43/"))
				.andExpect(status().isOk())
				.andExpect(content().string(expected));
	}


	@Test
	public void getDataFromPatchMapping() throws Exception  {
		String expected= "getDataFromPatchMapping";
		mockMvc.perform(patch("/s43/"))
				.andExpect(status().isOk())
				.andExpect(content().string(expected));
	}
}
