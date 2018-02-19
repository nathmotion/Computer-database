package com.excilys.java.cdb.service;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.java.cdb.dao.DaoComputer;
import com.excilys.java.cdb.model.Computer;

public enum ServiceComputer {
	INSTANCE;

	private DaoComputer daocomputer ;

	ServiceComputer(){
	}

	public ArrayList<Computer> daoGetAllEntities() {
		daocomputer = DaoComputer.INSTANCE;
		return daocomputer.getAll();
	}

	public ArrayList<Computer> daoGetPage(int offset){
		daocomputer = DaoComputer.INSTANCE;
		return daocomputer.getPage(offset);
	}
	public int daoGetNbComputer() {
		daocomputer =DaoComputer.INSTANCE;
		return daocomputer.getNbComputer();
	}

	public boolean daoCreate(Computer computer) {
		daocomputer= DaoComputer.INSTANCE;
		return daocomputer.create(computer);
	}

	public boolean daoUpdate(Computer computer) {
		daocomputer = DaoComputer.INSTANCE;
		return daocomputer.update(computer);
	}

	public boolean daoDelete(Computer computer) {
		daocomputer = DaoComputer.INSTANCE;
		return daocomputer.delete(computer);
	}
	public Optional<Computer> daoFindById(int id){
		daocomputer = DaoComputer.INSTANCE;
		return daocomputer.findById(id);
	}
}


