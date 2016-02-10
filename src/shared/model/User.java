package shared.model;

import org.w3c.dom.Element;

import server.importer.DataImporter;

/**
 * @author meganarnell
 *
 */
public class User {

	/**
	 * The user's id number
	 */
	private int id;
	
	/**
	 * The user's name
	 */
	private String name;
	
	/**
	 * The user's password
	 */
	private String password;
	
	/**
	 * The user's first name
	 */
	private String firstname;
	
	/**
	 * The user's last name
	 */
	private String lastname;
	
	/**
	 * The user's email
	 */
	private String email;
	
	/**
	 * The number of records that the user has indexed
	 */
	private int indexedrecords;
	
	/**
	 * The id of the current batch checked out
	 */
	private int currentbatch;
	
	/**
	 * Creates a user with default field values
	 */
	public User() {
		setId(-1);
		setName(null);
		setPassword(null);
		setFirstname(null);
		setLastname(null);
		setEmail(null);
		setIndexedrecords(0);
		setCurrentbatch(0);
	}
	
	/**Creates a user with given field values
	 * @param id						The user's id number
	 * @param name						The user's name
	 * @param password					The user's password
	 * @param firstname					The user's first name
	 * @param lastname					The user's last name
	 * @param email						The user's email
	 * @param indexedrecords			The number of records that the user has indexed
	 * @param currentbatch				The id of the batch currently checked out
	 */	
	public User(String name, String password, String firstname, String lastname, String email, int indexedrecords, int currentbatch) {
		setName(name);
		setPassword(password);
		setFirstname(firstname);
		setLastname(lastname);
		setEmail(email);
		setIndexedrecords(indexedrecords);
		setCurrentbatch(currentbatch);
	}
	
	public User(Element projectElement) {
        name = DataImporter.getValue((Element)projectElement.getElementsByTagName("username").item(0));
        password = DataImporter.getValue((Element)projectElement.getElementsByTagName("password").item(0));
        firstname = DataImporter.getValue((Element)projectElement.getElementsByTagName("firstname").item(0));
        lastname = DataImporter.getValue((Element)projectElement.getElementsByTagName("lastname").item(0));
        email = DataImporter.getValue((Element)projectElement.getElementsByTagName("username").item(0));
        indexedrecords = Integer.parseInt(DataImporter.getValue((Element)projectElement.getElementsByTagName("indexedrecords").item(0)));
        currentbatch = -1;
    }

	
	/**Retrieves the id of the batch currently checked out
	 * @return	currentbatch		The id of the batch currently checked out
	 */
	public int getCurrentbatch() {
		return currentbatch;
	}

	/**Sets the id of the batch currently checked out to the given value
	 * @param currentbatch		The id of the batch currently checked out
	 */
	public void setCurrentbatch(int currentbatch) {
		this.currentbatch = currentbatch;
	}

	/**Retrieves user's id number
	 * @return	id		User's id number
	 */
	public int getId() {
		return id;
	}

	/**Sets a user's id number to the given value
	 * @param id		User's id number
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**Retrieves user's name
	 * @return	name		User's name
	 */
	public String getName() {
		return name;
	}

	/**Sets a user's name to the given value
	 * @param name			User's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**Retrieves user's password
	 * @return password		User's password
	 */
	public String getPassword() {
		return password;
	}

	/**Sets a user's password to the given value
	 * @param password			User's password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**Retrieves user's first name
	 * @return firstname		User's first name		
	 */
	public String getFirstname() {
		return firstname;
	}

	/**Sets a user's first name to the given value
	 * @param firstname			User's first name
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**Retrieves user's last name
	 * @return lastname		User's last name
	 */
	public String getLastname() {
		return lastname;
	}

	/**Sets a user's last name to the given value
	 * @param lastname			User's last name
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**Retrieves user's email address
	 * @return email		User's email address
	 */
	public String getEmail() {
		return email;
	}

	/**Sets a user's email address to the given value
	 * @param email			User's email address
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**Retrieves number of records indexed by user
	 * @return indexedrecords		Number of records indexed by user
	 */
	public int getIndexedrecords() {
		return indexedrecords;
	}

	/**Sets the number of records indexed by user to the given value
	 * @param indexedrecords			Number of records indexed by user
	 */
	public void setIndexedrecords(int indexedrecords) {
		this.indexedrecords = indexedrecords;
	}

}