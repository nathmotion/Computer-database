package main.java.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;

import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;

public interface InterfaceDao<T>{
	
	public abstract List<T> getAll();
	public abstract List<T> getPage(Page<T> page);
	public abstract int getNbElement();
	public abstract void create(T t);
	public abstract void update(T t);
	public abstract void delete(T t);
	public abstract Optional<T> findById(int id);
	public abstract List<T> getSearch(Page<T> page,String name);
	public abstract int getNbElementSearch(String name);
	public abstract List<T> getPageSort(Page<Computer> page,String critere, Boolean order);
}

