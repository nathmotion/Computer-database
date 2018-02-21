package main.java.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.Optional;

import main.java.excilys.cdb.connectionManager.SingletonConn;

public interface Dao<T>{
	
	public abstract ArrayList<T> getAll();
	public abstract ArrayList<T> getPage(int offset);

}

