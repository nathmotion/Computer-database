package main.java.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceCDB<T> {

	public List<T> getAllEntities();

	public Page<T> getPage(Pageable page);

	public Page<T> getPageByName(String name,Pageable page);

	public int getNbSearch(String name);

	public Page<T> getPageByOrder(Pageable page, String critere, Boolean order);

	public int getNbTotal();

	public void create(T obj);

	public void update(T obj);

	public void delete(T obj);

	public Optional<T> findById(int id) throws SQLException;
}
