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

public class ClientGetSampleImageTest {

	
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
	public void testInvalidUser() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		GetSampleImage_Params params;
		try {
			
			params = new GetSampleImage_Params();
			params.setUser("test");
			params.setPassword("test1");
			params.setProject_id(1);
			GetSampleImage_Result result = communicator.getSampleImage(params);
			
			assertEquals(result.isValidUser(), false);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@Test
	public void testInvalidPassword() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		GetSampleImage_Params params;
		try {
			
			params = new GetSampleImage_Params();
			params.setUser("test1");
			params.setPassword("test");
			params.setProject_id(1);
			GetSampleImage_Result result = communicator.getSampleImage(params);
			
			assertEquals(result.isValidUser(), false);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
//	@Test
//	public void testEmptyTable() throws ClientException {
//		
//		ClientCommunicator communicator = new ClientCommunicator();
//		
//		GetSampleImage_Params params;
//		try {
//			
//			db.getImagesDAO().dropTable();
//			params = new GetSampleImage_Params();
//			params.setUser("test1");
//			params.setPassword("test1");
//			params.setProject_id(1);
//			GetSampleImage_Result result = communicator.getSampleImage(params);
//			
//			assertEquals(result.getImage_url(), null);
//			
//		} catch (DatabaseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//			
//	}
	

}
