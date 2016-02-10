package server.importer;

import org.apache.commons.io.*;

import java.io.*;
import javax.xml.parsers.*;
import java.util.*;

import org.w3c.dom.*;

public class DataImporter {

	public static void main(String[] args) throws Exception {

		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		File file = new File(args[0]);
		File dest = new File("Records");
		
		if(!file.getParentFile().getCanonicalPath().equals(dest.getCanonicalPath()))
			FileUtils.deleteDirectory(dest);
			
		//	Copy the directories (recursively) from our source to our destination.
		FileUtils.copyDirectory(file.getParentFile(), dest);

		Document doc = builder.parse(file);
		doc.getDocumentElement().normalize();
		
		Element root = doc.getDocumentElement();

		IndexerData indexerData = new IndexerData(root);
		
		
	}		

	public static ArrayList<Element> getChildElements(Node node) {
		ArrayList<Element> result = new ArrayList<Element>();

		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeType() == Node.ELEMENT_NODE){
				result.add((Element)child);
			}
		}

		return result;
	}
	
	public static String getValue(Element element) {
		String result = "";
		Node child = element.getFirstChild();
		result = child.getNodeValue();
		return result;
	}
	
}