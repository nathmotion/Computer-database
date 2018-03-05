package main.java.excilys.cdb.connectionmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum SingletonConn {
	INSTANCE;

	final static Logger LOGGER = LogManager.getLogger(SingletonConn.class);
	// url
	private String url;
	// username
	private String username;
	// password
	private String password;
	// objet connection
	private Connection conn;
	private String driver;

	SingletonConn() {

		ResourceBundle bundle = ResourceBundle.getBundle("userInfoDB");
		username = bundle.getString("login");
		password = bundle.getString("password");
		url = bundle.getString("url");
		driver = bundle.getString("driver");

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println(" sql Connection fail ....  " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(" Connection fail ....  " + e.getMessage());
		}
	}

	public void initConn() {
		ResourceBundle bundle = ResourceBundle.getBundle("userInfoDB");
		username = bundle.getString("login");
		password = bundle.getString("password");
		url = bundle.getString("url");
		driver = bundle.getString("driver");
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			LOGGER.error("conn :" + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void initConn(String url, String user, String pass) {
		ResourceBundle bundle = ResourceBundle.getBundle("userInfoDB");
		driver = bundle.getString("driver");
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			LOGGER.error("conn : " + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void closeConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			LOGGER.error("close : " + e.getMessage());
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
			LOGGER.error(" statement creation :" + e.getMessage());
		}
		return null;
	}

}
