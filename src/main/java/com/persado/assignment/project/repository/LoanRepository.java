package com.persado.assignment.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.persado.assignment.project.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

	@Query(value = "SELECT e FROM #{#entityName} e where e.isLoaned = TRUE  ")
	List<Loan> findAllBooksForReturn();
}
