package com.pablo.text.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pablo.text.model.Result;
import com.pablo.text.model.Text;
import com.pablo.text.model.TextRequest;
import com.pablo.text.repository.TextRepository;

@Service
public class TextService {
	
	private final TextRepository textRepository;
	
	@Autowired
	public TextService(TextRepository textRepository){
		this.textRepository = textRepository;
	}
	
	public ResponseEntity<Object> findTextById(Long id){
		Text text = new Text();
		try {
			text = textRepository.findById(id).get();
			return new ResponseEntity<Object>(text, 
				      HttpStatus.OK);
		}catch(Exception e) {
			Map<String, Object> res = new HashMap<String,Object>();
			res.put("error",true);
			res.put("message","text not found");
			res.put("code", 404);
			return new ResponseEntity<Object>(res, HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<Object> generateText(TextRequest textRequest){
		String hash = getHash(textRequest);
		Text text = textRepository.findByHash(hash);
		if(text != null) {
			return new ResponseEntity<Object>(text, 
				      HttpStatus.OK);
		}else {
			Text newText = new Text();
			newText.setResult(generateResult(textRequest));
			newText.setChars(textRequest.getChars());
			newText.setHash(hash);
			return new ResponseEntity<Object>(text, 
				      HttpStatus.OK);
		}
		
	}
	
	public String getHash(TextRequest textRequest){
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
	
	public Set<Result> generateResult(TextRequest textRequest){
        HashMap<String, Integer> strCountMap
            = new HashMap<String, Integer>();
 
        List <String> splited = splitText(textRequest.getText(),textRequest.getChars());
        
        for (String s : splited) {
            if (strCountMap.containsKey(s)) {
                strCountMap.put(s, strCountMap.get(s) + 1);
            }
            else {
                strCountMap.put(s, 1);
            }
        }
        System.out.println(strCountMap);
        return null;
	}
	
	public List<String> splitText(String text, int size) {
		List<String> ret = new ArrayList<>();
		 
        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        for (int start = 1; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
	    return ret;
	}
}


