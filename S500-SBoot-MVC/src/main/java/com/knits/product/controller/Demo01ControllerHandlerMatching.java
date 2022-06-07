package com.knits.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo01ControllerHandlerMatching {

	@RequestMapping(value = "/basic/path")
	public String getSomeResourceAsStringByPathMapping() {
		//throw new ServiceException("Duplicate Key on table <SomeTable>", -900);
		return "value from uri path mapping";
	}
	
	@RequestMapping(value = "/basic/path",method = RequestMethod.POST)
	public String getSomeResourceAsStringByPathMappingPost() {
		return "value from uri path mapping method POST";
	}
		
	@RequestMapping(value = "/basic/path/headers", headers = "key=val")
	public String getSomeResourceAsStringByPathMappingWithHeader() {
		return "value from uri path mapping method Header key=val";
	}
	
	@RequestMapping(value = "/basic/path/multiple/headers", headers = { "key1=val1", "key2=val2" })
	public String getSomeResourceAsStringByPathMappingWithMultipleHeader() {
		return "value from uri path mapping method multiple Headers key1=val1, key2=val2";
	}
	
	@RequestMapping(value = "/basic/path/headers/accept", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getSomeResourceAsStringAcceptHeader() {
		return "value from uri path mapping method Accept Headers";
	}
	
	@RequestMapping(value = "/basic/path/headers/produces", method = RequestMethod.GET, produces = "application/json")
	public String getSomeResourceAsStringProducer() {
		return "value from uri path mapping method Produce json";
	}
	
	
	
}
