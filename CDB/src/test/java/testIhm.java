package test.java;


import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import main.java.excilys.cdb.ihm.Ihm;
import main.java.excilys.cdb.service.ServiceComputer;

public class testIhm {

	@Mock
	Ihm cli;
	@Mock
	ServiceComputer serviceComputer;
	@Mock
	LocalDate mockDate;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("static-access")
	@Test
	public void testConversion() {
		mockDate = LocalDate.now();
		assertSame(Timestamp.valueOf((LocalDate.now().atStartOfDay())),(cli.convertLocalDatetoTimestamp(mockDate)));
	}

}
