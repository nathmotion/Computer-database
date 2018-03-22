package main.java.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.java.excilys.cdb.dao.ComputerDaoImpl;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;

@Service
public class ComputerServiceImpl implements ServiceCDB<Computer> {

	private final ComputerDaoImpl daoComputer;

	public ComputerServiceImpl(ComputerDaoImpl daoComputer) {
		this.daoComputer = daoComputer;
	}

	public List<Computer> getAllEntities() {
		return daoComputer.getAll();
	}

	public Page<Computer> getPage(Page<Computer> page) {
		page.elementsPage = daoComputer.getPage(page);
		return page;
	}

	public Page<Computer> getPageByName(Page<Computer> page, String name) {
		page.elementsPage = daoComputer.getSearch(page, name);
		return page;
	}

	public int getNbSearch(String name) {
		return daoComputer.getNbElementSearch(name);
	}

	public Page<Computer> getPageByOrder(Page<Computer> page, String critere, Boolean order) {
		page.elementsPage = daoComputer.getPageSort(page, critere, order);
		return page;
	}

	public int getNbTotal() {
		return daoComputer.getNbElement();
	}

	public void create(Computer computer) {
		daoComputer.create(computer);
	}

	public void update(Computer computer) {
		daoComputer.update(computer);
	}

	public void delete(Computer computer) {
		daoComputer.delete(computer);
	}

	public Optional<Computer> findById(int id) throws SQLException {
		return daoComputer.findById(id);
	}

}
