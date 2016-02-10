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

public class GetSampleImageHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("recordindexer"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		GetSampleImage_Params params = (GetSampleImage_Params)xmlStream.fromXML(exchange.getRequestBody());
		String user = params.getUser();
		String password = params.getPassword();
		int projectId = params.getProject_id();
	
		//GetSampleImage_Result variables needed
		int batch_id = -1;
		String image_url = null;
		
		try {
				batch_id = ServerFacade.getBatchId(projectId);
				image_url = ServerFacade.getImageUrl(batch_id);
		}
		catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		GetSampleImage_Result result;
		try {

			result = new GetSampleImage_Result();
			result.setImage_url(image_url);
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
