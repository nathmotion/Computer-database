package main.java.excilys.cdb.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.java.excilys.cdb.model.Computer;

@Repository
public interface ComputerRepositoryImpl extends PagingAndSortingRepository<Computer, Long> {

	Long countByNameContaining(String name);

	Page<Computer> findByNameContaining(Pageable p, String name);
	
}
