package client.communication;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import shared.model.*;
import shared.communication.*;
import client.ClientException;
import server.database.Database;
import server.database.DatabaseException;
import server.database.FieldsDAO;
import server.database.ProjectsDAO;
import server.importer.DataImporter;
import server.importer.IndexerData;

public class ClientValidateUserTest {

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Load database driver	
		Database.initialize();
		
	
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
		
	private Database db;

	@Before
	public void setUp() throws Exception {

		// Delete all fields from the database	
		db = new Database();		
		db.startTransaction();
		
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		File file = new File("Records/Records.xml");
		Document doc = builder.parse(file);
		doc.getDocumentElement().normalize();
		
		Element root = doc.getDocumentElement();

		IndexerData indexerData = new IndexerData(root);
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
	}
	
	@Test
	public void testUserIsValid() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		ValidateUser_Params params;
		try {
			
			params = new ValidateUser_Params();
			params.setUser("test1");
			params.setPassword("test1");
			ValidateUser_Result result = communicator.validateUser(params);
			assertEquals(result.getUser_first_name(), "Test");
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	@Test
	public void testUserIsInvalid() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		ValidateUser_Params params;
		try {
			
			params = new ValidateUser_Params();
			params.setUser("test");
			params.setPassword("test");
			ValidateUser_Result result = communicator.validateUser(params);
			assertEquals(result.isValid(), false);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	@Test
	public void testEmptyUserTable() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		ValidateUser_Params params;
		try {
			db.getUsersDAO().dropTable();
			params = new ValidateUser_Params();
			params.setUser("test");
			params.setPassword("test");
			ValidateUser_Result result = communicator.validateUser(params);
			assertEquals(result.isValid(), false);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	@Test
	public void testBadServerConnection() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator("host", 9000);
		
		ValidateUser_Params params;
		try {
			db.getUsersDAO().dropTable();
			params = new ValidateUser_Params();
			params.setUser("test");
			params.setPassword("test");
			
			try{
				ValidateUser_Result result = communicator.validateUser(params);
			}catch(Exception e){
				assert(false);
			}
			assert(true);
			
		} catch (DatabaseException e) {
			e.printStackTrace();
		}	
		
	}

}
