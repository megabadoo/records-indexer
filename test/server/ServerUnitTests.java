package server;

import org.junit.* ;
import static org.junit.Assert.* ;

public class ServerUnitTests {
	
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	
	@Test
	public void test_1() {
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	
	public static void main(String[] args) {
		
		String[] testClasses = new String[] {
				"server.ServerUnitTests",
				"server.database.FieldsDAOTest",
				"server.database.ProjectsDAOTest",
				"server.database.RecordsDAOTest",
				"server.database.UsersDAOTest",
				"server.database.ValuesDAOTest",
				"client.communication.ClientValidateUserTest",
				"client.communication.ClientDownloadBatchTest",
				"client.communication.ClientSearchTest",
				"client.communication.ClientSubmitBatchTest",
				"client.communication.ClientGetFieldsTest",
				"client.communication.ClientGetProjectsTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

