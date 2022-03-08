package com.pablo.text.model;

public class TextRequest {
	
	private String text;
	private Integer chars;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getChars() {
		return chars;
	}
	public void setChars(Integer chars) {
		this.chars = chars;
	}
	@Override
	public String toString() {
		return "{text: " + text + ", chars: " + chars + "}";
	}
	
}
