package com.example.mail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.mail.model.Data;
@Repository
public interface Repo extends JpaRepository<Data, String>{

	@Query(value="SELECT email FROM DATA", nativeQuery = true)
	List<String> getEmails();
}
