package main.java.excilys.cdb.service;

import java.util.ArrayList;

import main.java.excilys.cdb.dao.DaoCompany;
import main.java.excilys.cdb.dao.DaoComputer;
import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;

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
