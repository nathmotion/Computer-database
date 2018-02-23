package main.java.excilys.cdb.connectionmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public enum SingletonConn {
	INSTANCE;

	final Logger logger=Logger.getLogger(SingletonConn.class);
	// url 
	private final String url;
	// username
	private String username;
	// password
	private String password;
	// objet connection
	private Connection conn ;

	SingletonConn(){
		
		 ResourceBundle bundle = ResourceBundle.getBundle("userInfoDB");
	        username = bundle.getString("login");
	        password = bundle.getString("password");
	        url = bundle.getString("url");
	        
		try {
			conn= DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			logger.error(" Connection fail ....  "+e.getMessage());
		}
	}

	public void initConn() {
		
		ResourceBundle bundle = ResourceBundle.getBundle("connection");
        username = bundle.getString("login");
        password = bundle.getString("password");
        
		try {
			conn= DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			logger.error("conn :"+e.getMessage());
		}
	}
	public void initConn(String url, String user, String pass) {
		try {
			conn=DriverManager.getConnection(url,user,pass);
		} catch (SQLException e) {
			logger.error("conn : "+e.getMessage());
		}
	}
	public void closeConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			logger.error("close : "+e.getMessage());
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

	public java.sql.Statement createStatement() {
		try {
			return conn.createStatement();
		} catch (SQLException e) {
			logger.error(" statement creation :" + e.getMessage());
		}
		return null;
	}

}
