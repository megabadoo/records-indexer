package shared.model;

import org.w3c.dom.Element;

import server.importer.DataImporter;

/**
 * @author meganarnell
 *
 */
public class Field {

	/**
	 * Field's id
	 */
	private int id;
	
	/**
	 * Field's title, a non-empty string
	 */
	private String title;
	
	/**
	 * Non-negative integer that specifies the x-coordinate of the field on the project's images.  Coordinates are relative to the top-left corner of the image, zero-based, and measured in pixels
	 */
	private int xcoord;
	
	/**
	 * Positive integer that specifies the width of the field in the project's images, measured in pixels
	 */
	private int width;
	
	/**
	 * Specifies the location of an HTML file that contains end-user help for this field.  The file's location is a relative path to a file in the ZIP file's fieldhelp sub-directory.  The path is relative to the directory containing the XML file
	 */
	private String helphtml;
	
	/**
	 * OPTIONAL. Specifies the location of a text file that contains known values for this field.  The file's location is a relative path to a file in the ZIP file's knowndata sub-directory.  The path is relative to the directory containing the XML file
	 */
	private String knowndata;
	
	/**
	 * Project's id to which the field corresponds
	 */
	private int project;
	
	/**
	 * The number of the column to which the field corresponds
	 */
	private int columnnum;
	
	/**
	 * Creates a field with default values
	 */
	public Field() {
		setId(-1);
		setTitle("Title");
		setXcoord(0);
		setWidth(0);
		setHelphtml(null);
		setKnowndata(null);
		setProject(0);
		setColumnnum(0);
	}
	
	/**
	 * Creates a field with given parameter values
	 * @param id			Field's id
	 * @param title			Field's title
	 * @param xcoord		X-coordinate of the field on the project's images
	 * @param width			Width of the field in the project's images, measured in pixels
	 * @param helphtml		Location of an HTML file that contains end-user help for this field
	 * @param knowndata		Location of a text file that contains known values for this field
	 * @param project		Project's id to which this field corresponds
	 * @param columnnum		The number of the column to which this field corresponds
	 */
	public Field(String title, int xcoord, int width,
					String helphtml, String knowndata, int project, int columnnum) {
	
		setTitle(title);
		setXcoord(xcoord);
		setWidth(width);
		setHelphtml(helphtml);
		setKnowndata(knowndata);
		setProject(project);
		setColumnnum(columnnum);
	}
	
	public Field(Element projectElement) {
        title = DataImporter.getValue((Element)projectElement.getElementsByTagName("title").item(0));
        xcoord = Integer.parseInt(DataImporter.getValue((Element)projectElement.getElementsByTagName("xcoord").item(0)));
        width = Integer.parseInt(DataImporter.getValue((Element)projectElement.getElementsByTagName("width").item(0)));
        helphtml = DataImporter.getValue((Element)projectElement.getElementsByTagName("helphtml").item(0));
        if((Element)projectElement.getElementsByTagName("knowndata").item(0)!=null)
        	knowndata = DataImporter.getValue((Element)projectElement.getElementsByTagName("knowndata").item(0));
        else
        	knowndata = "";
    }

	
	/**Retrieves the number of the column to which this field corresponds
	 * @return columnnum		The number of the column to which this field corresponds
	 */
	public int getColumnnum() {
		return columnnum;
	}

	/**Sets the column number to which this field corresponds to given parameter value
	 * @param columnnum		The number of the column to which this field corresponds
	 */
	public void setColumnnum(int columnnum) {
		this.columnnum = columnnum;
	}

	/**Retrieves project's id to which this field corresponds
	 * @return project		Project's id to which this field corresponds
	 */
	public int getProject() {
		return project;
	}

	/**Sets project's id to which this field corresponds to given parameter value
	 * @param project		Project's id to which this field corresponds
	 */
	public void setProject(int project) {
		this.project = project;
	}

	/**Retrieves field's id
	 * @return id		Field's id
	 */
	public int getId() {
		return id;
	}

	/**Set's field's id to given parameter value
	 * @param id		Field's id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**Retrieves field's title
	 * @return title		Field's title
	 */
	public String getTitle() {
		return title;
	}

	/**Set's field's title to given parameter value
	 * @param title		Field's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**Retrieves x-coordinate of the field on the project's images
	 * @return xcoord		X-coordinate of the field on the project's images
	 */
	public int getXcoord() {
		return xcoord;
	}

	/**Set's x-coordinate of the field on the project's images to given parameter value
	 * @param xcoord		X-coordinate of the field on the project's images
	 */
	public void setXcoord(int xcoord) {
		this.xcoord = xcoord;
	}

	/**Retrieves width of the field in the project's images
	 * @return width 		Width of the field in the project's images, measured in pixels
	 */
	public int getWidth() {
		return width;
	}

	/**Set's width of field in the project's images to given parameter value
	 * @param width		Width of field in the project's images, measured in pixels
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**Retrieves location of an HTML file that contains end-user help for this field
	 * @return helphtml		Location of an HTML file that contains end-user help for this field
	 */
	public String getHelphtml() {
		return helphtml;
	}

	/**Set's location of an HTML file that contains end-user help for this field to given parameter value
	 * @param helphtml		Location of an HTML file that contains end-user help for this field
	 */
	public void setHelphtml(String helphtml) {
		this.helphtml = helphtml;
	}

	/**Retrieves location of a text file that contains known values for this field
	 * @return knowndata		Location of a text file that contains known values for this field
	 */
	public String getKnowndata() {
		return knowndata;
	}

	/**Sets location of a text file that contains known values for this field to given parameter value
	 * @param knowndata		Location of a text file that contains known values for this field
	 */
	public void setKnowndata(String knowndata) {
		this.knowndata = knowndata;
	}
	
	
}
