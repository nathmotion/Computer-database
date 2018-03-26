package test.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import main.java.excilys.cdb.connectionmanager.SingletonConn;
import main.java.excilys.cdb.model.Computer;

public class testDao {
	// final static Logger LOGGER = LogManager.getLogger(testDao.class);
	final static String QUERY_GET_ALL = "SELECT id, name, introduced, discontinued, company_id FROM computer";
	final static String QUERY_CREATE_TABLE = "CREATE TABLE computer (id LONG AUTO_INCREMENT, name VARCHAR(30) NOT NULL,date_introduced TIMESTAMP, date_discontinued TIMESTAMP,company_id LONG,PRIMARY KEY(id))";
	final static String QUERY_INSERT_COMPUTER1 = "INSERT INTO computer VALUES ('MacArton',null,null,'5')";
	final static String QUERY_INSERT_COMPUTER2 = "INSERT INTO computer VALUES ('Ipoubelle',null,null,'1')";
	final static String QUERY_INSERT_COMPUTER3 = "INSERT INTO computer VALUES ('windaub',null,null,'4')";

	@Mock
	ResultSet mockResultSet;

	@Before
	public void setUp() {
		SingletonConn conn = SingletonConn.INSTANCE;
		conn.initConn();
		try (Statement stmnt = conn.createStatement()) {
			stmnt.execute(QUERY_CREATE_TABLE);
			conn.getConn().commit();
			stmnt.executeUpdate(QUERY_INSERT_COMPUTER1);
			stmnt.executeUpdate(QUERY_INSERT_COMPUTER2);
			stmnt.executeUpdate(QUERY_INSERT_COMPUTER3);
			conn.getConn().commit();
		} catch (SQLException e) {
			// LOGGER.error("erreur de creation de base de donnée ");
		}

	}

	@After
	public void tearDown() throws Exception {

	}

	@BeforeClass
	public static void init() throws Exception {
	}

	@Test
	public void testgetAll() {

		/*
		 * Mockito.when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		 * Mockito.when(mockResultSet.getLong("id")).thenReturn((long)50);
		 * Mockito.when(mockResultSet.getString("name")).thenReturn("mon test");
		 * Mockito.when(mockResultSet.getTimestamp("date introduced")).thenReturn(
		 * Timestamp.valueOf(LocalDate.now().atStartOfDay()));
		 * Mockito.when(mockResultSet.getTimestamp("date discontinued")).thenReturn(
		 * Timestamp.valueOf(LocalDate.now().atStartOfDay()));
		 * Mockito.when(mockResultSet.getLong("company_id")).thenReturn((long)2);
		 * 
		 * Computer computer = new Computer();
		 * computer.setId(mockResultSet.getLong("id"));
		 * computer.setName(mockResultSet.getString("name"));
		 * computer.setIntroduced(mockResultSet.getTimestamp("date introduced"));
		 * computer.setDiscontinued(mockResultSet.getTimestamp("date discontinued"));
		 * computer.setCompany_id(mockResultSet.getLong("company_id"));
		 * ArrayList<Computer> testComputersrs= new ArrayList<Computer>();
		 * testComputersrs.add(computer);
		 * 
		 * Mockito.when(mockStmnt.executeQuery(QUERY_GET_ALL)).thenReturn(mockResultSet)
		 * ; Mockito.when(mockSingletonConn.createStatement()).thenReturn(mockStmnt);
		 */
		ArrayList<Computer> testComputers = new ArrayList<Computer>();
		// assertEquals(testComputers.get(0), testComputersrs.get(0));

	}

}
