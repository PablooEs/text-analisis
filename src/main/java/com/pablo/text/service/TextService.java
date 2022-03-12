package com.pablo.text.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablo.text.exception.GenericException;
import com.pablo.text.exception.TextException;
import com.pablo.text.model.Result;
import com.pablo.text.model.Text;
import com.pablo.text.model.TextRequest;
import com.pablo.text.repository.TextRepository;

@Service
public class TextService {

	private final TextRepository textRepository;
	private final TextException textException;
	private final GenericException genericException;

	private static final Logger LOG = LogManager.getLogger(TextService.class);

	@Autowired
	public TextService(TextRepository textRepository) {
		this.textRepository = textRepository;
		this.textException = new TextException();
		this.genericException = new GenericException();
	}

	public ResponseEntity<Object> findTextById(Long id) {
		LOG.info("Se testea findTextByID");
		Text text = new Text();
		try {
			text = textRepository.findById(id).get();
			if (text.isDeleted() == true) {
				return textException.textNotFound();
			} else {
				LOG.info(text.toString());
				return new ResponseEntity<Object>(text, HttpStatus.OK);
			}
		} catch (Exception e) {
			return textException.textNotFound();
		}
	}

	public ResponseEntity generateText(TextRequest textRequest, Integer chars) {
		LOG.info("Se testea generateText");
		try {
			if (textRequest.getChars() == null || textRequest.getChars() < 2) {
				textRequest.setChars(chars);
			}
			String textToLowerCase = textRequest.toString().toLowerCase();
			String hash = getHash(textToLowerCase);
			Text text = textRepository.findByHash(hash);
			if (text != null) {
				LOG.info("El texto ya existia");
				if(text.isDeleted()==true) {
					text.setDeleted(false);
					textRepository.save(text);
				}		
				
				Map<String, Object> existsIn = new HashMap<String, Object>();
				existsIn.put("id", text.getId());
				existsIn.put("url", "/text/" + text.getId().toString());
				LOG.info(existsIn.toString());
				return new ResponseEntity<Object>(existsIn, HttpStatus.CREATED);
			} else {
				Text newText = new Text();
				newText.setResult(generateResult(textRequest));
				newText.setChars(textRequest.getChars());
				newText.setHash(hash);
				Text savedText = textRepository.save(newText);
				Map<String, Object> saved = new HashMap<String, Object>();
				saved.put("id", savedText.getId());
				saved.put("url", "/text/" + savedText.getId().toString());
				return new ResponseEntity<Object>(saved, HttpStatus.CREATED);
			}

		} catch (Exception e) {
			LOG.info(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(genericException.handleAnyUncaughtException());
		}

	}

	public String getHash(String textRequest) {
		LOG.info("Se testea getHash");
		MessageDigest md;
		String s = "";
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(textRequest.getBytes());
			s = Hex.encodeHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return s;
	}

	public List<Result> generateResult(TextRequest textRequest) {
		LOG.info("Se testea generateResult");
		LinkedHashMap<String, Integer> strCountMap = new LinkedHashMap<String, Integer>();

		if (textRequest.getChars() > textRequest.getText().length()) {
			textRequest.setChars(textRequest.getText().length());
		}
		// Split the text request by chars
		List<String> splited = splitText(textRequest.getText(), textRequest.getChars());

		// Count the occurrences
		for (String s : splited) {
			if (strCountMap.containsKey(s)) {
				strCountMap.put(s, strCountMap.get(s) + 1);
			} else {
				strCountMap.put(s, 1);
			}
		}
		List<Result> result = new ArrayList<Result>();
		// Transform the result to the Result Entity
		for (Map.Entry<String, Integer> entry : strCountMap.entrySet()) {
			result.add(new Result(entry.getKey(), entry.getValue()));
		}
		return result;
	}

	public List<String> splitText(String text, int size) {
		LOG.info("Se testea splitText");
		List<String> splited = new ArrayList<>();

		for (int start = 0; start < text.length() + 1; start++) {
			if (start + size < text.length() + 1) {
				splited.add(text.substring(start, start + size));
			}
		}
		return splited;
	}

	public ResponseEntity<Object> deleteText(Long id) {
		LOG.info("Se testea deleteText");
		Text text = new Text();
		try {
			text = textRepository.findById(id).get();
			if (text != null && text.isDeleted() != true) {
				text.setDeleted(true);
				textRepository.save(text);
				return new ResponseEntity<Object>("{}", HttpStatus.OK);
			} else {
				return textException.textNotFound();
			}
		} catch (Exception e) {
			return textException.textNotFound();
		}
	}

	public ResponseEntity getAllText(Integer chars, int page, int size) {
		LOG.info("Se testea getAllText");
		Pageable paging = null;
		Page<Text> pagedResult = null;
		try {
			if (page > 1) {
				paging = PageRequest.of(page, size);
			} else {
				paging = PageRequest.of(0, size);
			}
			if (chars != null) {
				pagedResult = textRepository.findByCharsAndDeletedFalse(paging, chars);
			} else {
				pagedResult = textRepository.findAll(paging);
			}

			if (pagedResult.hasContent()) {
				LOG.info("PagedResult: "+ pagedResult.getContent().toString());
				pagedResult.getContent().forEach(t->{
					LOG.info("Text: "+ t.toString());
					for(Result result: t.getResult()) {
						LOG.info("Result: "+ result.toString());
					}
				});
				
				return ResponseEntity.status(HttpStatus.OK).body(pagedResult.getContent());
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<Text>());
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(genericException.handleAnyUncaughtException());
		}

	}

}
