package com.excilys.xdurbec.formation.computerdatabase.service;

import java.util.List;

public interface EntityServiceComportment<ENTITY> {
	List<ENTITY> getAll() throws  ExceptionService;
	
}
