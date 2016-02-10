package shared.communication;

import server.database.DatabaseException;
import shared.model.*;
import java.util.*;

/**
 * @author meganarnell
 *
 */
public class GetProjects_Params {

	/**
	 * User's name
	 */
	private String user;
	
	/**
	 * User's password
	 */
	private String password;
	
	/**Creates GetProjects_Params
	 * @throws DatabaseException	throws DatabaseException if operation fails for any reason
	 */
	public GetProjects_Params() throws DatabaseException{
		user = null;
		password = null;
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
	
}
