package com.persado.assignment.project.model.jpa;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "book_id", unique = true, nullable = false)
	private Integer bookId;

	@Column(name = "book_name", length = 200)
	private String bookName;

	@Column(name = "book_summary", length = 200)
	private String bookSummary;

	@Column(length = 200)
	private String isbn;
	
	@Column(name = "total_copies")
	private Integer totalCopies;

	@Column(name = "available_copies")
	private Integer availableCopies;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	List<Loan> loan;

}
