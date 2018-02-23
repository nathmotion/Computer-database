package main.java.excilys.cdb.dao;

import java.util.ArrayList;

public interface Dao<T>{
	
	public abstract ArrayList<T> getAll();
	public abstract ArrayList<T> getPage(int offset);

}

