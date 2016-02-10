package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import server.database.DatabaseException;
import server.facade.*;

public class ValidateUserHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("contactmanager"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		ValidateUser_Params params = (ValidateUser_Params)xmlStream.fromXML(exchange.getRequestBody());
		String user = params.getUser();
		String password = params.getPassword();
		String firstName = "";
		String lastName = "";
		int numRecords = -1;
		boolean isValid = false;
		
		try {
			isValid = ServerFacade.validateUser(user, password);
			if(isValid){
				firstName = ServerFacade.getFirstName(user, password);
				lastName = ServerFacade.getLastName(user, password);
				numRecords = ServerFacade.getNumRecords(user, password);
			}
		}
		catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		ValidateUser_Result result;
		try {

			result = new ValidateUser_Result();
			if(isValid){
				result.setNum_records(numRecords);
				result.setUser_first_name(firstName);
				result.setUser_last_name(lastName);
				result.setValid(isValid);
			}
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exchange.getResponseBody().close();
	}
}
