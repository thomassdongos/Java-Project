package com.persado.assignment.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persado.assignment.project.model.Loan;
import com.persado.assignment.project.repository.LoanRepository;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepository;
	
	/**
	 * Find all users
	 * 
	 * @return	List<User>
	 */
	public List<Loan> findAllBooksForReturn() {
		
		return loanRepository.findAllBooksForReturn();
	}
}
