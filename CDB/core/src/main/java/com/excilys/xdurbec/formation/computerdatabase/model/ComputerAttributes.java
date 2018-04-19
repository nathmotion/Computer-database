package com.excilys.xdurbec.formation.computerdatabase.model;

public enum ComputerAttributes {
	NAME("name"), ID("id"), COMPANY_NAME("company"), INTRODUCED("introduced"), DISCONTINUED("discontinued");
	
	public final String sqlName;
	
	ComputerAttributes(String sqlName) {
		this.sqlName = sqlName;
	}
	
}
