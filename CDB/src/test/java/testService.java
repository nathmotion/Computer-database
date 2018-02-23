package test.java;

import static org.junit.Assert.*;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import main.java.excilys.cdb.dao.DaoComputer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DaoComputer.class})
@PowerMockIgnore({"javax.management.*"})
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
		Whitebox.setInternalState(DaoComputer.class, "INSTANCE", mockDAOComputer);
		Mockito.when(mockDAOComputer.getNbComputer()).thenReturn(Mockito.anyInt());

	}


}
