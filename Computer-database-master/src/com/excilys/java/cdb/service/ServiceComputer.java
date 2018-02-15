package com.excilys.java.cdb.service;

import com.excilys.java.cdb.dao.DaoComputer;

public enum ServiceComputer {
			INSTANCE;
	

	private DaoComputer dc ;
	
	ServiceComputer(){
		dc = new DaoComputer();
	}
	/**
	 * 		RECUPERE LE DAO DE LA CLASS METIER COMPUTER
	 * @return
	 */
	public DaoComputer getDao(){
		return dc;
		
	}
}
