package com.pablo.text.controller;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pablo.text.model.Text;
import com.pablo.text.model.TextRequest;
import com.pablo.text.service.TextService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class TextController {
	
	private final TextService textService;

	@Autowired
	public TextController(TextService textService) {
		this.textService = textService;
	}

	@GetMapping("/text")
	public ResponseEntity<List<Text>> getAlltext(@RequestParam(required = false, defaultValue = "2") @Min(2) Integer chars,
			@RequestParam(defaultValue = "1") @Min(1) int page,
			@RequestParam(defaultValue = "10") @Min(10) @Max(100) int rpp) {
		return textService.getAllText(chars, page, rpp);
	}

	@GetMapping("text/{id}")
	public ResponseEntity<Object> getText(@PathVariable Long id) {
		return textService.findTextById(id);
	}

	@PostMapping("text")
	public ResponseEntity<Object> generateText(@RequestBody TextRequest text,
			@RequestParam(required = false,defaultValue = "2") @Min(2) Integer chars) {
		return textService.generateText(text, chars);
	}

	@DeleteMapping("text/{id}")
	public ResponseEntity<Object> deleteText(@PathVariable Long id) {
		return textService.deleteText(id);
	}
}
