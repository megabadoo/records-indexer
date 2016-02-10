package shared.model;

import java.util.ArrayList;
import org.w3c.dom.Element;
import server.importer.DataImporter;

/**
 * @author meganarnell
 *
 */
public class Project {

	/**
	 * Unique project id
	 */
	private int id;
	
	/**
	 * Project's title
	 */
	private String title;
	
	/**
	 * Positive integer that indicates the number of records that appear on each batch image
	 */
	private int recordsperimage;
	
	/**
	 * Non-negative integer that specifies the y-coordinate of the top of the first record on the project's images.  Coordinates are relative to the top-left corner of the image, zero-based, and measured in pixels
	 */
	private int firstycoord;
	
	/**
	 *Positive integer that specifies the height of each record in the prject's images, measured in pixels.  All records in the project's images have the same height. 
	 */
	private int recordheight;
	
	/**
	 *Integer that specifies the number of fields in the project
	 */
	private int numfields;
	
	ArrayList<Field> fields = new ArrayList<Field>();
	ArrayList<Image> images = new ArrayList<Image>();
	
	/**
	 * Creates a new project with default values
	 */
	public Project() {
		setId(-1);
		setTitle("Title");
		setRecordsperimage(0);
		setFirstycoord(0);
		setRecordheight(0);
		setNumfields(0);
	}
	
	/**Creates a new project with given field values
	 * @param title					Project's title
	 * @param recordsperimage		Number of records that appear on each batch image
	 * @param firstycoord			Y-coordinate of the top of the first record on the project's images
	 * @param recordheight			Height of each record in the project's images, measured in pixels
	 */
	public Project(String title, int recordsperimage, int firstycoord,
					int recordheight, int numfields) {
		setTitle(title);
		setRecordsperimage(recordsperimage);
		setFirstycoord(firstycoord);
		setRecordheight(recordheight);
		setNumfields(numfields);
	}
	
	public Project(Element projectElement) {
        title = DataImporter.getValue((Element)projectElement.getElementsByTagName("title").item(0));
        recordsperimage = Integer.parseInt(DataImporter.getValue((Element)projectElement.getElementsByTagName("recordsperimage").item(0)));
        firstycoord = Integer.parseInt(DataImporter.getValue((Element)projectElement.getElementsByTagName("firstycoord").item(0)));
        recordheight = Integer.parseInt(DataImporter.getValue((Element)projectElement.getElementsByTagName("recordheight").item(0)));
    }
	
	
	/**Retrieves number of fields in project
	 * @return numfields		Number of fields in project
	 */
	public int getNumfields() {
		return numfields;
	}

	/**Sets number of fields in project to given parameter value
	 * @param numfields		Number of fields in project
	 */
	public void setNumfields(int numfields) {
		this.numfields = numfields;
	}

	/**Retrieves project's id
	 * @return id		Project's id
	 */
	public int getId() {
		return id;
	}

	/**Sets project's id to given parameter value
	 * @param id		Project's id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**Retrieves project's title
	 * @return title 		Project's title
	 */
	public String getTitle() {
		return title;
	}

	/**Sets project's title to given parameter value
	 * @param title		Project's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**Retrieves the number of records that appear on each batch image
	 * @return recordsperimage		Number of records that appear on each batch image
	 */
	public int getRecordsperimage() {
		return recordsperimage;
	}

	/**Sets the number of records that appear on each batch image to given parameter value
	 * @param recordsperimage		Number of records that appear on each batch image
	 */
	public void setRecordsperimage(int recordsperimage) {
		this.recordsperimage = recordsperimage;
	}

	/**Retrieves first y-coordinate of the top of the first record on the project's images
	 * @return firstycoord		First y-coordinate of the top of the first record on the project's images
	 */
	public int getFirstycoord() {
		return firstycoord;
	}

	/**Sets the y-coordinate of the top of the first record on the project's images to given parameter value
	 * @param firstycoord		Y-coordinate of the top of the first record on the project's images
	 */
	public void setFirstycoord(int firstycoord) {
		this.firstycoord = firstycoord;
	}

	/**Retrieves height of each record in the project's images, measured in pixels
	 * @return recordheight		Height of each record in the project's images, measured in pixels
	 */
	public int getRecordheight() {
		return recordheight;
	}

	/**Set the height of each record in the project's images to given parameter value
	 * @param recordheight		Height of each record in the project's images, measured in pixels
	 */
	public void setRecordheight(int recordheight) {
		this.recordheight = recordheight;
	}
	
}