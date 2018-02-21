package main.java.excilys.cdb.connectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import main.java.excilys.cdb.dao.DaoComputer;

public enum SingletonConn {
	INSTANCE;

	final Logger logger=Logger.getLogger(SingletonConn.class);
	// url 
	private final String url="jdbc:mysql://127.0.0.1:3306/computer-database-db?useSSL=false";
	// username
	private final String username="admincdb";
	// password
	private final String password="qwerty1234";
	// objet connection
	private Connection conn ;

	SingletonConn(){
		try {
			conn= DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			logger.error(" Connection fail ....  "+e.getMessage());
		}
	}

	public void initConn() {
		try {
			conn= DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			logger.error("error"+e.getMessage());
		}
	}

	public void closeConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			logger.error("error"+e.getMessage());
		}
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}
