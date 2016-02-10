package server.database;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.*;
import shared.model.*;


public class UsersDAOTest {

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
	private UsersDAO dbUsers;

	@Before
	public void setUp() throws Exception {

		// Delete all users from the database	
		db = new Database();		
		db.startTransaction();
		
		db.getUsersDAO().dropTable();
		db.getUsersDAO().createTable();
		List<User> users = db.getUsersDAO().getAll();
		
//		for (User u : users) {
//			db.getUsersDAO().delete(u);
//		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		dbUsers = db.getUsersDAO();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		dbUsers = null;
	}
	
	@Test
	public void testValidateUser() throws DatabaseException {
		User megan = new User("name", "password", "first", "last", "email", 2, 3);
		dbUsers.add(megan);
		
		assertEquals(dbUsers.validateUser(megan.getName(), megan.getPassword()), true);
	}
	
	@Test
	public void testGetFirstName() throws DatabaseException {
		User megan = new User("name", "password", "first", "last", "email", 2, 3);
		dbUsers.add(megan);
		
		assertEquals(dbUsers.getFirstName(megan.getName(), megan.getPassword()), "first");
	}
	
	@Test
	public void testGetLastName() throws DatabaseException {
		User megan = new User("name", "password", "first", "last", "email", 2, 3);
		dbUsers.add(megan);
		
		assertEquals(dbUsers.getLastName(megan.getName(), megan.getPassword()), "last");
	}
	
	@Test
	public void testGetNumRecords() throws DatabaseException {
		User megan = new User("name", "password", "first", "last", "email", 2, 3);
		dbUsers.add(megan);
		
		assertEquals(dbUsers.getNumRecords(megan.getName(), megan.getPassword()), 2);
	}

	@Test
	public void testGetAll() throws DatabaseException {
		
		User megan = new User("name", "password", "first", "last", "email", 2, 3);
		User bob = new User("name2", "password2", "first", "last", "email", 4, 5);
		
		dbUsers.add(megan);
		dbUsers.add(bob);
		
		List<User> all = dbUsers.getAll();
		assertEquals(2, all.size());
		assertEquals("name", all.get(0).getName());
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		
		User megan = new User("name", "password", "first", "last", "email", 2, 3);
		User bob = new User("name2", "password2", "first2", "last2", "email2", 4, 5);
		
		dbUsers.add(megan);
		dbUsers.add(bob);
		
		List<User> all = dbUsers.getAll();
		assertEquals(2, all.size());
		
		boolean foundMegan = false;
		boolean foundBob = false;
		
		for (User u : all) {
			
			assertFalse(u.getId() == -1);
			
			if (!foundMegan) {
				foundMegan = areEqual(u, megan, false);
			}		
			if (!foundBob) {
				foundBob = areEqual(u, bob, false);
			}
		}
		
		assertTrue(foundMegan && foundBob);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		User megan = new User("name", "password", "first", "last", "email", 2, 3);
		User bob = new User("name2", "password2", "first", "last", "email", 4, 5);
		
		dbUsers.add(megan);
		dbUsers.add(bob);
		
		megan.setName("name2");
		megan.setPassword("megan3.jpeg");
		megan.setFirstname("megan3.jpeg");
		megan.setLastname("megan3.jpeg");
		megan.setEmail("megan3.jpeg");
		megan.setIndexedrecords(5);
		megan.setCurrentbatch(6);
		
		bob.setName("name3");
		bob.setPassword("megan5.jpeg");
		bob.setFirstname("mega63.jpeg");
		bob.setLastname("megan43.jpeg");
		bob.setEmail("megan43.jpeg");
		bob.setIndexedrecords(7);
		bob.setCurrentbatch(8);
		
		dbUsers.update(megan);
		dbUsers.update(bob);
		
		List<User> all = dbUsers.getAll();
		assertEquals(2, all.size());
		
		boolean foundAge = false;
		boolean foundPlace = false;
		
		for (User u : all) {
			
			if (!foundAge) {
				foundAge = areEqual(u, megan, false);
			}		
			if (!foundPlace) {
				foundPlace = areEqual(u, bob, false);
			}
		}
		
		assertTrue(foundAge && foundPlace);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		User megan = new User("name", "password", "first", "last", "email", 2, 3);
		User bob = new User("name2", "password2", "first1", "last1", "email1", 4, 5);
		
		dbUsers.add(megan);
		dbUsers.add(bob);
		
		List<User> all = dbUsers.getAll();
		assertEquals(2, all.size());
		
		dbUsers.delete(megan);
		dbUsers.delete(bob);
		
		all = dbUsers.getAll();
		assertEquals(0, all.size());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		User invalidUser = new User(null, null, null, null, null, -1, -1);
		dbUsers.add(invalidUser);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		User invalidUser = new User(null, null, null, null, null, -1, -1);
		dbUsers.update(invalidUser);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		User invalidUser = new User(null, null, null, null, null, -1, -1);
		dbUsers.delete(invalidUser);
	}
	
	private boolean areEqual(User a, User b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getName(), b.getName()) &&
				safeEquals(a.getPassword(), b.getPassword()) &&
				safeEquals(a.getFirstname(), b.getFirstname()) &&
				safeEquals(a.getLastname(), b.getLastname()) &&
				safeEquals(a.getEmail(), b.getEmail()) &&
				safeEquals(a.getIndexedrecords(), b.getIndexedrecords()) &&
				safeEquals(a.getCurrentbatch(), b.getCurrentbatch()));
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
