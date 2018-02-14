package com.excilys.java.cdb.dao;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.java.cdb.connectionManager.SingletonConn;

public abstract class Dao<T>{
	
//	protected SingletonConn con = SingletonConn.INSTANCE;

	// requete de recherche sql by id
	public abstract ArrayList<T> getAll();
	

}

