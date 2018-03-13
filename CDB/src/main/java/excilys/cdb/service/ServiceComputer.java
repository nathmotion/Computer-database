package main.java.excilys.cdb.service;

import java.util.ArrayList;
import java.util.Optional;

import main.java.excilys.cdb.dao.InterfaceDao;
import main.java.excilys.cdb.dao.DaoComputer;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;

public enum ServiceComputer {
	INSTANCE;
	
	private InterfaceDao<Computer> daocomputer= DaoComputer.INSTANCE;

	ServiceComputer(){
	}

	public ArrayList<Computer> getAllEntities() {
		return daocomputer.getAll();
	}

	public Page<Computer> getPage(Page<Computer> page){
		page.elementsPage=daocomputer.getPage(page);
		return page;
	}
	public Page<Computer> getPageByName(Page<Computer> page,String name){
		page.elementsPage=daocomputer.getSearch(page,name);
		return page;
	}
	public int getNbComputerSearch(String name) {
		return daocomputer.getNbElementSearch(name);
	}
	public Page<Computer> getPageByOrder(Page<Computer> page,String critere, Boolean order) {
		page.elementsPage=daocomputer.getPageSort(page,critere,order);
		return page;
	}
	
	public int getNbComputer() {
		return daocomputer.getNbElement();
	}

	public boolean create(Computer computer) {
		return daocomputer.create(computer);
	}

	public void update(Computer computer) {
		daocomputer.update(computer);
	}

	public void delete(Computer computer) {
		daocomputer.delete(computer);
	}
	
	public Optional<Computer> findById(int id){
		return daocomputer.findById(id);
	}
}


