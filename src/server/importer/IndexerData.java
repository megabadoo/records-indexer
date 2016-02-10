package server.importer;

import java.util.*;
import org.w3c.dom.*;
import server.database.*;
import shared.model.*;

public class IndexerData {

	private Database db;
	private UsersDAO dbUsers;
	private FieldsDAO dbFields;
	private ProjectsDAO dbProjects;
	private RecordsDAO dbRecords;
	private ValuesDAO dbValues;
	private ImagesDAO dbImages;

	public IndexerData(Element root) throws Exception {
		
		// Load database driver	
		Database.initialize();
		
		ArrayList<Element> rootElements = DataImporter.getChildElements(root);
		
		// Delete all users from the database	
		db = new Database();		
		db.startTransaction();
		
		db.getUsersDAO().dropTable();
		db.getUsersDAO().createTable();
		List<User> users = db.getUsersDAO().getAll();

		dbUsers = db.getUsersDAO();
		
	
	 
		ArrayList<Element> userElements = DataImporter.getChildElements(rootElements.get(0));
		for(Element userElement : userElements) {
			User user = new User(userElement);
			users.add(user);
			dbUsers.add(user);
		}
		
		db.getProjectsDAO().dropTable();
		db.getProjectsDAO().createTable();
		List<Project> projects = db.getProjectsDAO().getAll();
		
		db.getFieldsDAO().dropTable();
		db.getFieldsDAO().createTable();
		List<Field> fields = db.getFieldsDAO().getAll();
		
		db.getImagesDAO().dropTable();
		db.getImagesDAO().createTable();
		List<Image> images = db.getImagesDAO().getAll();
		
		db.getRecordsDAO().dropTable();
		db.getRecordsDAO().createTable();
		List<Record> records = db.getRecordsDAO().getAll();
		
		db.getValuesDAO().dropTable();
		db.getValuesDAO().createTable();
		List<Value> values = db.getValuesDAO().getAll();
	
		dbProjects = db.getProjectsDAO();
		dbFields = db.getFieldsDAO();
		dbImages = db.getImagesDAO();
		dbRecords = db.getRecordsDAO();
		dbValues = db.getValuesDAO();
		ArrayList<Element> projectElements = DataImporter.getChildElements(rootElements.get(1));
		for(Element projectElement : projectElements) {
			Project project = new Project(projectElement);
			project.setNumfields(0);
			projects.add(project);
			dbProjects.add(project);
			
			 Element fieldsElement = (Element)projectElement.getElementsByTagName("fields").item(0);
		     NodeList fieldElements = fieldsElement.getElementsByTagName("field");
		     for(int i = 0; i < fieldElements.getLength(); i++) {
		    	 Field field = new Field((Element)fieldElements.item(i));
		    	 field.setProject(project.getId());
		    	 field.setColumnnum(i+1);
		    	 project.setNumfields(project.getNumfields()+1);
		    	 fields.add(field);
		    	 dbFields.add(field);
		    	 dbProjects.update(project);
		     }

		     
		     Element imagesElement = (Element)projectElement.getElementsByTagName("images").item(0);
		     NodeList imageElements = imagesElement.getElementsByTagName("image");
		     for(int i = 0; i < imageElements.getLength(); i++) {
		    	 Image image = new Image((Element)imageElements.item(i));
				image.setFile(DataImporter.getValue((Element)projectElement.getElementsByTagName("file").item(i)));
				image.setProject(project.getId());
				images.add(image);
				dbImages.add(image);
				
				Element imageElement = (Element)imageElements.item(i);
				Element recordsElement = (Element) imageElement.getElementsByTagName("records").item(0);
				if(recordsElement!=null){
					NodeList recordElements = recordsElement.getElementsByTagName("record");
					for(int j = 0; j < recordElements.getLength(); j++) {
						Record record = new Record((Element)recordElements.item(j));
						record.setImage(image.getId());
						record.setRecordnum(j+1);
						records.add(record);
						dbRecords.add(record);
						
						Element recordElement = (Element)recordElements.item(j);
						Element valuesElement = (Element) recordElement.getElementsByTagName("values").item(0);
							NodeList valueElements = valuesElement.getElementsByTagName("value");
							for(int k = 0; k < valueElements.getLength(); k++) {
								Value value = new Value((Element)valueElements.item(k));
								value.setValue(DataImporter.getValue((Element)recordElement.getElementsByTagName("value").item(k)));
						    	value.setRecord(record.getId());
						    	value.setImage(image.getId());
						    	value.setImageurl(image.getFile());
						    	value.setColumnnum(k+1);
						    	value.setRecordnum(record.getRecordnum());
						    	value.setProject(project.getId());
						    	values.add(value);
								dbValues.add(value);
								
							}
					}
				}
		     }
		     
		}
		
		
		db.endTransaction(true);
	}
	
}
