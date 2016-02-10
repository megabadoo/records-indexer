package shared.communication;

import server.database.DatabaseException;
import shared.model.*;
import java.util.*;

/**
 * @author meganarnell
 *
 */
public class GetFields_Result {
	
	private boolean validUser;
	
	private String url_header;

	/**
	 * Project id
	 */
	private int project_id;
	
	private List<Field> fields;
	
	/**
	 * Field id
	 */
	private int field_id;
	
	/**
	 * Field title
	 */
	private String field_title;
	
	/**Create GetFields_Result
	 * @throws DatabaseException		throws DatabaseException if operation fails for any reason
	 */
	public GetFields_Result() throws DatabaseException {
		project_id = -1;
		field_id = -1;
		field_title = null;
		fields = null;
	}

	
	
	
	public boolean isValidUser() {
		return validUser;
	}




	public void setValidUser(boolean validUser) {
		this.validUser = validUser;
	}




	public String getUrl_header() {
		return url_header;
	}



	public void setUrl_header(String url_header) {
		this.url_header = url_header;
	}



	public List<Field> getFields() {
		return fields;
	}



	public void setFields(List<Field> fields) {
		this.fields = fields;
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

	/**Retrieves field id
	 * @return field_id		Field id
	 */
	public int getField_id() {
		return field_id;
	}

	/**Sets field id to given parameter value
	 * @param field_id		Field id
	 */
	public void setField_id(int field_id) {
		this.field_id = field_id;
	}

	/**Retrieves field's title
	 * @return field_title		Field's title
	 */
	public String getField_title() {
		return field_title;
	}

	/**Sets field's title to given parameter value
	 * @param field_title		Field's title
	 */
	public void setField_title(String field_title) {
		this.field_title = field_title;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		for(int x=0; x<fields.size(); x++){
			str.append(fields.get(x).getProject() + "\n");
			str.append(fields.get(x).getId() + "\n");
			str.append(fields.get(x).getTitle() + "\n");
		}
		
		return str.toString();
	}
	
}
