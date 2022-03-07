package com.pablo.text.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextController {
	
	@GetMapping("text/{id}", produces = "application/json")
    public Text gettext(@PathVariable int id) {
        return findTextById(id);
    }
}
