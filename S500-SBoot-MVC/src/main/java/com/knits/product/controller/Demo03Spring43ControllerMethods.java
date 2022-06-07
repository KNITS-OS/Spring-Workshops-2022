package com.knits.product.controller;

import org.springframework.web.bind.annotation.*;


@RestController
public class Demo03Spring43ControllerMethods {

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

	@PatchMapping("/s43/")
	public String getDataFromPatchMapping() {
		return "getDataFromPatchMapping";
	}
	
}
