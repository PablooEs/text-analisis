package com.pablo.text.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pablo.text.exception.TextException;
import com.pablo.text.model.Result;
import com.pablo.text.model.Text;
import com.pablo.text.model.TextRequest;
import com.pablo.text.repository.TextRepository;

@Service
public class TextService {

	private final TextRepository textRepository;
	private final TextException textException;

	@Autowired
	public TextService(TextRepository textRepository) {
		this.textRepository = textRepository;
		this.textException = new TextException();
	}

	public ResponseEntity<Object> findTextById(Long id) {
		Text text = new Text();
		try {
			text = textRepository.findById(id).get();
			return new ResponseEntity<Object>(text, HttpStatus.OK);
		} catch (Exception e) {
			return textException.textNotFound();
		}
	}

	public ResponseEntity<Object> generateText(TextRequest textRequest) {
		String hash = getHash(textRequest);
		Text text = textRepository.findByHash(hash);
		if (text != null) {
			System.out.println("El texto ya existia");
			return new ResponseEntity<Object>(text, HttpStatus.OK);
		} else {
			Text newText = new Text();
			newText.setResult(generateResult(textRequest));
			newText.setChars(textRequest.getChars());
			newText.setHash(hash);
			textRepository.save(newText);
			return new ResponseEntity<Object>(newText, HttpStatus.OK);
		}
	}

	public String getHash(TextRequest textRequest) {
		MessageDigest md;
		String s = "";
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(textRequest.toString().getBytes());
			s = Hex.encodeHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return s;
	}

	public List<Result> generateResult(TextRequest textRequest) {
		LinkedHashMap<String, Integer> strCountMap = new LinkedHashMap<String, Integer>();
		
		//Split the text request by chars 
		List<String> splited = splitText(textRequest.getText(), textRequest.getChars());
		
		//Count the occurrences
		for (String s : splited) {
			if (strCountMap.containsKey(s)) {
				strCountMap.put(s, strCountMap.get(s) + 1);
			} else {
				strCountMap.put(s, 1);
			}
		}
		List<Result> result = new ArrayList<Result>();
		//Transform the result to the Result Entity
		for( Map.Entry<String, Integer> entry : strCountMap.entrySet()){
			result.add(new Result(entry.getKey(),entry.getValue()));  		  
		}
		return result;
	}

	public List<String> splitText(String text, int size) {
		List<String> splited = new ArrayList<>();

		for (int start = 0; start < text.length() + 1; start++) {
			if (start + size < text.length() + 1) {
				splited.add(text.substring(start, start + size));
			}
		}
		return splited;
	}
	
	public ResponseEntity<Object> deleteText(Long id) {
		Text text = new Text();
		try {
			text = textRepository.findById(id).get();
			if(text!=null && text.isDeleted()!=true) {
				text.setDeleted(true);
				textRepository.save(text);
				return new ResponseEntity<Object>("{}", HttpStatus.OK);
			}else {
				return textException.textNotFound();
			}
		} catch (Exception e) {
			return textException.textNotFound();
		}
	}

}
