package com.knits.product.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knits.product.dto.UserDto;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.ByteArrayOutputStream;

public class HttpUtils {

	
	public static UserDto parseIntoUser(String userAsString){
		UserDto userDto=null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			userDto= mapper.readValue(userAsString,UserDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return userDto;
	}
	
	public static byte[] serializeUser(UserDto userDto){
		ByteArrayOutputStream bos=null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			bos= new ByteArrayOutputStream();
			mapper.writeValue(bos,userDto);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			IOUtils.closeQuietly(bos);
		}
		return bos.toByteArray();
		
	}
}
