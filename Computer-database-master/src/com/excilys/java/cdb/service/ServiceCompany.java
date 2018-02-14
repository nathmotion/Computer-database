package com.excilys.java.cdb.service;

import com.excilys.java.cdb.dao.DaoCompany;

public enum ServiceCompany {
		INSTANCE;
	
	private DaoCompany dc ;
	
	ServiceCompany(){
		dc = new DaoCompany();
	}
	
	public DaoCompany getDao(){
		
		return dc;
		
	}
}
