package shared.model;

import org.w3c.dom.Element;

/**
 * @author meganarnell
 *
 */
public class Image {
	
	private boolean indexed;

	/**
	 * Image's id
	 */
	private int id;
	
	/**
	 * Location of a PNG file that contains a single image for the project.  The file's location is a relative path to a file in the ZIP file's images sub-directory.  The path is relative to the directory containing the XML file
	 */
	private String file;
	
	/**
	 * Project's id to which this image corresponds
	 */
	private int project;

	/**
	 * Creates a new image with default values
	 */
	public Image() {
		setId(-1);
		setFile("File");
		setProject(0);
	}
	
	/**Creates a new image with given parameter values
	 * @param id
	 * @param file
	 */
	public Image(int project, String file) {
		setFile(file);
		setProject(project);
	}
	
	public Image(Element projectElement) {
		
	}
	
	

	public boolean isIndexed() {
		return indexed;
	}

	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}

	/**Retrieves project's id to which this image corresponds
	 * @return project		Project's id to which this image corresponds
	 */
	public int getProject() {
		return project;
	}

	/**Set's project's id to which this image corresponds to given parameter value
	 * @param project		project's id to which this image corresponds
	 */
	public void setProject(int project) {
		this.project = project;
	}
	
	/**Retrieves image's id
	 * @return id		Image's id
	 */
	public int getId() {
		return id;
	}

	/**Sets image's id to given parameter value
	 * @param id		Image's id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**Retrieves location of a PNG file that contains a single image for the project
	 * @return file		Location of a PNG file that contains a single image for the project
	 */
	public String getFile() {
		return file;
	}

	/**Sets location of a PNG file that contains a single image for the project to given parameter value
	 * @param file		Location of a PNG file that contains a single image for the project
	 */
	public void setFile(String file) {
		this.file = file;
	}
	
}