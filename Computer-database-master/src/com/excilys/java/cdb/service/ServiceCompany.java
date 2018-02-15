package com.excilys.java.cdb.service;

import com.excilys.java.cdb.dao.DaoCompany;

public enum ServiceCompany {
		INSTANCE;
	
	private DaoCompany daocompany ;
	
	ServiceCompany(){
		daocompany = new DaoCompany();
	}
	/**
	 *  RECUPERE LE DAO DE LA CLASS METIER COMPANY
	 * @return
	 */
	public DaoCompany getDao(){
		
		return daocompany;
		
	}
}
