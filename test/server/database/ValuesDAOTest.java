package server.database;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.*;
import shared.model.*;


public class ValuesDAOTest {

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
	private ValuesDAO dbValues;

	@Before
	public void setUp() throws Exception {

		// Delete all values from the database	
		db = new Database();		
		db.startTransaction();
		
		
		
//		for (Value i : values) {
//			db.getValuesDAO().delete(i);
//		}
		db.getValuesDAO().dropTable();
		db.getValuesDAO().createTable();
	//	db.endTransaction(true);
//
//		// Prepare database for test case	
//		db = new Database();
//		db.startTransaction();
		List<Value> values = db.getValuesDAO().getAll();
		dbValues = db.getValuesDAO();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		dbValues = null;
	}

	@Test
	public void testGetAll() throws DatabaseException {
		
		List<Value> all = dbValues.getAll();
		assertEquals(0, all.size());
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		
		Value census = new Value("value", 2, 3, "url", 4, 5, 6);
		Value births = new Value("value1", 6, 7, "url1", 8, 9, 10);
		
		dbValues.add(census);
		dbValues.add(births);
		
		List<Value> all = dbValues.getAll();
		assertEquals(2, all.size());
		
		boolean foundAge = false;
		boolean foundPlace = false;
		
		for (Value v : all) {
			
			assertFalse(v.getId() == -1);
			
			if (!foundAge) {
				foundAge = areEqual(v, census, false);
			}		
			if (!foundPlace) {
				foundPlace = areEqual(v, births, false);
			}
		}
		
		assertTrue(foundAge && foundPlace);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Value census = new Value("value", 2, 3, "url", 4, 5, 6);
		Value births = new Value("value1", 6, 7, "url1", 8, 9, 10);
		
		dbValues.add(census);
		dbValues.add(births);
		
		census.setValue("value2");
		census.setRecord(6);
		census.setImage(7);
		census.setImageurl("url2");
		census.setColumnnum(8);
		census.setRecordnum(9);
		census.setProject(10);
		
		births.setValue("value3");
		births.setRecord(7);
		births.setImage(8);
		births.setImageurl("url2");
		births.setColumnnum(9);
		births.setRecordnum(10);
		births.setProject(11);
		
		dbValues.update(census);
		dbValues.update(births);
		
		List<Value> all = dbValues.getAll();
		assertEquals(2, all.size());
		
		boolean foundAge = false;
		boolean foundPlace = false;
		
		for (Value v : all) {
			
			if (!foundAge) {
				foundAge = areEqual(v, census, false);
			}		
			if (!foundPlace) {
				foundPlace = areEqual(v, births, false);
			}
		}
		
		assertTrue(foundAge && foundPlace);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		Value census = new Value("value", 2, 3, "url", 4, 5, 6);
		Value births = new Value("value1", 6, 7, "url1", 8, 9, 10);
		
		dbValues.add(census);
		dbValues.add(births);
		
		List<Value> all = dbValues.getAll();
		assertEquals(2, all.size());
		
		dbValues.delete(census);
		dbValues.delete(births);
		
		all = dbValues.getAll();
		assertEquals(0, all.size());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Value invalidValue = new Value(null, -1, -1, null, -1, -1, -1);
		dbValues.add(invalidValue);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Value invalidValue = new Value(null, -1, -1, null, -1, -1, -1);
		dbValues.update(invalidValue);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Value invalidValue = new Value(null, -1, -1, null, -1, -1, -1);
		dbValues.delete(invalidValue);
	}
	
	private boolean areEqual(Value a, Value b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getValue(), b.getValue()) &&
				safeEquals(a.getRecord(), b.getRecord()) &&
				safeEquals(a.getImage(), b.getImage()) &&
				safeEquals(a.getImageurl(), b.getImageurl()) &&
				safeEquals(a.getColumnnum(), b.getColumnnum()) &&
				safeEquals(a.getProject(), b.getProject()) &&
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
