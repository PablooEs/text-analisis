package com.pablo.text.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "TEXT")
public class Text {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "HASH", nullable = false, unique = true)
	private String hash;
	@Column(name = "CHARS", nullable = false)
	private Integer chars;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "TEXT_RESULT", joinColumns = @JoinColumn(name = "text_id"))
	@Column(name = "RESULT")
	private Set<Result> result = new HashSet<Result>();

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Integer getChars() {
		return chars;
	}

	public void setChars(Integer chars) {
		this.chars = chars;
	}

	public Set<Result> getResult() {
		return result;
	}

	public void setResult(Set<Result> result) {
		this.result = result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
