package server.database;

import java.util.*;
import java.sql.*;
import java.util.logging.*;

import shared.model.Image;
import shared.model.User;



/**
 * @author meganarnell
 *
 */
public class UsersDAO {

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("recordindexer");
	}

	private Database db;
	
	/**Creates new UsersDAO
	 * @param db	Database
	 */
	UsersDAO(Database db) {
		this.db = db;
	}
	
	public void dropTable() throws DatabaseException {
		logger.entering("server.database.Fields", "getColumnNum");
		
		Statement stmt = null;
		try {
			String query = "DROP TABLE IF EXISTS USER ";
			stmt = db.getConnection().createStatement();
			stmt.executeUpdate(query);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Fields", "getColumnNum", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Fields", "getColumnNum");
			
	}
	
	public void createTable() throws DatabaseException {
		logger.entering("server.database.Fields", "getColumnNum");
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String query = "create table user(id integer not null primary key autoincrement,name text not null,password text not null,firstname text not null,lastname text not null,email text not null,indexedrecords integer not null check (indexedrecords>=0),currentbatch integer not null)";
			stmt = db.getConnection().createStatement();
			stmt.executeUpdate(query);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Fields", "getColumnNum", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Fields", "getColumnNum");
			
	}
	
	/**Checks to see that the user name and password combination is found in the database.
	 * @param name			User name
	 * @param password		User password
	 * @return				true if user name and password combination is found
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 *
	 */
	public boolean validateUser(String name, String password) throws DatabaseException {
		logger.entering("server.database.Users", "validateUser");
		
		String result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from user where name = '" + name + "' and password = '" + password + "'";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			if(rs.next())
				result = rs.getString(1);
			else
				result = "";
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Users", "validateUser", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Users", "validateUser");
		
		if(result.length()>0)
			return true;
		return false;
			
	}
	
	public int getCurrentBatch(String name, String password) throws DatabaseException {
		logger.entering("server.database.Users", "getFirstName");
		
		int result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select currentbatch from user where name = '" + name + "' and password = '" + password + "'";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getInt(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Users", "getFirstName", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Users", "getFirstName");
		
		return result;	
			
	}
	
	public boolean batchChecked(int batch) throws DatabaseException {
		logger.entering("server.database.Users", "getFirstName");
		
		boolean result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select id from user where currentbatch = " + batch;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			if(rs.next())
				result = true;
			else
				result = false;
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Users", "getFirstName", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Users", "getFirstName");
		
		return result;	
			
	}
	
	/**Returns first name of user with given name and password
	 * @return							First name of user
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public String getFirstName(String name, String password) throws DatabaseException {
		logger.entering("server.database.Users", "getFirstName");
		
		String result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select firstname from user where name = '" + name + "' and password = '" + password + "'";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getString(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Users", "getFirstName", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Users", "getFirstName");
		
		return result;	
			
	}
	
	/**Returns last name of user with given name and password
	 * @return							Last name of user
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public String getLastName(String name, String password) throws DatabaseException {
		logger.entering("server.database.Users", "getLastName");
		
		String result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select lastname from user where name = '" + name + "' and password = '" + password + "'";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getString(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Users", "getLastName", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Users", "getLastName");
		
		return result;	
			
	}
	
	/**Returns the number of records indexed by user with given name and password
	 * @return							Number of records indexed by user
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public int getNumRecords(String name, String password) throws DatabaseException {
		logger.entering("server.database.Users", "getNumRecords");
		
		int result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select indexedrecords from user where name = '" + name + "' and password = '" + password + "'";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getInt(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Users", "getNumRecords", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Users", "getNumRecords");
		
		return result;	
			
	}
	
	/**Returns list of all Users in database
	 * @return							List of all Users in database
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public List<User> getAll() throws DatabaseException {

		logger.entering("server.database.Users", "getAll");
		
		ArrayList<User> result = new ArrayList<User>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select id, name, password, firstname, lastname, email, indexedrecords, currentbatch from user";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String firstname = rs.getString(4);
				String lastname = rs.getString(5);
				String email = rs.getString(6);
				int indexedrecords = rs.getInt(7);
				int currentbatch = rs.getInt(8);

				User user = new User(name, password, firstname, lastname, email, indexedrecords, currentbatch);
				user.setId(id);
				result.add(user);
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Users", "getAll", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Users", "getAll");
		
		return result;	
			

	}
	
	/**Adds new User to database
	 * @param user						User object to be added
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void add(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into user (name, password, firstname, lastname, email, indexedrecords, currentbatch) values (?, ?, ?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstname());
			stmt.setString(4, user.getLastname());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getIndexedrecords());
			stmt.setInt(7, user.getCurrentbatch());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				user.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert user", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}	
	}
	
	/**Updates information about User in database
	 * @param user						User object to be updated
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void update(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update user set name = ?, password = ?, firstname = ?, lastname = ?, email = ?, indexedrecords = ?, currentbatch = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstname());
			stmt.setString(4, user.getLastname());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getIndexedrecords());
			stmt.setInt(7, user.getCurrentbatch());
			stmt.setInt(8, user.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update user", e);
		}
		finally {
			Database.safeClose(stmt);
		}	
	}
	
	public void updateCurrentBatch(String name, String password, int newbatch) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update user set currentbatch = ? where name = ? and password = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, newbatch);
			stmt.setString(2, name);
			stmt.setString(3, password);
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update user", e);
		}
		finally {
			Database.safeClose(stmt);
		}	
	}
	
	public void updateIndexedRecords(String name, String password, int records) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			int indexedrecords = this.getNumRecords(name, password) + records;
			String query = "update user set indexedrecords = ? where name = ? and password = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, indexedrecords);
			stmt.setString(2, name);
			stmt.setString(3, password);
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update user", e);
		}
		finally {
			Database.safeClose(stmt);
		}	
	}
	
	/**Deletes User from database
	 * @param user						User object to be deleted
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void delete(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from user where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, user.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete user", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

}