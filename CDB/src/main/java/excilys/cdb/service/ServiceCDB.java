package main.java.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import main.java.excilys.cdb.model.Page;

public interface ServiceCDB<T> {

	public List<T> getAllEntities();

	public Page<T> getPage(Page<T> page);

	public Page<T> getPageByName(Page<T> page, String name);

	public int getNbSearch(String name);

	public Page<T> getPageByOrder(Page<T> page, String critere, Boolean order);

	public int getNbTotal();

	public void create(T obj);

	public void update(T obj);

	public void delete(T obj);

	public Optional<T> findById(int id);
}
