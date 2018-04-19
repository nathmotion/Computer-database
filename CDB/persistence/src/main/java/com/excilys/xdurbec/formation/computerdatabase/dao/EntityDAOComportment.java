package com.excilys.xdurbec.formation.computerdatabase.dao;

import java.util.List;
public interface EntityDAOComportment<T>  {
	List<T> getAll() throws ExceptionDAO;
}
