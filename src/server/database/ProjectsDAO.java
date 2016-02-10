package server.database;

import java.util.*;
import java.sql.*;
import java.util.logging.*;

import shared.model.Field;
import shared.model.Project;



/**
 * @author meganarnell
 *
 */
public class ProjectsDAO {
	
	private static Logger logger;
	
	static {
		logger = Logger.getLogger("recordindexer");
	}

	private Database db;
	
	/**Creates new ProjectDAO
	 * @param db	Database
	 */
	ProjectsDAO(Database db) {
		this.db = db;
	}	
	
	public void dropTable() throws DatabaseException {
		logger.entering("server.database.Fields", "getColumnNum");
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String query = "drop table if exists project";
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
			String query = "create table project(id integer not null primary key autoincrement,title text not null,recordsperimage int not null check (recordsperimage>=0),firstycoord integer not null check (firstycoord>=0),recordheight integer not null check (recordheight>=0),numfields integer not null)";
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
	
	/**Retrieves the y-coordinate of the top of the first record on the project’s images
	 * @param project				Project id
	 * @return						Y-coordinate of the top of the first record on the project’s images
	 * @throws DatabaseException	Throws Database Exception if operations fail for any reason
	 */
	public int getFirstYCoord(int project) throws DatabaseException {
		logger.entering("server.database.Projects", "getFirstYCoord");
		
		int result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select firstycoord from project where id = " + project;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getInt(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Fields", "getFirstYCoord", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Projects", "getFirstYCoord");
		
		return result;	
	}
	
	/**Retrieves the height of each record in the project’s images, measured in pixels
	 * @param project				Project id
	 * @return						Height of each record in the project’s images, measured in pixels
	 * @throws DatabaseException	Throws Database Exception if operations fail for any reason
	 */
	public int getRecordHeight(int project) throws DatabaseException {
	logger.entering("server.database.Projects", "getRecordHeight");
		
		int result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select recordheight from project where id = " + project;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getInt(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Projects", "getRecordHeight", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Projects", "getRecordHeight");
		
		return result;	
	}
	
	/**Retrieves number of records per image in the project
	 * @param project				Project id
	 * @return						Number of records per image in the project
	 * @throws DatabaseException	Throws Database Exception if operations fail for any reason
	 */
	public int getRecsPerImage(int project) throws DatabaseException {
		logger.entering("server.database.Projects", "getRecsPerImage");
		
		int result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select recordsperimage from project where id = " + project;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getInt(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Projects", "getRecsPerImage", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Projects", "getRecsPerImage");
		
		return result;	
	}
	
	/**Retrieves number of projects in project
	 * @param project				Project id
	 * @return						Number of projects in project
	 * @throws DatabaseException	Throws Database Exception if operations fail for any reason
	 */
	public int getNumFields(int project) throws DatabaseException {
		logger.entering("server.database.Projects", "getNumFields");
		
		int result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select numfields from project where id = " + project;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getInt(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Projects", "getNumFields", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Projects", "getNumFields");
		
		return result;	
	}
	
	/**Returns information about all of the available projects for a given user
	 * @param username					User's name      I took these out because i don't think i need them, just to validate, but i can do that in the facade
	 * @param password					User's password
	 * @return							List of all of the available projects for a given user
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public List<Project> getAll() throws DatabaseException {

		logger.entering("server.database.Projects", "getAll");
		
		ArrayList<Project> result = new ArrayList<Project>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select id, title, recordsperimage, firstycoord, recordheight, numfields from project";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int recordsperimage = rs.getInt(3);
				int firstycoord = rs.getInt(4);
				int recordheight = rs.getInt(5);
				int numfields = rs.getInt(6);

				Project project = new Project(title, recordsperimage, firstycoord, recordheight, numfields);
				project.setId(id);
				result.add(project);
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Projects", "getAll", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Projects", "getAll");
		
		return result;	
			
	}
	
	/**Adds new Project to database
	 * @param project					Project object to be added
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void add(Project project) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into project (title, recordsperimage, firstycoord, recordheight, numfields) values (?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, project.getTitle());
			stmt.setInt(2, project.getRecordsperimage());
			stmt.setInt(3, project.getFirstycoord());
			stmt.setInt(4, project.getRecordheight());
			stmt.setInt(5, project.getNumfields());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				project.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert project");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert project", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	/**Updates information about Project in database
	 * @param project					Project object to be updated
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void update(Project project) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update project set title = ?, recordsperimage = ?, firstycoord = ?, recordheight = ?, numfields = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, project.getTitle());
			stmt.setInt(2, project.getRecordsperimage());
			stmt.setInt(3, project.getFirstycoord());
			stmt.setInt(4, project.getRecordheight());
			stmt.setInt(5, project.getNumfields());
			stmt.setInt(6, project.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update project");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update project", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	/**Deletes Project from database
	 * @param project					Project object to be deleted
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void delete(Project project) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from project where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, project.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete project");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete project", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

}