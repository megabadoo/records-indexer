package server.database;

import java.util.*;
import java.sql.*;
import java.util.logging.*;

import shared.model.User;
import shared.model.Value;

/**
 * @author meganarnell
 *
 */
public class ValuesDAO {

	private static Logger logger;

	static {
		logger = Logger.getLogger("recordindexer");
	}

	private Database db;

	/**
	 * Creates new ValuesDAO
	 * 
	 * @param db
	 *            Database
	 */
	ValuesDAO(Database db) {
		this.db = db;
	}

	public void dropTable() throws DatabaseException {
		logger.entering("server.database.Fields", "getColumnNum");
		
		Statement stmt = null;
		try {
			String query = "DROP TABLE IF EXISTS VALUE ";
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
			String query = "create table value(id integer not null primary key autoincrement,record integer not null,columnnum integer not null,imageid integer not null,imageurl text not null,recordnum integer not null,value text, project integer not null)";
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
	
	/**
	 * Returns list of all Values in database corresponding to the given name
	 * and password
	 * 
	 * @return List of all Values in database for a given user
	 * @throws DatabaseException
	 *             throws Database Exception if operations fail for any reason
	 */
	public List<Value> getAll(int project, int columnnum) throws DatabaseException {

		logger.entering("server.database.Values", "getAll");

		ArrayList<Value> result = new ArrayList<Value>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select id, value, record, imageid, imageurl, recordnum from value where project = " + project + " and columnnum = " + columnnum;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String value = rs.getString(2);
				int record = rs.getInt(3);
				int image = rs.getInt(4);
				String imageurl = rs.getString(5);
				int recordnum = rs.getInt(6);

				Value v = new Value(value, record, image, imageurl, columnnum, recordnum, project);
				v.setId(id);
				result.add(v);
			}
		} catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);

			logger.throwing("server.database.Values", "getAll", serverEx);

			throw serverEx;
		} finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Values", "getAll");

		return result;

	}
	
	public List<Value> getAll() throws DatabaseException {

		logger.entering("server.database.Values", "getAll");

		ArrayList<Value> result = new ArrayList<Value>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select id, value, record, imageid, imageurl, recordnum, project, columnnum from value";
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			while (rs!=null && rs.next()) {
				int id = rs.getInt(1);
				String value = rs.getString(2);
				int record = rs.getInt(3);
				int image = rs.getInt(4);
				String imageurl = rs.getString(5);
				int recordnum = rs.getInt(6);
				int project = rs.getInt(7);
				int columnnum = rs.getInt(8);

				Value v = new Value(value, record, image, imageurl, columnnum, recordnum, project);
				v.setId(id);
				result.add(v);
			}
		} catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);

			logger.throwing("server.database.Values", "getAll", serverEx);

			throw serverEx;
		} finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Values", "getAll");

		return result;

	}


	/**
	 * Adds new Value to a particular record in the database
	 * 
	 * @param value
	 *            Value object to be added
	 * @param record
	 *            Record id to which value is added
	 * @throws DatabaseException
	 *             throws Database Exception if operations fail for any reason
	 */
	public void add(Value value) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "insert into value (value, record, imageid, imageurl, columnnum, recordnum, project) values (?, ?, ?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, value.getValue());
			stmt.setInt(2, value.getRecord());
			stmt.setInt(3, value.getImage());
			stmt.setString(4, value.getImageurl());
			stmt.setInt(5, value.getColumnnum());
			stmt.setInt(6, value.getRecordnum());
			stmt.setInt(7, value.getProject());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				value.setId(id);
			} else {
				throw new DatabaseException("Could not insert value");
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not insert value", e);
		} finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}

	/**
	 * Updates information for Value in database
	 * 
	 * @param value
	 *            Value object to be updated
	 * @throws DatabaseException
	 *             throws Database Exception if operations fail for any reason
	 */
	public void update(Value value) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update value set value = ?, record = ?, imageid = ?, imageurl = ?, columnnum = ?, recordnum = ?, project = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, value.getValue());
			stmt.setInt(2, value.getRecord());
			stmt.setInt(3, value.getImage());
			stmt.setString(4, value.getImageurl());
			stmt.setInt(5, value.getColumnnum());
			stmt.setInt(6, value.getRecordnum());
			stmt.setInt(7, value.getProject());
			stmt.setInt(8, value.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update value");
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not update value", e);
		} finally {
			Database.safeClose(stmt);
		}
	}

	/**
	 * Deletes Value from database
	 * 
	 * @param value
	 *            Value object to be deleted
	 * @throws DatabaseException
	 *             throws Database Exception if operations fail for any reason
	 */
	public void delete(Value value) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from value where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, value.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete value");
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not delete value", e);
		} finally {
			Database.safeClose(stmt);
		}
	}

}