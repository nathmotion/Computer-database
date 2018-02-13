package com.excilys.java.cdb.model;

import java.sql.Timestamp;

public class Computer {

	private Long id;
	private String name;
	private Timestamp introduction;
	private Timestamp Discontinuation;
	
	
	public Computer() {
		
	}


	public Computer(String name, Timestamp introduction, Timestamp discontinuation) {
	
		this.name = name;
		this.introduction = introduction;
		Discontinuation = discontinuation;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Timestamp getIntroduction() {
		return introduction;
	}


	public void setIntroduction(Timestamp introduction) {
		this.introduction = introduction;
	}


	public Timestamp getDiscontinuation() {
		return Discontinuation;
	}


	public void setDiscontinuation(Timestamp discontinuation) {
		Discontinuation = discontinuation;
	}
	
	
	
}
