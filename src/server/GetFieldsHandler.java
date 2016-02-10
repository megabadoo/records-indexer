package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.*;
import java.util.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import shared.model.*;
import server.database.DatabaseException;
import server.facade.*;

public class GetFieldsHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("recordindexer"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		GetFields_Params params = (GetFields_Params)xmlStream.fromXML(exchange.getRequestBody());
		String user = params.getUser();
		String password = params.getPassword();
		int projectId = params.getProject();
	
		//GetFields_Result variables needed
		List<Field> fields;
		
		try {
				if(projectId!=-1){
					fields = ServerFacade.getFields(projectId);
				}
				else{
					fields = ServerFacade.getFields();
				}
		}
		catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		GetFields_Result result;
		try {

			result = new GetFields_Result();
			result.setFields(fields);
			result.setProject_id(projectId);
			result.setValidUser(ServerFacade.validateUser(user, password));
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			
		} catch (DatabaseException | ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exchange.getResponseBody().close();
	}
}
