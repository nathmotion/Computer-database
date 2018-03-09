package main.java.excilys.cdb.service;

import java.util.ArrayList;
import main.java.excilys.cdb.dao.DaoCompany;
import main.java.excilys.cdb.dao.InterfaceDao;
import main.java.excilys.cdb.model.Company;

public enum ServiceCompany {
	INSTANCE;

	private InterfaceDao<Company> daoCompany = DaoCompany.INSTANCE;


	ServiceCompany(){
	}

	public ArrayList<Company> daoGetAllEntities() {
		return daoCompany.getAll();
	}
	public void daoDelete(Company company) {
		daoCompany.delete(company);
	}
}
