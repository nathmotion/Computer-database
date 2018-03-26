package main.java.excilys.cdb.connectionmanager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariProxyConnection;

public enum SingletonConn {
	INSTANCE;

	//final static Logger LOGGER = LogManager.getLogger(SingletonConn.class);
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
	private ResourceBundle bundle = ResourceBundle.getBundle("userInfoDB");
	// Hikari Connection Pool
	private HikariConfig config = new HikariConfig();
	private HikariDataSource dsConnectionPool;

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
			dsConnectionPool = new HikariDataSource(config);
		} catch (ClassNotFoundException e) {
			System.out.println("conn instance :" + e.getMessage());
		}
	}

	public void initConn() {
		try {
			Class.forName(driver);
			conn = (HikariProxyConnection) dsConnectionPool.getConnection();
		} catch (SQLException | ClassNotFoundException e) {
			//LOGGER.error("conn :" + e.getMessage());
		}
	}

	public void closeConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			//LOGGER.error("close : " + e.getMessage());
		}
	}

	public Connection getConn() {
		return conn;
	}

	public java.sql.Statement createStatement() {
		try {
			return conn.createStatement();
		} catch (SQLException e) {
			//LOGGER.error(" statement creation :" + e.getMessage());
		}
		return null;
	}

}
