package server.database;

import java.io.*;
import java.sql.*;
import java.util.logging.*;


public class Database {
	
	private static final String DATABASE_DIRECTORY = "database";
	private static final String DATABASE_FILE = "record-indexer.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY +
												File.separator + DATABASE_FILE;

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("recordindexer");
	}

	public static void initialize() throws DatabaseException {
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(ClassNotFoundException e) {
			
			DatabaseException serverEx = new DatabaseException("Could not load database driver", e);
			
			logger.throwing("server.database.Database", "initialize", serverEx);

			throw serverEx; 
		}
	}

	private FieldsDAO fieldsDAO;
	private ProjectsDAO projectsDAO;
	private ImagesDAO imagesDAO;
	private RecordsDAO recordsDAO;
	private UsersDAO usersDAO;
	private ValuesDAO valuesDAO;
	private Connection connection;
	
	public Database() {
		fieldsDAO = new FieldsDAO(this);
		projectsDAO = new ProjectsDAO(this);
		imagesDAO = new ImagesDAO(this);
		recordsDAO = new RecordsDAO(this);
		usersDAO = new UsersDAO(this);
		valuesDAO = new ValuesDAO(this);
		connection = null;
	}
	
	public FieldsDAO getFieldsDAO() {
		return fieldsDAO;
	}
	
	public ProjectsDAO getProjectsDAO() {
		return projectsDAO;
	}
	
	public ImagesDAO getImagesDAO() {
		return imagesDAO;
	}
	
	public RecordsDAO getRecordsDAO() {
		return recordsDAO;
	}
	
	public UsersDAO getUsersDAO() {
		return usersDAO;
	}
	
	public ValuesDAO getValuesDAO() {
		return valuesDAO;
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void startTransaction() throws DatabaseException {
		try {
			assert (connection == null);			
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not connect to database. Make sure " + 
				DATABASE_FILE + " is available in ./" + DATABASE_DIRECTORY, e);
		}
	}
	
	public void endTransaction(boolean commit) {
		if (connection != null) {		
			try {
				if (commit) {
					connection.commit();
				}
				else {
					connection.rollback();
				}
			}
			catch (SQLException e) {
				System.out.println("Could not end transaction");
				e.printStackTrace();
			}
			finally {
				safeClose(connection);
				connection = null;
			}
		}
	}
	
	public static void safeClose(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(PreparedStatement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
}
