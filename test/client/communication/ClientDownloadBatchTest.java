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

public class ClientDownloadBatchTest {

	
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
	public void testDownloadBatch() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		DownloadBatch_Params params;
		try {
			
			params = new DownloadBatch_Params();
			params.setUser("test1");
			params.setPassword("test1");
			params.setProject(1);
			DownloadBatch_Result result = communicator.downloadBatch(params);
			assertEquals(result.getBatch_id(), 1);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@Test
	public void testBatchAlreadyChecked() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		DownloadBatch_Params params;
		try {
			
			params = new DownloadBatch_Params();
			params.setUser("test1");
			params.setPassword("test1");
			params.setProject(1);
			DownloadBatch_Result result = communicator.downloadBatch(params);
			assertEquals(result.getBatch_id(), 1);
			
			params = new DownloadBatch_Params();
			params.setUser("test2");
			params.setPassword("test2");
			params.setProject(1);
			DownloadBatch_Result result2 = communicator.downloadBatch(params);
			assertEquals(result2.getBatch_id(), 2);
			
			
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@Test
	public void testAlreadyHasBatch() throws ClientException {
		
		ClientCommunicator communicator = new ClientCommunicator();
		
		DownloadBatch_Params params;
		try {
			
			params = new DownloadBatch_Params();
			params.setUser("test1");
			params.setPassword("test1");
			params.setProject(1);
			DownloadBatch_Result result = communicator.downloadBatch(params);
			assertEquals(result.getBatch_id(), 1);
			assertEquals(result.isValid(), true);
			
			params = new DownloadBatch_Params();
			params.setUser("test1");
			params.setPassword("test1");
			params.setProject(2);
			DownloadBatch_Result result2 = communicator.downloadBatch(params);
			assertEquals(result2.isValid(), false);
			
			
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	

}
