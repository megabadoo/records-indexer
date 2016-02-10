package server.database;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.*;
import shared.model.*;


public class ImagesDAOTest {

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
	private ImagesDAO dbImages;

	@Before
	public void setUp() throws Exception {

		// Delete all images from the database	
		db = new Database();		
		db.startTransaction();
		
		
		db.getImagesDAO().dropTable();
		db.getImagesDAO().createTable();
		List<Image> images = db.getImagesDAO().getAll();
		
//		for (Image i : images) {
//			db.getImagesDAO().delete(i);
//		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		dbImages = db.getImagesDAO();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		dbImages = null;
	}
	
	@Test
	public void testGetSampleImage() throws DatabaseException {
		Image census = new Image(2, "census.jpeg");
		dbImages.add(census);
		
		assertEquals(dbImages.getSampleImage(census.getProject()), "census.jpeg");
	}
	
	@Test
	public void testGetBatchId() throws DatabaseException {
		Image census = new Image(2, "census.jpeg");
		dbImages.add(census);
		
		assertEquals(dbImages.getBatchId(census.getProject()), census.getId());
	}
	
	@Test
	public void testGetImageUrl() throws DatabaseException {
		Image census = new Image(2, "census.jpeg");
		dbImages.add(census);
		
		assertEquals(dbImages.getImageUrl(census.getId()), "census.jpeg");
	}

	@Test
	public void testGetAll() throws DatabaseException {
		
		List<Image> all = dbImages.getAll();
		assertEquals(0, all.size());
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		
		Image census = new Image(2, "census.jpeg");
		Image births = new Image(3, "census2.jpeg");
		
		dbImages.add(census);
		dbImages.add(births);
		
		List<Image> all = dbImages.getAll();
		assertEquals(2, all.size());
		
		boolean foundAge = false;
		boolean foundPlace = false;
		
		for (Image i : all) {
			
			assertFalse(i.getId() == -1);
			
			if (!foundAge) {
				foundAge = areEqual(i, census, false);
			}		
			if (!foundPlace) {
				foundPlace = areEqual(i, births, false);
			}
		}
		
		assertTrue(foundAge && foundPlace);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Image census = new Image(2, "census.jpeg");
		Image births = new Image(3, "census2.jpeg");
		
		dbImages.add(census);
		dbImages.add(births);
		
		census.setProject(3);
		census.setFile("census3.jpeg");
		
		births.setProject(4);
		births.setFile("census4.jpeg");
		
		dbImages.update(census);
		dbImages.update(births);
		
		List<Image> all = dbImages.getAll();
		assertEquals(2, all.size());
		
		boolean foundAge = false;
		boolean foundPlace = false;
		
		for (Image i : all) {
			
			if (!foundAge) {
				foundAge = areEqual(i, census, false);
			}		
			if (!foundPlace) {
				foundPlace = areEqual(i, births, false);
			}
		}
		
		assertTrue(foundAge && foundPlace);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		Image census = new Image(2, "census.jpeg");
		Image births = new Image(3, "census2.jpeg");
		
		dbImages.add(census);
		dbImages.add(births);
		
		List<Image> all = dbImages.getAll();
		assertEquals(2, all.size());
		
		dbImages.delete(census);
		dbImages.delete(births);
		
		all = dbImages.getAll();
		assertEquals(0, all.size());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Image invalidImage = new Image(-1, null);
		dbImages.add(invalidImage);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Image invalidImage = new Image(-1, null);
		dbImages.update(invalidImage);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Image invalidImage = new Image(-1, null);
		dbImages.delete(invalidImage);
	}
	
	private boolean areEqual(Image a, Image b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getProject(), b.getProject()) &&
				safeEquals(a.getFile(), b.getFile()));
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
