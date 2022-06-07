package com.knits.product.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.web.bind.annotation.*;

@WebMvcTest(controllers = Demo02RequestMappingController.class)
public class Demo02IOMappingControllerTest {

	@RequestMapping(value = "/basic/category/{id}/sub/{subCatId}")
	@ResponseBody
	public String getValueWithPathVariables(@PathVariable long id, @PathVariable long subCatId) {
		return "Get Catyegory=" + id + " SubCategory=" + subCatId;
	}
	
	@RequestMapping(value = "/basic/path/io/{numericId:[\\d]+}")
	@ResponseBody
	public String getValueWithNumericPathVariables(@PathVariable final long numericId) {
		return "Get numeric value="+numericId;
	}
	
	@RequestMapping(value = "/basic/path/io/params")
	@ResponseBody
	public String getValueWithRequestParamVariables(@RequestParam("id") long id) {
		return "Get request param value="+id;
	}

    
    @RequestMapping(value = { "/basic/demo/io/headers"})
	@ResponseBody
	public String getValueMultiplePathMapping( 
			@RequestHeader("Accept-Encoding") String encoding,
			@RequestHeader("Keep-Alive") long keepAlive,
			@RequestHeader("Message-Source") String messageSource
			) {
    	    	
		return "Headers values: "+
				" encoding: "+encoding+
				" keepAlive: "+keepAlive+
				" messageSource: "+messageSource;
	}
    
	
	@RequestMapping(value = { "/basic/path/io/params1", "/basic/anotherpath/io/params2" })
	@ResponseBody
	public String getValueMultiplePathMapping() {
		return "default mapping";
	}
	
	
	
	
	@RequestMapping(value = "*")
	@ResponseBody
	public String getFallback() {
		return "Fallback for GET Requests";
	}
	
	@RequestMapping(value = "*", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String allFallback() {
		return "Fallback for All Requests";
	}
	
}
