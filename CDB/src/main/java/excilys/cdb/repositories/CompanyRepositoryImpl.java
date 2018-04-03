package main.java.excilys.cdb.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import main.java.excilys.cdb.model.Company;

@Repository 
public interface CompanyRepositoryImpl extends PagingAndSortingRepository<Company, Integer>{

}
