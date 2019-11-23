package com.persado.assignment.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.persado.assignment.project.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

	@Query(value = "SELECT e FROM #{#entityName} e where e.availableCopies > 0  ")
	List<Book> findBooksForLoan();
	
}
