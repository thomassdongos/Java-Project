package com.persado.assignment.project.model;

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
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true)
	private Integer userId;

	@Column(length = 200)
	private String firstname;

	@Column(length = 200)
	private String lastname;

	@Column(length = 200)
	private String useraddress;

	@Column(name = "total_book_loaned")
	private Integer totalBookedLoaned;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Book> bookList;

}
