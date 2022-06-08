package com.knits.product.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = Demo02RequestMappingController.class)
public class Demo02RequestMappingControllerTest {

	@Autowired
	private MockMvc mockMvc;
	//@RequestMapping(value = "/basic/category/{id}/sub/{subCatId}")
	@Test
	public void testGetValueWithPathVariables() throws Exception{

		long id=1L;
		long subCatId=1L;
		String expected= "Get Catyegory=" + id + " SubCategory=" + subCatId;

		mockMvc.perform(get("/basic/category/"+id+"/sub/"+subCatId))
				.andExpect(status().isOk())
				.andExpect(content().string(expected));

	}
	
	//@RequestMapping(value = "/basic/path/io/{numericId:[\\d]+}")
	@Test
	public void  getValueWithNumericPathVariables() throws Exception{
		long numericId=1L;
		String expected = "Get numeric value="+numericId;

		mockMvc.perform(get("/basic/path/io/"+numericId))
				.andExpect(status().isOk())
				.andExpect(content().string(expected));
	}
	
	//@RequestMapping(value = "/basic/path/io/params")
	@Test
	public void  getValueWithRequestParamVariables()throws Exception {

		long id=1L; //@RequestParam("id")
		String expected =  "Get request param value="+id;

		mockMvc.perform(get("/basic/path/io/params?id="+id))
				.andExpect(status().isOk())
				.andExpect(content().string(expected));
	}

       // @RequestMapping(value = { "/basic/demo/io/headers"})

	@Test
	public void  testGetValueWithHeaders() throws Exception{

		String encoding="UTF-8";
		long keepAlive=2000L;
		String messageSource="A mock message source";

		String expected= "Headers values: "+
				" encoding: "+encoding+
				" keepAlive: "+keepAlive+
				" messageSource: "+messageSource;

		mockMvc.perform(get("/basic/demo/io/headers")
						.header("Accept-Encoding",encoding)
						.header("Keep-Alive",keepAlive)
						.header("Message-Source",messageSource)
				)
				.andExpect(status().isOk())
				.andExpect(content().string(expected));
	}
    
	
	//@RequestMapping(value = { "/basic/path/io/params1", "/basic/anotherpath/io/params2" })
	@Test
	public void  getValueMultiplePathMapping() throws Exception{

		String expected= "default mapping";

		mockMvc.perform(get("/basic/path/io/path1"))
				.andExpect(status().isOk())
				.andExpect(content().string(expected));

		mockMvc.perform(get("/basic/anotherpath/io/path2"))
				.andExpect(status().isOk())
				.andExpect(content().string(expected));

	}
	
	
	
	
	//@RequestMapping(value = "*")
	@Test
	public void  getFallback() throws Exception {

		String expected= "Fallback for GET Requests";

		mockMvc.perform(get("/other/whatever"))
				.andExpect(status().isOk())
				.andExpect(content().string(expected));
	}
	
	//@RequestMapping(value = "*", method = { RequestMethod.GET, RequestMethod.POST })
	@Test
	public void  allFallback() throws Exception {
		String expected= "Fallback for All Requests";

		mockMvc.perform(get("/all-methods/whatever"))
				.andExpect(status().isOk())
				.andExpect(content().string(expected));

		mockMvc.perform(post("/all-methods/whatever-as-post"))
				.andExpect(status().isOk())
				.andExpect(content().string(expected));
	}
	
}
