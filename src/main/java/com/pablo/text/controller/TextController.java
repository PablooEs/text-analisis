package com.pablo.text.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pablo.text.model.TextRequest;
import com.pablo.text.service.TextService;

@RestController
public class TextController {
	
	private final TextService textService;
	
	@Autowired
	public TextController(TextService textService) {
		this.textService = textService;
	}
	
	@GetMapping("text/{id}")
    public ResponseEntity<Object> getText(@PathVariable Long id) {
        return textService.findTextById(id);
    }
	
	@PostMapping("text")
	public ResponseEntity<Object> generateText(@RequestBody TextRequest text) {
        return textService.generateText(text);
    }
	
	@DeleteMapping("text/{id}")
	public ResponseEntity<Object> deleteText(@PathVariable Long id) {
        return textService.deleteText(id);
    }
}
