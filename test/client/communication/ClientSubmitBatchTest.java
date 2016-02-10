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

public class ClientSubmitBatchTest {

	
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
	public void testSubmitBatch() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		SubmitBatch_Params params;
		try {
			
			//download batch
			DownloadBatch_Params params1 = new DownloadBatch_Params();
			params1.setUser("test1");
			params1.setPassword("test1");
			params1.setProject(3);
			DownloadBatch_Result result1 = communicator.downloadBatch(params1);
			assertEquals(result1.getBatch_id(), 41);
			
			//with project specified
			params = new SubmitBatch_Params();
			params.setUser("test1");
			params.setPassword("test1");
			params.setBatch(41);
			List<List<String>> field_values = new ArrayList<List<String>>();
			List<String> record1 = new ArrayList<String>();
			record1.add("ARNELL");
			record1.add("MEGAN");
			record1.add("23");
			record1.add("WHITE");
			field_values.add(record1);
			List<String> record2 = new ArrayList<String>();
			record2.add("HAMILTON");
			record2.add("LISA");
			record2.add("23");
			record2.add("WHITE");
			field_values.add(record2);
			params.setField_values(field_values);
			SubmitBatch_Result result = communicator.submitBatch(params);		
			
			assertEquals(result.isSucceeded(), true);
			
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

}
