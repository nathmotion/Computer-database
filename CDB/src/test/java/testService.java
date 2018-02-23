package test.java;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import main.java.excilys.cdb.dao.DaoComputer;


public class testService {

	@Mock
	private DaoComputer mockDAOComputer;

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
