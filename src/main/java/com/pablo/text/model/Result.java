package com.pablo.text.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

@Embeddable
@Table(name="RESULT")
public class Result {
	@Column(name="CONTENT", nullable=false)
	private String content;
	@Column(name="COUNT", nullable=false)
	private Integer count;
	
	public Result() {
		super();
	}
	public Result(String content, Integer count) {
		super();
		this.content = content;
		this.count = count;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
