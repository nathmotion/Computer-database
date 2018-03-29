package main.java.excilys.cdb.repositories;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;

@Repository
public class ComputerRepository implements ComputerRepositoryImpl {

	@Override
	@Transactional
	public void save(Computer computer) {
		if(computer == null) {
			throw new  IllegalArgumentException(" computer is null ! ");
		}
		
	}

	@Override
	public void delete(Computer computer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNbElement() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Optional<Computer> findById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Computer> getPage(Page<Computer> page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Computer> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Computer> getPageSort(Page<Computer> page, String critere, Boolean order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNbElementSearch(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Computer> getSearch(Page<Computer> page, String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
