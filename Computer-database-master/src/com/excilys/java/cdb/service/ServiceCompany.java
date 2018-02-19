package com.excilys.java.cdb.service;

import java.util.ArrayList;

import com.excilys.java.cdb.dao.DaoCompany;
import com.excilys.java.cdb.dao.DaoComputer;
import com.excilys.java.cdb.model.Company;
import com.excilys.java.cdb.model.Computer;

public enum ServiceCompany {
	INSTANCE;

	private DaoCompany daocompany ;

	ServiceCompany(){
	}
	
	public ArrayList<Company> daoGetAllEntities() {
		daocompany = DaoCompany.INSTANCE;
		return daocompany.getAll();
	}
	
	public ArrayList<Company> daoGetPage(int offset){
		daocompany = DaoCompany.INSTANCE;
		return daocompany.getPage(offset);
	}
	
}
