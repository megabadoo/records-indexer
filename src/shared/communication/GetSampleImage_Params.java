package shared.communication;

import server.database.DatabaseException;
import shared.model.*;

/**
 * @author meganarnell
 *
 */
public class GetSampleImage_Params {

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
	private int project_id;
	
	/**
	 * @throws DatabaseException	throws DatabaseException if operation fails for any reason
	 */
	public GetSampleImage_Params() throws DatabaseException{
		user = null;
		password = null;
		project_id = -1;
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
	 * @return project_id		Project id
	 */
	public int getProject_id() {
		return project_id;
	}

	/**Sets project id to given parameter value
	 * @param project_id		Project id
	 */
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	
	
	
}
