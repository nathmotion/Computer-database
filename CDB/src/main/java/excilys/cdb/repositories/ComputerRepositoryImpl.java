package main.java.excilys.cdb.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.java.excilys.cdb.model.Computer;

@Repository
public interface ComputerRepositoryImpl extends PagingAndSortingRepository<Computer, Integer> {

	Long countByName(String name);
	
}
