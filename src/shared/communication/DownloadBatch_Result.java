package shared.communication;

import java.util.List;

import server.database.DatabaseException;
import shared.model.*;

/**
 * @author meganarnell
 *
 */
public class DownloadBatch_Result {
	
	private boolean valid;
	
	private String url_header;

	/**
	 * String displayed if downloadBatch method succeeds
	 */
	private String succeeded;
	
	/**
	 * String displayed if downloadBatch method fails
	 */
	private String failed;
	
	/**
	 * Batch id
	 */
	private int batch_id;
	
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
	 * Image url
	 */
	private String image_url;
	
	/**
	 * Non-negative integer that specifies the y-coordinate of the top of the first record on the project's images
	 */
	private int first_y_coord;
	
	/**
	 * Positive integer that specifies the height of each record in the project's images, measured in pixels.  All records in the project's images have the same height
	 */
	private int record_height;
	
	/**
	 * Number of records in batch
	 */
	private int num_records;
	
	/**
	 * Number of fields in batch
	 */
	private int num_fields;
	
	/**
	 * Field number
	 */
	private int field_num;
	
	/**
	 * Field title
	 */
	private String field_title;
	
	/**
	 * Location of HTML file that contains end-user help for a given field
	 */
	private String help_url;
	
	/**
	 * X-coordinate of field on a project's image
	 */
	private int x_coord;
	
	/**
	 * Width of field in a project's image
	 */
	private int pixel_width;
	
	/**
	 * OPTIONAL. Specifies the location of a text file that contains known values for this field.  The file's location is a relative path to a file in the ZIP file's knowndata sub-directory.  The path is relative to the directory containing the XML file
	 */
	private String known_values_url;
	
	/**creates DownloadBatch_Result
	 * @throws DatabaseException		throws DatabaseException if operation fails for any reason
	 */
	public DownloadBatch_Result() throws DatabaseException{
		succeeded = null;
		failed = null;
		batch_id = -1;
		fields = null;
		image_url = null;
		first_y_coord = -1;
		record_height = -1;
		num_records = -1;
		num_fields = -1;
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



	public String getSucceeded() {
		return succeeded;
	}



	public void setSucceeded(String succeeded) {
		this.succeeded = succeeded;
	}



	public String getFailed() {
		return failed;
	}



	public void setFailed(String failed) {
		this.failed = failed;
	}



	public List<Field> getFields() {
		return fields;
	}



	public void setFields(List<Field> fields) {
		this.fields = fields;
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

	/**Retrieves y-coordinate of the top of the first record in the project's images
	 * @return first_y_coord		Y-coordinate of the top of the first record in the project's images
	 */
	public int getFirst_y_coord() {
		return first_y_coord;
	}

	/**Sets y-coordinate of the top of the first record in the project's images to given parameter value
	 * @param first_y_coord		Y-coordinate of the top of the first record in the project's images
	 */
	public void setFirst_y_coord(int first_y_coord) {
		this.first_y_coord = first_y_coord;
	}

	/**Retrieves record height of each record in the project's images
	 * @return record_height		Record height of each record in the project's images, measured in pixels
	 */
	public int getRecord_height() {
		return record_height;
	}

	/**Sets record height of each record in the project's images to given parameter value
	 * @param record_height		Record height of each record in the project's images, measured in pixels
	 */
	public void setRecord_height(int record_height) {
		this.record_height = record_height;
	}

	/**Retrieves number of records in batch
	 * @return num_records		Number of records in batch
	 */
	public int getNum_records() {
		return num_records;
	}

	/**Sets number of records in batch to given parameter value
	 * @param num_records		Number of records in batch
	 */
	public void setNum_records(int num_records) {
		this.num_records = num_records;
	}

	/**Retrieves number of fields in batch
	 * @return num_fields		Number of fields in batch
	 */
	public int getNum_fields() {
		return num_fields;
	}

	/**Sets number of fields in batch to given parameter value
	 * @param num_fields		Number of fields in batch
	 */
	public void setNum_fields(int num_fields) {
		this.num_fields = num_fields;
	}

	/**Retrieves field number
	 * @return field_num		Field number
	 */
	public int getField_num() {
		return field_num;
	}

	/**Sets field number to given parameter value
	 * @param field_num		Number of fields in batch
	 */
	public void setField_num(int field_num) {
		this.field_num = field_num;
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

	/**Retrieves location of HTML file that contains end-user help for a given field
	 * @return help_url		Location of HTML file that contains end-user help for a given field
	 */
	public String getHelp_url() {
		return help_url;
	}

	/**Sets location of HTML file that contains end-user help for a given field to given parameter value
	 * @param help_url		Location of HTML file that contains end-user help for a given field
	 */
	public void setHelp_url(String help_url) {
		this.help_url = help_url;
	}

	/**Retrieves x-coordinate of field on the project's images
	 * @return x_coord		X-coordinate of field on the project's images
	 */
	public int getX_coord() {
		return x_coord;
	}

	/**Sets x-coordinate of field on the project's images to given parameter value
	 * @param x_coord		X-coordinate of field on the project's images
	 */
	public void setX_coord(int x_coord) {
		this.x_coord = x_coord;
	}

	/**Retrieves width of field on project's images
	 * @return pixel_width		Width of field on project's images, measured in pixels
	 */
	public int getPixel_width() {
		return pixel_width;
	}

	/**Sets width of field on project's images to given parameter value
	 * @param pixel_width		Width of field on project's images, measured in pixels
	 */
	public void setPixel_width(int pixel_width) {
		this.pixel_width = pixel_width;
	}

	/**Retrieves location of a text file that contains known values for this field
	 * @return known_values_url		Location of a text file that contains known values for this field
	 */
	public String getKnown_values_url() {
		return known_values_url;
	}

	/**Sets location of a text file that contains known values for this field to given parameter value
	 * @param known_values_url		Location of a text file that contains known values for this field
	 */
	public void setKnown_values_url(String known_values_url) {
		this.known_values_url = known_values_url;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append(batch_id + "\n");
		str.append(project_id + "\n");
		str.append(url_header + image_url + "\n");
		str.append(first_y_coord + "\n");
		str.append(record_height + "\n");
		str.append(num_records + "\n");
		str.append(num_fields + "\n");
		for(int x=0; x<fields.size(); x++){
			str.append(fields.get(x).getId() + "\n");
			str.append(fields.get(x).getColumnnum() + "\n");
			str.append(fields.get(x).getTitle() + "\n");
			str.append(url_header + fields.get(x).getHelphtml() + "\n");
			str.append(fields.get(x).getXcoord() + "\n");
			str.append(fields.get(x).getWidth() + "\n");
			if(!fields.get(x).getKnowndata().equals("")){
				str.append(url_header + fields.get(x).getKnowndata() + "\n");
			}
		}
		
		return str.toString();
	}

	
}
