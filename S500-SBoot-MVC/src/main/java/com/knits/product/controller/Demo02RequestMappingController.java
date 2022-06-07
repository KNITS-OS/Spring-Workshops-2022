package com.knits.product.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class Demo02RequestMappingController {

	@RequestMapping(value = "/basic/category/{id}/sub/{subCatId}")
	public String getValueWithPathVariables(@PathVariable long id, @PathVariable long subCatId) {
		return "Get Catyegory=" + id + " SubCategory=" + subCatId;
	}
	
	@RequestMapping(value = "/basic/path/io/{numericId:[\\d]+}")
	public String getValueWithNumericPathVariables(@PathVariable final long numericId) {
		return "Get numeric value="+numericId;
	}
	
	@RequestMapping(value = "/basic/path/io/params")
	public String getValueWithRequestParamVariables(@RequestParam("id") long id) {
		return "Get request param value="+id;
	}

	@RequestMapping(value = "/basic/path/io/params")
	public String getValueWithRequestParamVariablesAsObject(@RequestParam("id") long id) {
		return "Get request param value="+id;
	}

	@RequestMapping(value = { "/basic/demo/io/headers"})
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
    
	
	@RequestMapping(value = { "/basic/path/io/path1", "/basic/anotherpath/io/path2" })
	public String getValueMultiplePathMapping() {
		return "default mapping";
	}

	@RequestMapping(value = "*")
	public String getFallback() {
		return "Fallback for GET Requests";
	}
	
	@RequestMapping(value = "*", method = { RequestMethod.GET, RequestMethod.POST })
		public String allFallback() {
		return "Fallback for All Requests";
	}
	
}
