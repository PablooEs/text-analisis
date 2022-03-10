package com.pablo.text.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TextException{
	
	public ResponseEntity<Object> textNotFound(){
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("error", true);
		res.put("message", "text not found");
		res.put("code", 404);
		return new ResponseEntity<Object>(res, HttpStatus.NOT_FOUND);
	}
	
	
}
