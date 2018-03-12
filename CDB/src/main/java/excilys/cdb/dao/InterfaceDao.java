package main.java.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.Optional;
import main.java.excilys.cdb.model.Page;

public interface InterfaceDao<T>{
	
	public abstract ArrayList<T> getAll();
	ArrayList<T> getPage(Page<T> page);
	public int getNbElement();
	public boolean create(T t);
	public void update(T t);
	public void delete(T t);
	public Optional<T> findById(int id);
	public ArrayList<T> getSearch(Page<T> page,String name);
	public int getNbElementSearch(String name);
}

