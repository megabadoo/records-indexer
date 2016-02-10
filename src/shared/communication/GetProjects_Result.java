package shared.communication;

import server.database.DatabaseException;
import shared.model.*;
import java.util.*;

/**
 * @author meganarnell
 *
 */
public class GetProjects_Result {
	
	private boolean validUser;

	/**
	 * Project id
	 */
	private int project_id;
	
	/**
	 * Project title
	 */
	private String project_title;
	
	private String url_header;
	
	private List<Project> projects;
	
	/**Creates new GetProjects_Result
	 * @throws DatabaseException		throws DatabaseException if operation fails for any reason
	 */
	public GetProjects_Result() throws DatabaseException{
		project_id = -1;
		project_title = null;
		projects = null;
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



	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
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
	
	/**Retrieves project title
	 * @return project_title		Project title
	 */
	public String getProject_title() {
		return project_title;
	}

	/**Sets project title to given parameter value
	 * @param project_title		Project title
	 */
	public void setProject_title(String project_title) {
		this.project_title = project_title;
	}
	
	@Override
	public String toString() {
		String str = "";
		
		for(int x=0; x<projects.size(); x++){
			str = str + projects.get(x).getId() + "\n";
			str = str + projects.get(x).getTitle() + "\n";
		}
		
		return str;
	}

	
}
