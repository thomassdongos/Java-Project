package com.persado.assignment.project.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_loan")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_loan_id", unique = true, nullable = false)
	private Integer loanId;

	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private User user;

	@JoinColumn(name = "book_id", referencedColumnName = "book_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Book book;

	@Column(name = "loan_date")
	private LocalDate loanDate;
	
	@Column(name = "returned_date")
	private LocalDate returnedDate;
	
	@Column(name = "is_loaned")
	private boolean isLoaned;

}
