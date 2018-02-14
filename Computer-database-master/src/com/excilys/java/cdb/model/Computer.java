package com.excilys.java.cdb.model;

import java.sql.Timestamp;

public class Computer {

	private Long id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Long company_id;
	
	public Computer() {
		
	}


	public Computer(Long id, String name, Timestamp introduction, Timestamp discontinuation,Long company_id) {
	
		this.id=id;
		this.name = name;
		this.introduced = introduction;
		this.discontinued = discontinuation;
		this.company_id=company_id;
		
	}

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getCompany_id() {
		return company_id;
	}


	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Timestamp getIntroduced() {
		return introduced;
	}


	public void setIntroduced(Timestamp introduction) {
		this.introduced = introduction;
	}


	public Timestamp getDiscontinued() {
		return discontinued;
	}


	public void setDiscontinued(Timestamp discontinuation) {
		discontinued = discontinuation;
	}
	
	
	
}
