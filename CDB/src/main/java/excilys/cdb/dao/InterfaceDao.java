package main.java.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.Optional;

public interface InterfaceDao<T>{
	
	public abstract ArrayList<T> getAll();
	ArrayList<T> getPage(int offset, int limitPage);
	public int getNbElement();
	public boolean create(T t);
	public void update(T t);
	public void delete(T t);
	public Optional<T> findById(int id);
}

