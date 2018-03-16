package test.java;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import main.java.excilys.cdb.dao.InterfaceDao;
import main.java.excilys.cdb.model.Computer;



public class testService {

	@Mock
	private InterfaceDao<Computer> mockDAOComputer;

	@Before
	public void setUp() throws Exception {
		// MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetNb(){

	}


}
