package server.database;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.*;
import shared.model.*;


public class RecordsDAOTest {

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
	private RecordsDAO dbRecords;

	@Before
	public void setUp() throws Exception {

		// Delete all records from the database	
		db = new Database();		
		db.startTransaction();
		
		db.getRecordsDAO().dropTable();
		db.getRecordsDAO().createTable();
		List<Record> records = db.getRecordsDAO().getAll();
		
//		for (Record i : records) {
//			db.getRecordsDAO().delete(i);
//		}
		//db.getRecordsDAO().dropTable();
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		dbRecords = db.getRecordsDAO();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		dbRecords = null;
	}
	
	@Test
	public void testRecordNum() throws DatabaseException {
		Record census = new Record(2, 3);
		dbRecords.add(census);
		
		assertEquals(dbRecords.getRecordNum(census.getImage()), 3);
	}
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		List<Record> all = dbRecords.getAll();
		assertEquals(0, all.size());
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		
		Record census = new Record(2, 3);
		Record births = new Record(4, 5);
		
		dbRecords.add(census);
		dbRecords.add(births);
		
		List<Record> all = dbRecords.getAll();
		assertEquals(2, all.size());
		
		boolean foundAge = false;
		boolean foundPlace = false;
		
		for (Record r : all) {
			
			assertFalse(r.getId() == -1);
			
			if (!foundAge) {
				foundAge = areEqual(r, census, false);
			}		
			if (!foundPlace) {
				foundPlace = areEqual(r, births, false);
			}
		}
		
		assertTrue(foundAge && foundPlace);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Record census = new Record(2, 3);
		Record births = new Record(4, 5);
		
		dbRecords.add(census);
		dbRecords.add(births);
		
		census.setImage(3);
		census.setRecordnum(4);
		
		births.setImage(5);
		births.setRecordnum(6);
		
		dbRecords.update(census);
		dbRecords.update(births);
		
		List<Record> all = dbRecords.getAll();
		assertEquals(2, all.size());
		
		boolean foundAge = false;
		boolean foundPlace = false;
		
		for (Record r : all) {
			
			if (!foundAge) {
				foundAge = areEqual(r, census, false);
			}		
			if (!foundPlace) {
				foundPlace = areEqual(r, births, false);
			}
		}
		
		assertTrue(foundAge && foundPlace);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		Record census = new Record(2, 3);
		Record births = new Record(4, 5);
		
		dbRecords.add(census);
		dbRecords.add(births);
		
		List<Record> all = dbRecords.getAll();
		assertEquals(2, all.size());
		
		dbRecords.delete(census);
		dbRecords.delete(births);
		
		all = dbRecords.getAll();
		assertEquals(0, all.size());
	}
	
//	@Test(expected=DatabaseException.class)
//	public void testInvalidAdd() throws DatabaseException {
//		
//		Record invalidRecord = new Record(-1, -1);
//		dbRecords.add(invalidRecord);
//	}
//	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Record invalidRecord = new Record(-1, -1);
		dbRecords.update(invalidRecord);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Record invalidRecord = new Record(-1, -1);
		dbRecords.delete(invalidRecord);
	}
	
	private boolean areEqual(Record a, Record b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getImage(), b.getImage()) &&
				safeEquals(a.getRecordnum(), b.getRecordnum()));
	}
	
	private boolean safeEquals(Object a, Object b) {
		if (a == null || b == null) {
			return (a == null && b == null);
		}
		else {
			return a.equals(b);
		}
	}

}
