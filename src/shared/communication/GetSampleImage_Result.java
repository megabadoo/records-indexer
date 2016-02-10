package shared.communication;

import server.database.DatabaseException;
import shared.model.*;

/**
 * @author meganarnell
 *
 **/
public class GetSampleImage_Result {

	private boolean validUser;
	
	/**
	 * Image url
	 */
	private String image_url;
	
	private String url_header;
	
	/**Creates GetSampleImage_Result
	 * @throws DatabaseException		throws DatabaseException if operation fails for any reason
	 */
	public GetSampleImage_Result() throws DatabaseException{
		image_url = null;
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



	/**Retrieves image url
	 * @return image_url		Image url
	 */
	public String getImage_url() {
		return image_url;
	}

	/**Sets image url to given parameter value
	 * @param image_url		Image url
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
	@Override
	public String toString() {
		String str = url_header + image_url + "";
		
		return str;
	}
	
}
