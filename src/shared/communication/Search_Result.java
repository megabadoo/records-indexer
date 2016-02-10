package shared.communication;

import server.database.DatabaseException;
import shared.model.*;
import java.util.*;

/**
 * @author meganarnell
 *
 */
public class Search_Result {
	
	private boolean valid;
	
	private String url_header;

	/**
	 * Batch id
	 */
	private int batch_id;
	
	/**
	 * Image url
	 */
	private String image_url;
	
	/**
	 * Record number
	 */
	private int record_num;
	
	/**
	 * Field id
	 */
	private int field_id;
	
	/**Creates new Search_Result
	 * @throws DatabaseException		throws DatabaseException if operation fails for any reason
	 */
	public Search_Result() throws DatabaseException{
		batch_id = -1;
		image_url = null;
		record_num = -1;
		field_id = -1;
	}

	
	
	public boolean isValid() {
		return valid;
	}



	public void setValid(boolean valid) {
		this.valid = valid;
	}



	public String getUrl_header() {
		return url_header;
	}



	public void setUrl_header(String url_header) {
		this.url_header = url_header;
	}



	/**Retrieves batch id
	 * @return batch_id		Batch id
	 */
	public int getBatch_id() {
		return batch_id;
	}

	/**Sets batch id to given parameter value
	 * @param batch_id		Batch id
	 */
	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
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

	/**Retrieves record number
	 * @return record_num		Record number
	 */
	public int getRecord_num() {
		return record_num;
	}

	/**Sets record number to given parameter value
	 * @param record_num		Record number
	 */
	public void setRecord_num(int record_num) {
		this.record_num = record_num;
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

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append(batch_id + "\n");
		str.append(url_header + image_url + "\n");
		str.append(record_num + "\n");
		str.append(field_id + "\n");
		
		return str.toString();
	}
	
}
