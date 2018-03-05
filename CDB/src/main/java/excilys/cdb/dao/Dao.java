package main.java.excilys.cdb.dao;

import java.util.ArrayList;

import main.java.excilys.cdb.model.Computer;

public interface Dao<T>{
	
	public abstract ArrayList<T> getAll();
	ArrayList<T> getPage(int offset, int limitPage);

}

