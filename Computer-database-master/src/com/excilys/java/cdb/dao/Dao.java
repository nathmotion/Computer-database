package com.excilys.java.cdb.dao;

import java.util.Optional;

import com.excilys.java.cdb.connectionManager.SingletonConn;

public abstract class Dao<T>{
	
//	protected SingletonConn con = SingletonConn.INSTANCE;

	// requete de recherche sql by id
	public abstract Optional<T> findById(Long id);
	
	// create une tuple dans la base ( une entree )
	public abstract boolean create(T obj);
	
	// modif une donnée d'un table
	public abstract boolean update(T obj);

	// delete une donnée dans la table
	public abstract boolean  delete(T obj);
	
}

