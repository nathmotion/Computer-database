package main.java.excilys.cdb.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.java.excilys.cdb.model.Computer;

@Repository
public interface ComputerRepositoryImpl extends PagingAndSortingRepository<Computer, Integer> {

	Long countByName(String name);

	Page<Computer> findAllOrderByNameAsc(Pageable page);

	Page<Computer> findAllOrderByNameDesc(Pageable page);

	Page<Computer> findAllByName(String name, Pageable pageable);
}
//findAll(pageRequest.of(numeropage,nbElementPage,sOrt,properties)