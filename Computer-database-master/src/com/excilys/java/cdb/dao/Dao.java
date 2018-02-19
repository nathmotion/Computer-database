package com.excilys.java.cdb.dao;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.java.cdb.connectionManager.SingletonConn;

public interface Dao<T>{

	/**
	 * 
	 * @return
	 */
	public abstract ArrayList<T> getAll();

	public abstract ArrayList<T> getPage(int offset);

}

