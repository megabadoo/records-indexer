package shared.model;

import org.w3c.dom.Element;

/**
 * @author meganarnell
 *
 */
public class Record {

	/**
	 * Record's id
	 */
	private int id;
	
	/**
	 * Image's id to which this record corresponds
	 */
	private int image;
	
	/**
	 * Number of the record in the image (row number)
	 */
	private int recordnum;
	
	/**
	 * Creates a new record with default values
	 */
	public Record() {
		setId(-1);
		setImage(0);
		setRecordnum(0);
	}
	
	/**Creates a new record with given parameter values
	 * @param id		Record's id
	 * @param image		Image's id to which this record corresponds
	 */
	public Record(int image, int recordnum) {
		setImage(image);
		setRecordnum(recordnum);
	}

	public Record(Element projectElement) {
		
	}
	
	/**Retrieves record number (row number)
	 * @return recordnum		Record number (row number)
	 */
	public int getRecordnum() {
		return recordnum;
	}

	/**Sets record number (row number) to given paremeter value
	 * @param recordnum		Record number (row number)
	 */
	public void setRecordnum(int recordnum) {
		this.recordnum = recordnum;
	}

	/**Retrieves record's id
	 * @return id		Record's id
	 */
	public int getId() {
		return id;
	}

	/**Sets record's id to given paremeter value
	 * @param id		Record's id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**Retrieves image's id to which this record corresponds
	 * @return image		Image's id to which this record corresponds
	 */
	public int getImage() {
		return image;
	}

	/**Sets image's id to which this record corresponds to given parameter value
	 * @param image		Image's id to which this record corresponds
	 */
	public void setImage(int image) {
		this.image = image;
	}
	
}