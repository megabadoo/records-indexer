package server.database;

import java.util.*;
import java.sql.*;
import java.util.logging.*;

import shared.model.Image;
import shared.model.Project;



/**
 * @author meganarnell
 *
 */
public class ImagesDAO {
	
	private static Logger logger;
	
	static {
		logger = Logger.getLogger("recordindexer");
	}

	private Database db;
	

	/**Creates new ImagesDAO
	 * @param db	Database
	 */
	ImagesDAO(Database db) {
		this.db = db;
	}
	
	public void dropTable() throws DatabaseException {
		logger.entering("server.database.Fields", "getColumnNum");
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String query = "drop table if exists image";
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
			String query = "create table image(id integer not null primary key autoincrement,project integer not null,file text not null, indexed boolean)";
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
	
	/**Retrieves a sample image from a particular project
	 * @param project					Project id
	 * @return							Url of sample image from project
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public String getSampleImage(int project) throws DatabaseException {
		logger.entering("server.database.Images", "getSampleImage");
		
		String result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select file from image where project = " + project + " limit 1";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getString(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Images", "getSampleImage", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Images", "getSampleImage");
		
		return result;	
			
	}
	
	/**Retrieves batch_id from the Image object
	 * @param project					Project id
	 * @return							Image id
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public int getBatchId(int project) throws DatabaseException {
		logger.entering("server.database.Images", "getBatchId");
		
		int result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select id from image where project = " + project + " and indexed=0";
			stmt = db.getConnection().prepareStatement(query);
			boolean isindexed;
			rs = stmt.executeQuery();
			do{
				result = rs.getInt(1);
				isindexed = isIndexed(result);
			}while((db.getUsersDAO().batchChecked(result) || isIndexed(result)==true) && rs.next());
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Images", "getBatchId", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Images", "getBatchId");
		
		return result;	
			
	}
	
	/**Retrieves batch_id from the Image object
	 * @param project					Project id
	 * @return							Image id
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public int getProjectId(int image) throws DatabaseException {
		logger.entering("server.database.Images", "getProjectId");
		
		int result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select project from image where id = " + image;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getInt(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Images", "getProjectId", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Images", "getBatchId");
		
		return result;	
			
	}
	
	/**Retrieves image_url from the Image object
	 * @param project					Project id
	 * @return							Image url
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public String getImageUrl(int image) throws DatabaseException {
		logger.entering("server.database.Images", "getImageUrl");
		
		String result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select file from image where id = " + image;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getString(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Images", "getImageUrl", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Images", "getImageUrl");
		
		return result;	
			

	}
	
	public boolean isIndexed(int image) throws DatabaseException {
		logger.entering("server.database.Images", "getImageUrl");
		
		boolean result;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select indexed from image where id = " + image;
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			result = rs.getBoolean(1);
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Images", "getImageUrl", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Images", "getImageUrl");
		
		return result;	
			

	}
	
	/**Returns list of all Images in database
	 * @return							List of all Images in database
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public List<Image> getAll() throws DatabaseException {

		logger.entering("server.database.Images", "getAll");
		
		ArrayList<Image> result = new ArrayList<Image>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select id, project, file, indexed from image";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int project = rs.getInt(2);
				String file = rs.getString(3);
				boolean indexed = rs.getBoolean(4);

				Image image = new Image(project, file);
				image.setIndexed(indexed);
				image.setId(id);
				result.add(image);
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Images", "getAll", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Images", "getAll");
		
		return result;	
			

	}
	
	/**Adds new Image to database
	 * @param image						Image object to be added
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void add(Image image) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into image (project, file, indexed) values (?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, image.getProject());
			stmt.setString(2, image.getFile());
			stmt.setBoolean(3, image.isIndexed());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				image.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert image");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert image", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	/**Updates information about Image in database
	 * @param image						Image object to be updated
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void update(Image image) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update image set project = ?, file = ?, indexed = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, image.getProject());
			stmt.setString(2, image.getFile());
			stmt.setBoolean(3, image.isIndexed());
			stmt.setInt(4, image.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update image");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update image", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	public void updateIndexedBatch(int id) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update image set indexed = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setBoolean(1, true);
			stmt.setInt(2, id);
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update image");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update image", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	/**Deletes Image from database
	 * @param image						Image object to be deleted
	 * @throws DatabaseException		throws Database Exception if operations fail for any reason
	 */
	public void delete(Image image) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from image where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, image.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete image");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete image", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

}