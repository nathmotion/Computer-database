package main.java.excilys.cdb.service;

import java.util.ArrayList;
import java.util.Optional;

import main.java.excilys.cdb.dao.DaoComputer;
import main.java.excilys.cdb.dao.Page;
import main.java.excilys.cdb.model.Computer;

public enum ServiceComputer {
	INSTANCE;
	
	private DaoComputer daocomputer= DaoComputer.INSTANCE;

	ServiceComputer(){
	}

	public ArrayList<Computer> daoGetAllEntities() {
		return daocomputer.getAll();
	}

	public Page<Computer> daoGetPage(int offset, int limitPage){
		Page<Computer> page= new Page<Computer>(offset,daocomputer.getPage(offset,limitPage));
		return page;
	}
	
	public int daoGetNbComputer() {
		return daocomputer.getNbComputer();
	}

	public boolean daoCreate(Computer computer) {
		return daocomputer.create(computer);
	}

	public void daoUpdate(Computer computer) {
		daocomputer.update(computer);
	}

	public void daoDelete(Computer computer) {
		daocomputer.delete(computer);
	}
	
	public Optional<Computer> daoFindById(int id){
		return daocomputer.findById(id);
	}
}


