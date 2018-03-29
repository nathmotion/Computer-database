package main.java.excilys.cdb.repositories;

import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_BY_NAME;
import static main.java.excilys.cdb.constantes.ConstanteRequeteSql.QUERY_NB_ELEMENT_BY_NAME;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;

@Repository
public interface ComputerRepositoryImpl extends PagingAndSortingRepository<Computer, Integer> {

	@Query(QUERY_NB_ELEMENT_BY_NAME)
	int countByName(String name);

	@Query(QUERY_BY_NAME)
	List<Computer> findPageByName(Page<Computer> page, String name);

}
