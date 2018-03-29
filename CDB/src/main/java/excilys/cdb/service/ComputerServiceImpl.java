package main.java.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;
import main.java.excilys.cdb.repositories.ComputerRepositoryImpl;

@Service
public class ComputerServiceImpl implements ServiceCDB<Computer> {

	private final ComputerRepositoryImpl computerRepository;

	public ComputerServiceImpl(ComputerRepositoryImpl computerRepositoryImpl) {
		this.computerRepository = computerRepositoryImpl;
	}

	public List<Computer> getAllEntities() {
		return computerRepository.findAll();
	}

	public Page<Computer> getPage(Page<Computer> page) {
		page.elementsPage = computerRepository.findAll();
		return page;
	}

	public Page<Computer> getPageByName(Page<Computer> page, String name) {
		page.elementsPage = computerRepository.findPageByName(page, name);
		return page;
	}

	public int getNbSearch(String name) {
		return computerRepository.countByName(name);
	}

	public Page<Computer> getPageByOrder(Page<Computer> page, String critere, Boolean order) {
		page.elementsPage = computerRepository.
		return page;
	}

	public int getNbTotal() {
		return (int)computerRepository.count();
	}

	public void create(Computer computer) {
		computerRepository.save(computer);
	}

	public void update(Computer computer) {
		computerRepository.save(computer);
	}

	public void delete(Computer computer) {
		computerRepository.delete(computer);
	}

	public Optional<Computer> findById(int id) throws SQLException {
		return computerRepository.findById(id);
	}

}
