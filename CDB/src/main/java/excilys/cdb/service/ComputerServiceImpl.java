package main.java.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.repositories.ComputerRepositoryImpl;

@Service
public class ComputerServiceImpl {

	private final ComputerRepositoryImpl computerRepository;

	public ComputerServiceImpl(ComputerRepositoryImpl computerRepositoryImpl) {
		this.computerRepository = computerRepositoryImpl;
	}

	public List<Computer> getAll() {
		return (List<Computer>) computerRepository.findAll();
	}

	public Page<Computer> getPage(int numPage,int nbElement) {
		Page<Computer> page = computerRepository.findAll(PageRequest.of(numPage, nbElement));
		return page;
	}

	public Page<Computer> getPageByName(int numPage,int nbElementPage,String name) {
		Page<Computer> page = computerRepository.findAll(PageRequest.of(numPage,nbElementPage,Sort.Direction.ASC,name));
		return page;
	}

	public long getNbSearch(String name) {
		return computerRepository.countByName(name);
	}

	public Page<Computer> getPageByOrder(int numPage,int nbElementPage, Direction direction) {
		Page<Computer> page = computerRepository.findAll(PageRequest.of(numPage, nbElementPage, direction));
		return page;
	}

	public int getNbTotal() {
		return (int) computerRepository.count();
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
