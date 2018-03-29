package main.java.excilys.cdb.repositories;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;

public interface ComputerRepositoryImpl {

	void save(Computer computer);

	void delete(Computer computer);

	int getNbElement();

	Optional<Computer> findById(int id) throws SQLException;

	List<Computer> getPage(Page<Computer> page);

	List<Computer> getAll();

	public List<Computer> getPageSort(Page<Computer> page, String critere, Boolean order);

	int getNbElementSearch(String name);

	List<Computer> getSearch(Page<Computer> page, String name);

}
