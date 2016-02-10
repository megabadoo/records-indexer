package server.database;

import java.util.*;
import java.sql.*;
import java.util.logging.*;
import shared.model.Field;



/**
 * @author meganarnell
 *
 */
public class FieldsDAO {
	
	private static Logger logger;
	
	static {
		logger = Logger.getLogger("recordindexer");
	}

	private Database db;
	

	/**Creates new FieldsDAO
	 * @param db		Database
	 */
	FieldsDAO(Database db) {
		this.db = db;
	}
	
	public void dropTable() throws DatabaseException {
		logger.entering("server.database.Fields", "getColumnNum");
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String query = "drop table if exists field";
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
			String query = "create table field (id integer not null primary key autoincrement,title text not null,xcoord integer not null check (xcoord>=0),width integer not null check (width>=0),helphtml text not null,columnnum integer not null,knowndata text,project integer not null)";
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
	
	/**Retrieves column number of a given field
	 * @param field					Field id
	 * @return						Column number of a given field
	 * @throws DatabaseException	throws Database Exception if operations fail for any reason
	 */
	public int getColumnNum(int field) throws DatabaseException {
		logger.entering("server.database.Fields", "getColumnNum");
		
		int result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select columnnum from field where id = " + field;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getInt(1);
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
		
		return result;	
			
	}
	
	/**Retrieves project id to which a given field corresponds
	 * @param field					Field id
	 * @return						Project id to which a given field corresponds
	 * @throws DatabaseException	throws Database Exception if operations fail for any reason
	 */
	public int getProjectId(int field) throws DatabaseException {
		logger.entering("server.database.Fields", "getProjectId");
		
		int result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select project from field where id = " + field;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getInt(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Fields", "getProjectId", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Fields", "getProjectId");
		
		return result;	
			
	}
	
	/**Retrieves given field's title
	 * @param field					Field id
	 * @return						Field title
	 * @throws DatabaseException	throws Database Exception if operations fail for any reason
	 */
	public String getFieldTitle(int field) throws DatabaseException {
		logger.entering("server.database.Fields", "getFieldTitle");
		
		String result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select title from field where id = " + field;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getString(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Fields", "getFieldTitle", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Fields", "getFieldTitle");
		
		return result;	
			
	}
	
	/**Retrieves the location of an HTML file that contains end-user help for the given field
	 * @param field					Field id
	 * @return						Location of an HTML file that contains end-user help for the given field
	 * @throws DatabaseException	throws Database Exception if operations fail for any reason
	 */
	public String getHelpUrl(int project) throws DatabaseException {
		logger.entering("server.database.Fields", "getHelpUrl");
		
		String result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select helphtml from field where project = " + project;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getString(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Fields", "getHelpUrl", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Fields", "getHelpUrl");
		
		return result;	
			
	}
	
	/**Retrieves the x-coordinate of the field on the project’s images
	 * @param field					Field id
	 * @return						X-coordinate of the field on the project’s images
	 * @throws DatabaseException	throws Database Exception if operations fail for any reason
	 */
	public int getXCoord(int project) throws DatabaseException {
		logger.entering("server.database.Fields", "getXCoord");
		
		int result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select xcoord from field where project = " + project;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getInt(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Fields", "getXCoord", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Fields", "getXCoord");
		
		return result;	
			
	}
	
	/**Retrieves the width of the given field in the project’s images, measured in pixels.
	 * @param field					Field id
	 * @return						Width of the given field in the project’s images, measured in pixels.
	 * @throws DatabaseException	throws Database Exception if operations fail for any reason
	 */
	public int getPixelWidth(int project) throws DatabaseException {
		logger.entering("server.database.Fields", "getPixelWidth");
		
		int result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select width from field where project = " + project;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getInt(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Fields", "getPixelWidth", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Fields", "getPixelWidth");
		
		return result;	
			
	}
	
	/**Retrieves the location of a text file that contains known values for the given field
	 * @param field					Field id
	 * @return						Location of a text file that contains known values for the given field
	 * @throws DatabaseException	throws Database Exception if operations fail for any reason
	 */
	public String getKnownValuesUrl(int project) throws DatabaseException {
		logger.entering("server.database.Fields", "getKnownValuesUrl");
		
		String result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select knowndata from field where project = " + project;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getString(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Fields", "getKnownValuesUrl", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Contacts", "getKnownValuesUrl");
		
		return result;	
			
	}
	
	 /**Returns all fields for a specified project
	 * @param projectid					Project id
	 * @return 							List of all fields
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public List<Field> getAllInProj(int projectid) throws DatabaseException {
		logger.entering("server.database.Fields", "getAllInProj");
		
		ArrayList<Field> result = new ArrayList<Field>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select id, title, xcoord, width, helphtml, knowndata, project, columnnum from field where project = " + projectid;
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int xcoord = rs.getInt(3);
				int width = rs.getInt(4);
				String helphtml = rs.getString(5);
				String knowndata = rs.getString(6);
				int project = rs.getInt(7);
				int columnnum = rs.getInt(8);

				Field field = new Field(title, xcoord, width, helphtml, knowndata, project, columnnum);
				field.setId(id);
				result.add(field);
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Fields", "getAllInProj", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Fields", "getAllInProj");
		
		return result;	
			
	}
	
	 /**Returns all fields in system
	  * @return 							List of all fields
	  * @throws DatabaseException		throws Database Exception if operations fail for any reason
	  */
	public List<Field> getAll() throws DatabaseException {

		logger.entering("server.database.Fields", "getAll");
		
		ArrayList<Field> result = new ArrayList<Field>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select id, title, xcoord, width, helphtml, knowndata, project, columnnum from field";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int xcoord = rs.getInt(3);
				int width = rs.getInt(4);
				String helphtml = rs.getString(5);
				String knowndata = rs.getString(6);
				int project = rs.getInt(7);
				int columnnum = rs.getInt(8);

				Field field = new Field(title, xcoord, width, helphtml, knowndata, project, columnnum);
				field.setId(id);
				result.add(field);
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Fields", "getAll", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Fields", "getAll");
		
		return result;	
			
	}
	
	/**Add new field to database
	 * @param field						Field object to be added
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void add(Field field) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into field (title, xcoord, width, helphtml, knowndata, project, columnnum) values (?, ?, ?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, field.getTitle());
			stmt.setInt(2, field.getXcoord());
			stmt.setInt(3, field.getWidth());
			stmt.setString(4, field.getHelphtml());
			stmt.setString(5, field.getKnowndata());
			stmt.setInt(6, field.getProject());
			stmt.setInt(7, field.getColumnnum());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				field.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert field");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert field", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	/**Update information in a field
	 * @param field						Field to be updated
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void update(Field field) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update field set title = ?, xcoord = ?, width = ?, helphtml = ?, knowndata = ?, project = ?, columnnum = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, field.getTitle());
			stmt.setInt(2, field.getXcoord());
			stmt.setInt(3, field.getWidth());
			stmt.setString(4, field.getHelphtml());
			stmt.setString(5, field.getKnowndata());
			stmt.setInt(6, field.getProject());
			stmt.setInt(7, field.getColumnnum());
			stmt.setInt(8, field.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update field");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update field", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	/**Deletes field from database
	 * @param field						Field to be deleted
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void delete(Field field) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from field where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, field.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete field");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete field", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

}