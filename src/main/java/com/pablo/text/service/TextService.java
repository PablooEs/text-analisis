package com.pablo.text.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablo.text.model.Text;
import com.pablo.text.repository.TextRepository;

@Service
public class TextService {
	
	private final TextRepository textRepository;
	
	@Autowired
	public TextService(TextRepository textRepository){
		this.textRepository = textRepository;
	}
	
	public Map<String, String> findTextById(Long id){
		ObjectMapper oMapper = new ObjectMapper();
		Text text = new Text();
		//text = textRepository.findById(id);
		//Map<String, Object> map = oMapper.convertValue(obj, Map.class);
		return null;
	}
}
