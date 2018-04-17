package com.excilys.formation.tbezenger.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name="id")
	private int id;
	
	@Column(name = "username")
	private String username;

	@Column(name = "password", nullable = false)
	private String password;
	
	@Column (name="role")
	private String role;
}
