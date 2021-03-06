package shared.communication;

import server.database.DatabaseException;
import shared.model.*;

/**
 * @author meganarnell
 *
 */
public class GetFields_Params {

	/**
	 * User's name
	 */
	private String user;
	
	/**
	 * User's password
	 */
	private String password;
	
	/**
	 * Project id
	 */
	private int project;
	
	/**
	 * String if no project is specified
	 */
	private String empty;
	
	/**Creates GetField_Params
	 * @throws DatabaseException	throws DatabaseException if operation fails for any reason
	 */
	public GetFields_Params() throws DatabaseException{
		user = null;
		password = null;
		project = -1;
		empty = null;
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

	/**Retrieves project id
	 * @return project		Project id
	 */
	public int getProject() {
		return project;
	}

	/**Sets project id to given parameter value
	 * @param project		Project id
	 */
	public void setProject(int project) {
		this.project = project;
	}
	
	/**Retrieves empty string if no project is specified
	 * @return empty		Empty string if no project is specified
	 */
	public String getEmpty() {
		return empty;
	}

	/**Sets empty string if no project is specified
	 * @param empty		Empty string if no project is specified
	 */
	public void setEmpty(String empty) {
		this.empty = empty;
	}
	
	
}
