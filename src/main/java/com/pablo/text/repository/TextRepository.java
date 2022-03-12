package com.pablo.text.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pablo.text.model.Text;

@Repository
public interface TextRepository extends JpaRepository<Text , Long>{
	public Text findByHash(String hash);
	public Page<Text> findByCharsAndDeletedFalse(Pageable paging, Integer chars);
}
