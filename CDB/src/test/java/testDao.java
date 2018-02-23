package test.java;


import static org.junit.Assert.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;


import main.java.excilys.cdb.connectionmanager.SingletonConn;
import main.java.excilys.cdb.dao.DaoComputer;
import main.java.excilys.cdb.model.Computer;


public class testDao {
	final static Logger LOGGER = Logger.getLogger(DaoComputer.class);
	final static String QUERY_GET_ALL ="SELECT id, name, introduced, discontinued, company_id FROM computer";
	final static String QUERY_CREATE_TABLE ="CREATE TABLE computer (id LONG AUTO_INCREMENT, name VARCHAR(30) NOT NULL,date_introduced TIMESTAMP, date_discontinued TIMESTAMP,company_id LONG,PRIMARY KEY(id))";
	final static String QUERY_INSERT_COMPUTER1 ="INSERT INTO computer VALUES ('MacArton',null,null,'5')";
	final static String QUERY_INSERT_COMPUTER2 ="INSERT INTO computer VALUES ('Ipoubelle',null,null,'1')";
	final static String QUERY_INSERT_COMPUTER3 ="INSERT INTO computer VALUES ('windaub',null,null,'4')";

	@Mock
	ResultSet mockResultSet;


	@Before
	public void setUp(){
		SingletonConn conn = SingletonConn.INSTANCE;
	//	conn.initConn(); pas sur ?!
		try(Statement stmnt = conn.createStatement();){
			stmnt.execute(QUERY_CREATE_TABLE);
			conn.getConn().commit();
			stmnt.executeUpdate(QUERY_INSERT_COMPUTER1);
			stmnt.executeUpdate(QUERY_INSERT_COMPUTER2);
			stmnt.executeUpdate(QUERY_INSERT_COMPUTER3);
			conn.getConn().commit();
		}catch(SQLException e) {
			LOGGER.error("erreur de creation de base de donnée ");
		}

	}

	@After
	public void tearDown() throws Exception {

	}

	@BeforeClass
	public static void init()throws Exception{
	}

	@Test
	public void testgetAll() {

		ArrayList<Computer> testComputers=  new ArrayList<Computer>();
		testComputers.add(new Computer((long) 50,"mon test",Timestamp.valueOf(LocalDate.now().atStartOfDay()),Timestamp.valueOf(LocalDate.now().atStartOfDay()),(long)2));


	}


}
