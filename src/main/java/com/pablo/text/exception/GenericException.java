package com.pablo.text.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
public class GenericException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public GenericException() {
		super();
	}


	@ResponseBody
    public ResponseEntity<Object> handleAnyUncaughtException() {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("error", true);
		res.put("message", "An error occurred when processing the text");
		res.put("code", 422);
		return new ResponseEntity<Object>(res,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
