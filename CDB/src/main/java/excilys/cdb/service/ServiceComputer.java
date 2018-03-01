package main.java.excilys.cdb.service;

import java.util.ArrayList;
import java.util.Optional;

import main.java.excilys.cdb.dao.DaoComputer;
import main.java.excilys.cdb.model.Computer;

public enum ServiceComputer {
	INSTANCE;
	
	private DaoComputer daocomputer ;

	ServiceComputer(){
	}

	public ArrayList<Computer> daoGetAllEntities() {
		return DaoComputer.INSTANCE.getAll();
	}

	public ArrayList<Computer> daoGetPage(int offset){
		return DaoComputer.INSTANCE.getPage(offset);
	}
	
	public int daoGetNbComputer() {
		return DaoComputer.INSTANCE.getNbComputer();
	}

	public boolean daoCreate(Computer computer) {
		daocomputer= DaoComputer.INSTANCE;
		return daocomputer.create(computer);
	}

	public void daoUpdate(Computer computer) {
		daocomputer = DaoComputer.INSTANCE;
		 daocomputer.update(computer);
	}

	public void daoDelete(Computer computer) {
		daocomputer = DaoComputer.INSTANCE;
		daocomputer.delete(computer);
	}
	
	public Optional<Computer> daoFindById(int id){
		daocomputer = DaoComputer.INSTANCE;
		return daocomputer.findById(id);
	}
}


