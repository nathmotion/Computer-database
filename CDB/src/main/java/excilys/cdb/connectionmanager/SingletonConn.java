package main.java.excilys.cdb.connectionmanager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariProxyConnection;

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
	private HikariProxyConnection conn;
	private String driver;
	// Bundle Ressources
	ResourceBundle bundle = ResourceBundle.getBundle("userInfoDB");
	// Hikari Connection Pool
	HikariConfig config = new HikariConfig();
	HikariDataSource dsConnectionPool;

	SingletonConn() {
		username = bundle.getString("login");
		password = bundle.getString("password");
		url = bundle.getString("url");
		driver = bundle.getString("driver");
		config.setJdbcUrl(url);
		config.setPassword(password);
		config.setUsername(username);
		config.setMaximumPoolSize(100);
		try {
			Class.forName(driver);
			// conn = DriverManager.getConnection(url, username, password);
			dsConnectionPool = new HikariDataSource(config);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initConn() {

		try {
			Class.forName(driver);
			conn = (HikariProxyConnection) dsConnectionPool.getConnection();
		} catch (SQLException e) {
			LOGGER.error("conn :" + e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
		this.conn = (HikariProxyConnection) conn;
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
