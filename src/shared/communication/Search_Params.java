package shared.communication;

import server.database.DatabaseException;
import shared.model.*;
import java.util.*;

/**
 * @author meganarnell
 *
 */
public class Search_Params {

	/**
	 * User's name
	 */
	private String user;
	
	/**
	 * User's password
	 */
	private String password;
	
	/**
	 * List of field ids of fields to be searched
	 */
	private List<Integer> fields;
	
	/**
	 * List of strings to search for
	 */
	private List<String> search_values;
	
	/**Creates new Search_Params
	 * @throws DatabaseException	throws DatabaseException if operation fails for any reason
	 */
	public Search_Params() throws DatabaseException{
		user = null;
		password = null;
		fields = null;
		search_values = null;
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

	/**Retrieves list of field ids of fields to be searched
	 * @return fields		List of field ids of fields to be searched
	 */
	public List<Integer> getFields() {
		return fields;
	}

	/**Sets list of field ids of fields to be searched to given parameter value
	 * @param fields		List of field ids of fields to be searched
	 */
	public void setFields(List<Integer> fields) {
		this.fields = fields;
	}

	/**Retrieves list of strings to search for
	 * @return search_values		List of strings to search for
	 */
	public List<String> getSearch_values() {
		return search_values;
	}

	/**Sets list of strings to search for to given parameter value
	 * @param search_values		List of strings to search for
	 */
	public void setSearch_values(List<String> search_values) {
		this.search_values = search_values;
	}

	
	
}
