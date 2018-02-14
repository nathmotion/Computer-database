package com.excilys.java.cdb.connectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum SingletonConn {
	INSTANCE;
	
	// url 
	private String url= "jdbc:mysql://127.0.0.1:3306/computer-database-db?useSSL=false";
	
	// username
	
	private String username ="admincdb";
	
	// password
	
	private String password = "qwerty1234";
	
	// objet connection
	
	private Connection conn ;

	
	
	SingletonConn(){
		try {
			conn= DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("error"+e.getMessage());
		}
	}
	
	public void initConn() {
		try {
			conn= DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	
	
	
}
