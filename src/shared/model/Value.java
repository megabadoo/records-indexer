package shared.model;

import org.w3c.dom.Element;

import server.importer.DataImporter;

/**
 * @author meganarnell
 *
 */
public class Value {

	/**
	 * Value's id
	 */
	private int id;
	
	/**
	 * Value's value.  Can be any string (including empty)
	 */
	private String value;
	
	/**
	 * Record's id to which this value corresponds
	 */
	private int record;
	
	/**
	 * Record number of record to which this value corresponds
	 */
	private int recordnum;
	
	/**
	 * Image's id to which this value corresponds
	 */
	private int image;
	
	/**
	 * Image's url of image to which this value corresponds
	 */
	private String imageurl;
	
	/**
	 * Field column number to which this value corresponds
	 */
	private int columnnum;
	
	private int project;
	
	
	/**
	 * Creates new Value with default values
	 */
	public Value() {
		setId(-1);
		setValue("");
		setRecord(0);
		setImage(0);
		setImageurl(null);
	}
	
	/**Creates new value with given parameter values
	 * @param id
	 * @param value
	 * @param record
	 */
	public Value(String value, int record, int image, String imageurl, int columnnum, int recordnum, int project) {
		setValue(value);
		setRecord(record);
		setImage(image);
		setImageurl(imageurl);
		setColumnnum(columnnum);
		setRecordnum(recordnum);
		setProject(project);
		
	}
	
	public Value(Element recordElement) {
        //value = DataImporter.getValue((Element)recordElement.getElementsByTagName("value").item(0));
    }
	
	
	
	public int getProject() {
		return project;
	}

	public void setProject(int project) {
		this.project = project;
	}

	public int getColumnnum() {
		return columnnum;
	}

	public void setColumnnum(int columnnum) {
		this.columnnum = columnnum;
	}

	/**Retrieves record number of record to which value corresponds
	 * @return recordnum		Record number of record to which value corresponds
	 */
	public int getRecordnum() {
		return recordnum;
	}

	/**Sets record number of record to which value corresponds to given parameter value
	 * @param recordnum		Record number of record to which value corresponds
	 */
	public void setRecordnum(int recordnum) {
		this.recordnum = recordnum;
	}

	/**Retrieves image id of image to which value corresponds
	 * @return image		Image id of image to which value corresponds
	 */
	public int getImage() {
		return image;
	}

	/**Sets image id of image to which value corresponds to given parameter value
	 * @param image		Image id of image to which value corresponds
	 */
	public void setImage(int image) {
		this.image = image;
	}

	/**Retrieves image url of image to which value corresponds
	 * @return imageurl		Image url of image to which value corresponds
	 */
	public String getImageurl() {
		return imageurl;
	}

	/**Sets image url of image to which value corresponds to given parameter value
	 * @param imageurl		Image url of image to which value corresponds
	 */
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	/**Retrieves value's id
	 * @return id		Value's id
	 */
	public int getId() {
		return id;
	}

	/**Sets value's id to given parameter value
	 * @param id		Value's id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**Retrieves value's string value
	 * @return value		String value
	 */
	public String getValue() {
		return value;
	}

	/**Sets value's string value to given parameter value
	 * @param value		String value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**Retrieves record's id to which this value corresponds   
	 * @return record		Record's id to which this value corresponds
	 */
	public int getRecord() {
		return record;
	}

	/**Sets record's id to which this value corresponds to given parameter value
	 * @param record		Record's id to which this value corresponds
	 */
	public void setRecord(int record) {
		this.record = record;
	}
}