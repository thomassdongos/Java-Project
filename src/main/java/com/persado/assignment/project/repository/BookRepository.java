package com.persado.assignment.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persado.assignment.project.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

	
	
}
