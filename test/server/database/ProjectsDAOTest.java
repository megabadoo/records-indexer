package server.database;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.*;
import shared.model.*;


public class ProjectsDAOTest {

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
	private ProjectsDAO dbProjects;

	@Before
	public void setUp() throws Exception {

		// Delete all projects from the database	
		db = new Database();		
		db.startTransaction();
		
		db.getProjectsDAO().dropTable();
		db.getProjectsDAO().createTable();
		List<Project> projects = db.getProjectsDAO().getAll();
		
//		for (Project p : projects) {
//			db.getProjectsDAO().delete(p);
//		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		dbProjects = db.getProjectsDAO();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		dbProjects = null;
	}
	
	@Test
	public void testGetFirstYCoord() throws DatabaseException {
		Project census = new Project("census", 2, 3, 4, 5);
		dbProjects.add(census);
		
		assertEquals(dbProjects.getFirstYCoord(census.getId()), 3);
	}
	
	@Test
	public void testGetRecordHeight() throws DatabaseException {
		Project census = new Project("census", 2, 3, 4, 5);
		dbProjects.add(census);
		
		assertEquals(dbProjects.getRecordHeight(census.getId()), 4);
	}
	
	@Test
	public void testGetRecsPerImage() throws DatabaseException {
		Project census = new Project("census", 2, 3, 4, 5);
		dbProjects.add(census);
		
		assertEquals(dbProjects.getRecsPerImage(census.getId()), 2);
	}
	
	@Test
	public void testGetNumFields() throws DatabaseException {
		Project census = new Project("census", 2, 3, 4, 5);
		dbProjects.add(census);
		
		assertEquals(dbProjects.getNumFields(census.getId()), 5);
	}
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		List<Project> all = dbProjects.getAll();
		assertEquals(0, all.size());
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		
		Project census = new Project("census", 2, 3, 4, 5);
		Project births = new Project("births", 6, 7, 8, 9);
		
		dbProjects.add(census);
		dbProjects.add(births);
		
		List<Project> all = dbProjects.getAll();
		assertEquals(2, all.size());
		
		boolean foundAge = false;
		boolean foundPlace = false;
		
		for (Project p : all) {
			
			assertFalse(p.getId() == -1);
			
			if (!foundAge) {
				foundAge = areEqual(p, census, false);
			}		
			if (!foundPlace) {
				foundPlace = areEqual(p, births, false);
			}
		}
		
		assertTrue(foundAge && foundPlace);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Project census = new Project("census", 2, 3, 4, 5);
		Project births = new Project("births", 6, 7, 8, 9);
		
		dbProjects.add(census);
		dbProjects.add(births);
		
		census.setTitle("CENSUS");
		census.setRecordsperimage(3);
		census.setFirstycoord(4);
		census.setRecordheight(5);
		census.setNumfields(6);
		
		births.setTitle("BIRTHS");
		births.setRecordsperimage(7);
		births.setFirstycoord(8);
		births.setRecordheight(9);
		births.setNumfields(10);
		
		dbProjects.update(census);
		dbProjects.update(births);
		
		List<Project> all = dbProjects.getAll();
		assertEquals(2, all.size());
		
		boolean foundAge = false;
		boolean foundPlace = false;
		
		for (Project p : all) {
			
			if (!foundAge) {
				foundAge = areEqual(p, census, false);
			}		
			if (!foundPlace) {
				foundPlace = areEqual(p, births, false);
			}
		}
		
		assertTrue(foundAge && foundPlace);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		Project census = new Project("census", 2, 3, 4, 5);
		Project births = new Project("births", 6, 7, 8, 9);
		
		dbProjects.add(census);
		dbProjects.add(births);
		
		List<Project> all = dbProjects.getAll();
		assertEquals(2, all.size());
		
		dbProjects.delete(census);
		dbProjects.delete(births);
		
		all = dbProjects.getAll();
		assertEquals(0, all.size());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Project invalidProject = new Project(null, -1, -1, -1, -1);
		dbProjects.add(invalidProject);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Project invalidProject = new Project(null, -1, -1, -1, -1);
		dbProjects.update(invalidProject);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Project invalidProject = new Project(null, -1, -1, -1, -1);
		dbProjects.delete(invalidProject);
	}
	
	private boolean areEqual(Project a, Project b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getTitle(), b.getTitle()) &&
				safeEquals(a.getRecordsperimage(), b.getRecordsperimage()) &&
				safeEquals(a.getFirstycoord(), b.getFirstycoord()) &&
				safeEquals(a.getRecordheight(), b.getRecordheight()) &&
				safeEquals(a.getNumfields(), b.getNumfields()));
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
