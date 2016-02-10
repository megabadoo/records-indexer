package shared.communication;

import shared.model.*;
import server.database.DatabaseException;

/**
 * @author meganarnell
 *
 */
public class ValidateUser_Result {

	/**
	 * Boolean for if user is valid
	 */
	private boolean valid;
	
	/**
	 * User's first name
	 */
	private String user_first_name;
	
	/**
	 * User's last name
	 */
	private String user_last_name;
	
	/**
	 * Number of records indexed by user
	 */
	private int num_records;
	
	private String url_header;
	
	/**Creates new ValidateUser
	 * @throws DatabaseException		throws DatabaseException if operation fails for any reason
	 */
	public ValidateUser_Result() throws DatabaseException {
		valid = false;
		user_first_name = null;
		user_last_name = null;
		num_records = -1;
	}

	
	
	public String getUrlHeader() {
		return url_header;
	}



	public void setUrlHeader(String url_header) {
		this.url_header = url_header;
	}



	/**Retrieves boolean for if user is valid
	 * @return valid		Boolean for if user is valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**Sets boolean for if user is valid to given parameter value
	 * @param valid		Boolean for if user is valid
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**Retrieves user's first name
	 * @return user_first_name		User's first name
	 */
	public String getUser_first_name() {
		return user_first_name;
	}

	/**Sets user's first name to given parameter value
	 * @param user_first_name		User's first name
	 */
	public void setUser_first_name(String user_first_name) {
		this.user_first_name = user_first_name;
	}

	/**Retrieves user's last name
	 * @return user_last_name		User's last name
	 */
	public String getUser_last_name() {
		return user_last_name;
	}

	/**Sets user's last name to given parameter value
	 * @param user_last_name		User's last name
	 */
	public void setUser_last_name(String user_last_name) {
		this.user_last_name = user_last_name;
	}

	/**Retrieves number of records indexed by user
	 * @return num_records		Number of records indexed by user
	 */
	public int getNum_records() {
		return num_records;
	}

	/**Sets number of records indexed by user to given parameter value
	 * @param num_records		Number of records indexed by user
	 */
	public void setNum_records(int num_records) {
		this.num_records = num_records;
	}
	
	@Override
	public String toString() {
		String str = "";
		
		if(valid){
			str = "TRUE\n" + user_first_name + "\n" + user_last_name + "\n" + num_records + "\n";
		}
		else{
			str = "FALSE\n";
		}
		return str;
	}
	
}
