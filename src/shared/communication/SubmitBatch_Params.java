package shared.communication;

import server.database.DatabaseException;
import shared.model.*;
import java.util.*;

/**
 * @author meganarnell
 *
 */
public class SubmitBatch_Params {

	/**
	 * User's name
	 */
	private String user;
	
	/**
	 * User's password
	 */
	private String password;
	
	/**
	 * Batch id
	 */
	private int batch;
	
	/**
	 * Field values for the batch
	 */
	private List<List <String>> field_values;
	
	/**
	 * Record values for the batch
	 */
	private List<String> record_values;
	
	/**
	 * @throws DatabaseException	throws DatabaseException if operation fails for any reason
	 */
	public SubmitBatch_Params() throws DatabaseException{
		user = null;
		password = null;
		batch = -1;
		field_values = null;
		record_values = null;
	}

	/**Retrieves user's name
	 * @return user		User's name
	 */
	public String getUser() {
		return user;
	}

	/**Sets user's name to given parameter value
	 * @param user		User's name
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**Retrieves user's password
	 * @return password		User's password
	 */
	public String getPassword() {
		return password;
	}

	/**Sets user's password to given parameter value
	 * @param password		User's password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**Retrieves batch id
	 * @return batch		Batch id
	 */
	public int getBatch() {
		return batch;
	}

	/**Sets batch id to given parameter value
	 * @param batch		Batch id
	 */
	public void setBatch(int batch) {
		this.batch = batch;
	}

	/**Retrieves field values for the batch
	 * @return field_values		Field values for the batch
	 */
	public List<List<String>> getField_values() {
		return field_values;
	}

	/**Sets field values for the batch to given parameter value
	 * @param field_values		Field values for the batch
	 */
	public void setField_values(List<List<String>> field_values) {
		this.field_values = field_values;
	}

	/**Retrieves record values for the batch
	 * @return record_values		Record values for the batch
	 */
	public List<String> getRecord_values() {
		return record_values;
	}

	/**Sets record values for the batch to given parameter value
	 * @param record_values		Record values for the batch
	 */
	public void setRecord_values(List<String> record_values) {
		this.record_values = record_values;
	}
	
}
