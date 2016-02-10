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

public class ClientGetFieldsTest {

	
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
	public void testWithProject() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		GetFields_Params params;
		try {
			
			//with project specified
			params = new GetFields_Params();
			params.setUser("test1");
			params.setPassword("test1");
			params.setProject(1);
			GetFields_Result result = communicator.getFields(params);
			
			List<String> fieldTitles = new ArrayList<String>();
			fieldTitles.add("Last Name");
			fieldTitles.add("First Name");
			fieldTitles.add("Gender");
			fieldTitles.add("Age");
			for(int x=0; x<result.getFields().size(); x++){
				Field f = result.getFields().get(x);
				assertEquals(f.getTitle(), fieldTitles.get(x));
			}
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	

	@Test
	public void testWithoutProject() throws ClientException {
		ClientCommunicator communicator = new ClientCommunicator();
	
		GetFields_Params params;
		try {
		
			//with project specified
			params = new GetFields_Params();
			params.setUser("test1");
			params.setPassword("test1");
			GetFields_Result result = communicator.getFields(params);
		
			List<String> fieldTitles = new ArrayList<String>();
			fieldTitles.add("Last Name");
			fieldTitles.add("First Name");
			fieldTitles.add("Gender");
			fieldTitles.add("Age");
			fieldTitles.add("Gender");
			fieldTitles.add("Age");
			fieldTitles.add("Last Name");
			fieldTitles.add("First Name");
			fieldTitles.add("Ethnicity");
			fieldTitles.add("Last Name");
			fieldTitles.add("First Name");
			fieldTitles.add("Age");
			fieldTitles.add("Ethnicity");
			for(int x=0; x<result.getFields().size(); x++){
				Field f = result.getFields().get(x);
				assertEquals(f.getTitle(), fieldTitles.get(x));
			}
		
	} catch (DatabaseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
}
	
	@Test
	public void testInvalidUser() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		GetFields_Params params;
		try {
			
			//with project specified
			params = new GetFields_Params();
			params.setUser("test");
			params.setPassword("test1");
			params.setProject(1);
			GetFields_Result result = communicator.getFields(params);
			
			assertEquals(result.isValidUser(), false);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@Test
	public void testInvalidPassword() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		GetFields_Params params;
		try {
			
			//with project specified
			params = new GetFields_Params();
			params.setUser("test1");
			params.setPassword("test");
			params.setProject(1);
			GetFields_Result result = communicator.getFields(params);
			
			assertEquals(result.isValidUser(), false);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@Test
	public void testEmptyTable() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		GetFields_Params params;
		try {
			
			db.getFieldsDAO().dropTable();
			//with project specified
			params = new GetFields_Params();
			params.setUser("test1");
			params.setPassword("test");
			params.setProject(1);
			GetFields_Result result = communicator.getFields(params);
			
			assertEquals(result.isValidUser(), false);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	

}
