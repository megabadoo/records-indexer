package server.facade;

import java.util.*;

import server.database.*;
import shared.model.*;
import server.*;

public class ServerFacade {

	public static void initialize() throws ServerException {		
		try {
			Database.initialize();		
		}
		catch (DatabaseException e) {
			throw new ServerException(e.getMessage(), e);
		}		
	}
	
	public static boolean validateUser(String user, String password) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			boolean isValid = db.getUsersDAO().validateUser(user, password);
			db.endTransaction(true);
			return isValid;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static String getFirstName(String user, String password) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			String firstName = db.getUsersDAO().getFirstName(user, password);
			db.endTransaction(true);
			return firstName;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static String getLastName(String user, String password) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			String lastName = db.getUsersDAO().getLastName(user, password);
			db.endTransaction(true);
			return lastName;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static int getNumRecords(String user, String password) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			int numRecords = db.getUsersDAO().getNumRecords(user, password);
			db.endTransaction(true);
			return numRecords;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static int getBatchId(int project) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			int batchId = db.getImagesDAO().getBatchId(project);
			db.endTransaction(true);
			return batchId;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static List<Field> getFields(int project) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			List<Field> fields = db.getFieldsDAO().getAllInProj(project);
			db.endTransaction(true);
			return fields;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static List<Field> getFields() throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			List<Field> fields = db.getFieldsDAO().getAll();
			db.endTransaction(true);
			return fields;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static List<Project> getProjects() throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			List<Project> projects = db.getProjectsDAO().getAll();
			db.endTransaction(true);
			return projects;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static String getImageUrl(int image) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			String imageUrl = db.getImagesDAO().getImageUrl(image);
			db.endTransaction(true);
			return imageUrl;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static int getProjectId(int image) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			int projectid = db.getImagesDAO().getProjectId(image);
			db.endTransaction(true);
			return projectid;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static int getFirstYCoord(int project) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			int firstYCoord = db.getProjectsDAO().getFirstYCoord(project);
			db.endTransaction(true);
			return firstYCoord;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static int getRecordHeight(int project) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			int recordHeight = db.getProjectsDAO().getRecordHeight(project);
			db.endTransaction(true);
			return recordHeight;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static int getNumRecords(int project) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			int numRecords = db.getProjectsDAO().getRecsPerImage(project);
			db.endTransaction(true);
			return numRecords;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static int getNumFields(int project) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			int numFields = db.getProjectsDAO().getNumFields(project);
			db.endTransaction(true);
			return numFields;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static List<Value> search(int field, List<String> search_values) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			int project = db.getFieldsDAO().getProjectId(field);
			int columnnum = db.getFieldsDAO().getColumnNum(field);
			List<Value> values = db.getValuesDAO().getAll(project, columnnum);
			List<Value> matches = new ArrayList<Value>();
			for(int i=0; i<values.size(); i++){
				for(int j=0; j<search_values.size(); j++){
					String value = values.get(i).getValue();
					if(value.equals(search_values.get(j))){
						matches.add(values.get(i));
					}
				}
			}
			db.endTransaction(true);
			return matches;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static boolean submitBatch(int batch, List<List<String>> field_values) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			for(int x=0; x<field_values.size(); x++){
				Record record = new Record();
				record.setImage(batch);
				record.setRecordnum(x);
				db.getRecordsDAO().add(record);
				for(int y=0; y<field_values.get(x).size(); y++){
					Value value = new Value();
					value.setColumnnum(y+1);
					value.setImage(batch);
					value.setImageurl(db.getImagesDAO().getImageUrl(batch));
					value.setProject(db.getImagesDAO().getProjectId(batch));
					value.setRecord(record.getId());
					value.setRecordnum(x+1);
					value.setValue(field_values.get(x).get(y));
					db.getValuesDAO().add(value);
				}
			}
			
			db.endTransaction(true);
			return true;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static boolean hasBatch(String user, String password) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			int currentBatch = db.getUsersDAO().getCurrentBatch(user, password);
			db.endTransaction(true);
			if(currentBatch!=-1)
				return true;
			return false;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}

	public static boolean batchChecked(int batch) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			boolean batchChecked = db.getUsersDAO().batchChecked(batch);
			db.endTransaction(true);
			if(batchChecked)
				return true;
			return false;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static void updateCurrentBatch(String user, String password, int batch) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getUsersDAO().updateCurrentBatch(user, password, batch);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static int getCurrentBatch(String user, String password) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			int currentbatch = db.getUsersDAO().getCurrentBatch(user, password);
			db.endTransaction(true);
			return currentbatch;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static void updateIndexedRecords(String user, String password, int newrecords) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getUsersDAO().updateIndexedRecords(user, password, newrecords);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}

	public static void updateIndexedBatch(int batch) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getImagesDAO().updateIndexedBatch(batch);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static boolean batchIndexed(int batch) throws ServerException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			boolean indexed = db.getImagesDAO().isIndexed(batch);
			db.endTransaction(true);
			return indexed;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
}
