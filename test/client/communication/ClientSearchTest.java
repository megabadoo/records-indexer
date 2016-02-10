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

public class ClientSearchTest {

	
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
	public void testSearch() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		Search_Params params;
		try {
			
			//with project specified
			params = new Search_Params();
			params.setUser("test1");
			params.setPassword("test1");
			List<Integer> search_fields = new ArrayList<Integer>();
			search_fields.add(10);
			search_fields.add(11);
			params.setFields(search_fields);
			List<String> search_values = new ArrayList<String>();
			search_values.add("BARTLETT");
			search_values.add("FOX");
			search_values.add("Bartlett");
			params.setSearch_values(search_values);
			List<Search_Result> result = communicator.search(params);		
			
			assertEquals(result.size(), 2);
			assertEquals(result.get(0).getField_id(), 10);
			assertEquals(result.get(1).getField_id(), 10);
			
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@Test
	public void testDuplicates() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		Search_Params params;
		try {
			
			//with project specified
			params = new Search_Params();
			params.setUser("test1");
			params.setPassword("test1");
			List<Integer> search_fields = new ArrayList<Integer>();
			search_fields.add(10);
			search_fields.add(11);
			params.setFields(search_fields);
			List<String> search_values = new ArrayList<String>();
			search_values.add("BARTLETT");
			search_values.add("BARTLETT");
			search_values.add("FOX");
			search_values.add("Bartlett");
			params.setSearch_values(search_values);
			List<Search_Result> result = communicator.search(params);		
			
			assertEquals(result.size(), 2);
			assertEquals(result.get(0).getField_id(), 10);
			assertEquals(result.get(1).getField_id(), 10);
			
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	
	@Test
	public void testInvalidUser() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		Search_Params params;
		try {
			
			//with project specified
			params = new Search_Params();
			params.setUser("test");
			params.setPassword("test1");
			List<Integer> search_fields = new ArrayList<Integer>();
			search_fields.add(10);
			search_fields.add(11);
			params.setFields(search_fields);
			List<String> search_values = new ArrayList<String>();
			search_values.add("BARTLETT");
			search_values.add("FOX");
			search_values.add("Bartlett");
			params.setSearch_values(search_values);
			List<Search_Result> result = communicator.search(params);		
			
			assertEquals(result.get(1).isValid(), false);
			
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	@Test
	public void testInvalidPassword() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		Search_Params params;
		try {
			
			//with project specified
			params = new Search_Params();
			params.setUser("test1");
			params.setPassword("test");
			List<Integer> search_fields = new ArrayList<Integer>();
			search_fields.add(10);
			search_fields.add(11);
			params.setFields(search_fields);
			List<String> search_values = new ArrayList<String>();
			search_values.add("BARTLETT");
			search_values.add("FOX");
			search_values.add("Bartlett");
			params.setSearch_values(search_values);
			List<Search_Result> result = communicator.search(params);		
			
			assertEquals(result.get(1).isValid(), false);
			
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	
}
