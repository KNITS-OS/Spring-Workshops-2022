package com.knits.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
public class Demo03Spring43ControllerMethodsTest {

	@GetMapping("/s43/")	
	public String getDataFromGetMethod() {
		return "getDataFromGetMethod";
	}
	
	@PostMapping("/s43/")	
	public String getDataFromPostMethod() {
		return "getDataFromPostMethod";
	}
	
	@PutMapping("/s43/")	
	public String getDataFromPutMethod() {
		return "getDataFromPutMethod";
	}
	
	@DeleteMapping("/s43/")	
	public String deleteDataFromGetMethod() {
		return "deleteDataFromGetMethod";
	}
	
}
