package com.excilys.xdurbec.formation.computerdatabase.dto;

public class CompanyDTO {
	private String name;
	private int id;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "CompanyDTO [name=" + name + ", id=" + id + "]";
	}
	
	
	

}
