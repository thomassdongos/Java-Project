package com.persado.assignment.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.persado.assignment.project.model.Book;
import com.persado.assignment.project.model.Loan;
import com.persado.assignment.project.model.User;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

	@Query(value = "SELECT e FROM #{#entityName} e where e.isLoaned = TRUE  ")
	List<Loan> findAllBooksForReturn();
	
	@Query(value = "SELECT e.user FROM #{#entityName} e where e.book.bookId = :bookId  ")
	List<User> findUsersWithBook(@Param("bookId") Integer bookId);
	
	@Query(value = "SELECT e.book FROM #{#entityName} e where e.user.userId = :userId  ")
	List<Book> findBooksUserLoaned(@Param("userId") Integer userId);
	
	@Query(value = "SELECT e FROM #{#entityName} e where e.user.userId = :userId AND e.book.bookId = :bookId AND e.isLoaned = TRUE  ")
	Loan findBookLoaned(@Param("bookId") Integer bookId, @Param("userId") Integer userId);
	
}
