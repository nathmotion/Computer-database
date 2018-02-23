package main.java.excilys.cdb.service;

import java.util.ArrayList;

import main.java.excilys.cdb.dao.DaoCompany;
import main.java.excilys.cdb.model.Company;

public enum ServiceCompany {
	INSTANCE;

	private DaoCompany daoCompany ;

	ServiceCompany(){
	}

	public ArrayList<Company> daoGetAllEntities() {
		daoCompany = DaoCompany.INSTANCE;
		return daoCompany.getAll();
	}

	public ArrayList<Company> daoGetPage(int offset){
		daoCompany = DaoCompany.INSTANCE;
		return daoCompany.getPage(offset);
	}

}
