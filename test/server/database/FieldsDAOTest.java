package server.database;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.*;
import shared.model.*;


public class FieldsDAOTest {

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
	private FieldsDAO dbFields;
	private ProjectsDAO dbProjects;

	@Before
	public void setUp() throws Exception {

		// Delete all fields from the database	
		db = new Database();		
		db.startTransaction();
		
		db.getFieldsDAO().dropTable();
		db.getFieldsDAO().createTable();
		
		List<Field> fields = db.getFieldsDAO().getAll();
		
//		for (Field f : fields) {
//			db.getFieldsDAO().delete(f);
//		}
//		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		dbFields = db.getFieldsDAO();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		dbFields = null;
	}
	
	@Test
	public void testGetColumnNum() throws DatabaseException {
		Field age = new Field("age", 2, 3, "help.html", "knowndata.url", 4, 5);
		dbFields.add(age);
		
		assertEquals(dbFields.getColumnNum(age.getId()), 5);
	}
	
	@Test
	public void testGetProjectId() throws DatabaseException {
		Field age = new Field("age", 2, 3, "help.html", "knowndata.url", 4, 5);
		dbFields.add(age);
		
		assertEquals(dbFields.getProjectId(age.getId()), 4);
	}
	
	@Test
	public void testGetFieldTitle() throws DatabaseException {
		Field age = new Field("age", 2, 3, "help.html", "knowndata.url", 4, 5);
		dbFields.add(age);
		
		assertEquals(dbFields.getFieldTitle(age.getId()), "age");
	}

	@Test
	public void testGetHelpUrl() throws DatabaseException {
		Field age = new Field("age", 2, 3, "help.html", "knowndata.url", 4, 5);
		dbFields.add(age);
		
		assertEquals(dbFields.getHelpUrl(age.getProject()), "help.html");
	}

	@Test
	public void testGetXCoord() throws DatabaseException {
		Field age = new Field("age", 2, 3, "help.html", "knowndata.url", 4, 5);
		dbFields.add(age);
		
		assertEquals(dbFields.getXCoord(age.getProject()), 2);
	}

	@Test
	public void testGetWidth() throws DatabaseException {
		Field age = new Field("age", 2, 3, "help.html", "knowndata.url", 4, 5);
		dbFields.add(age);
		
		assertEquals(dbFields.getPixelWidth(age.getProject()), 3);
	}
	
	@Test
	public void getAllInProj() throws DatabaseException {
		
		dbProjects = db.getProjectsDAO();
				
		Project census = new Project("census", 2, 3, 4, 5);
		dbProjects.add(census);

		Field age = new Field("age", 2, 3, "help.html", "knowndata.url", census.getId(), 5);
		dbFields.add(age);
				
		List<Field> all = dbFields.getAllInProj(census.getId());
		assertEquals(1, all.size());
	}
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		List<Field> all = dbFields.getAll();
		assertEquals(0, all.size());
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		
		Field age = new Field("age", 2, 3, "help.html", "knowndata.url", 4, 5);
		Field place = new Field("place", 7, 8, "help2.html", "knowndata2.url", 9, 10);
		
		dbFields.add(age);
		dbFields.add(place);
		
		List<Field> all = dbFields.getAll();
		assertEquals(2, all.size());
		
		boolean foundAge = false;
		boolean foundPlace = false;
		
		for (Field f : all) {
			
			assertFalse(f.getId() == -1);
			
			if (!foundAge) {
				foundAge = areEqual(f, age, false);
			}		
			if (!foundPlace) {
				foundPlace = areEqual(f, place, false);
			}
		}
		
		assertTrue(foundAge && foundPlace);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Field age = new Field("age", 2, 3, "help.html", "knowndata.url", 4, 5);
		Field place = new Field("place", 7, 8, "help2.html", "knowndata2.url", 9, 10);
		
		dbFields.add(age);
		dbFields.add(place);
		
		age.setTitle("AGE");
		age.setXcoord(3);
		age.setWidth(4);
		age.setHelphtml("help2.html");
		age.setKnowndata("knowndata2.url");
		age.setProject(5);
		age.setColumnnum(6);
		
		place.setTitle("PLACE");
		place.setXcoord(8);
		place.setWidth(9);
		place.setHelphtml("help2.html");
		place.setKnowndata("knowndata2.url");
		place.setProject(10);
		place.setColumnnum(11);
		
		dbFields.update(age);
		dbFields.update(place);
		
		List<Field> all = dbFields.getAll();
		assertEquals(2, all.size());
		
		boolean foundAge = false;
		boolean foundPlace = false;
		
		for (Field f : all) {
			
			if (!foundAge) {
				foundAge = areEqual(f, age, false);
			}		
			if (!foundPlace) {
				foundPlace = areEqual(f, place, false);
			}
		}
		
		assertTrue(foundAge && foundPlace);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		Field age = new Field("age", 2, 3, "help.html", "knowndata.url", 4, 5);
		Field place = new Field("place", 7, 8, "help2.html", "knowndata2.url", 9, 10);
		
		dbFields.add(age);
		dbFields.add(place);
		
		List<Field> all = dbFields.getAll();
		assertEquals(2, all.size());
		
		dbFields.delete(age);
		dbFields.delete(place);
		
		all = dbFields.getAll();
		assertEquals(0, all.size());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Field invalidField = new Field(null, -1, -1, null, null, -1, -1);
		dbFields.add(invalidField);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Field invalidField = new Field(null, -1, -1, null, null, -1, -1);
		dbFields.update(invalidField);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Field invalidField = new Field(null, -1, -1, null, null, -1, -1);
		dbFields.delete(invalidField);
	}
	
	private boolean areEqual(Field a, Field b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getTitle(), b.getTitle()) &&
				safeEquals(a.getXcoord(), b.getXcoord()) &&
				safeEquals(a.getWidth(), b.getWidth()) &&
				safeEquals(a.getHelphtml(), b.getHelphtml()) &&
				safeEquals(a.getKnowndata(), b.getKnowndata()) &&
				safeEquals(a.getProject(), b.getProject()) &&
				safeEquals(a.getColumnnum(), b.getColumnnum()));
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
