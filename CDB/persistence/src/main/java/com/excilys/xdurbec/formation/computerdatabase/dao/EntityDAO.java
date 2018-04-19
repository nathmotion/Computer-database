package com.excilys.xdurbec.formation.computerdatabase.dao;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class EntityDAO {
	protected Logger log = LogManager.getLogger(this.getClass());

	protected void showLogSQLException(SQLException e) {
		log.error(e.getMessage());
	}
	
}
