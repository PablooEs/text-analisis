package com.pablo.text.model;

import java.util.HashMap;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name="TEXT")
public class Text {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	@Column(name="HASH", nullable=false, unique=true)
	private String hash;
	@Column(name="CHARS", nullable=false)
	private Integer chars;
	@ElementCollection
	@CollectionTable(name = "TEXT_RESULT", joinColumns = @JoinColumn(name = "text_id"))
	@Column(name = "RESULT")
	private HashMap<String,Integer> result;
	
	
	
}
