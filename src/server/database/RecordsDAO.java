package server.database;

import java.util.*;
import java.sql.*;
import java.util.logging.*;
import shared.model.Record;

/**
 * @author meganarnell
 *
 */
public class RecordsDAO {

	
	private static Logger logger;
	
	static {
		logger = Logger.getLogger("recordindexer");
	}

	private Database db;
	
	
	/**Creates new RecordsDAO
	 * @param db	Database
	 */
	RecordsDAO(Database db) {
		this.db = db;
	}
	
	public void dropTable() throws DatabaseException {
		logger.entering("server.database.Fields", "getColumnNum");
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String query = "drop table if exists record";
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
	
	public void createTable() throws DatabaseException {
		logger.entering("server.database.Fields", "getColumnNum");
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String query = "create table record(id integer not null primary key autoincrement,image integer not null,recordnum integer not null)";
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
	
	/**Retrieves the record number in the image (row number)
	 * @param image					Image id
	 * @return						The record number in the image (row number)
	 * @throws DatabaseException	throws Database Exception if operations fail for any reason
	 */
	public int getRecordNum(int image) throws DatabaseException {
		logger.entering("server.database.Records", "getRecordNum");
		
		int result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select recordnum from record where image = " + image;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getInt(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Images", "getRecordNum", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Images", "getRecordNum");
		
		return result;	
			
	}
	
	/**Returns list of all Records in database
	 * @return							List of all Records in database
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public List<Record> getAll() throws DatabaseException {

		logger.entering("server.database.Records", "getAll");
		
		ArrayList<Record> result = new ArrayList<Record>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select id, image, recordnum from record";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int image = rs.getInt(2);
				int recordnum = rs.getInt(3);

				Record record = new Record(image, recordnum);
				record.setId(id);
				result.add(record);
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Records", "getAll", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Records", "getAll");
		
		return result;	
			

	}
	
	/**Adds new Record to database
	 * @param record					Record object to be added
	 * @param image						Image id to which record belongs
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void add(Record record) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into record (image, recordnum) values (?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, record.getImage());
			stmt.setInt(2, record.getRecordnum());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				record.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert record");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert record", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	/**Updates information about Record in database
	 * @param record					Record object to be updated
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void update(Record record) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update record set image = ?, recordnum = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, record.getImage());
			stmt.setInt(2, record.getRecordnum());
			stmt.setInt(3, record.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update record");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update record", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	/**Deletes Record from database
	 * @param record					Record object to be deleted
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void delete(Record record) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from record where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, record.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete record");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete record", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

}