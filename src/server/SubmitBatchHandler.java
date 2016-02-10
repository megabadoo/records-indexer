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

public class SubmitBatchHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("recordindexer"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		SubmitBatch_Params params = (SubmitBatch_Params)xmlStream.fromXML(exchange.getRequestBody());
		String user = params.getUser();
		String password = params.getPassword();
		int batch = params.getBatch();
		List<List <String>> field_values = params.getField_values();
	
		//Search_Result variables needed
		SubmitBatch_Result result;
		
		try {
				result = new SubmitBatch_Result();
				result.setSucceeded(ServerFacade.submitBatch(batch, field_values));
				
				int project = ServerFacade.getProjectId(batch);
				for(int x=0; x<field_values.size(); x++){
					if(field_values.get(x).size()!=ServerFacade.getNumFields(project))
						result.setSucceeded(false);
				}
				
				if(ServerFacade.getCurrentBatch(user, password)!=batch)
					result.setSucceeded(false);
				if(ServerFacade.batchIndexed(batch))
					result.setSucceeded(false);
					
				if(result.isSucceeded()){
					ServerFacade.updateCurrentBatch(user, password, -1);
					ServerFacade.updateIndexedRecords(user, password, ServerFacade.getNumRecords(project));
					ServerFacade.updateIndexedBatch(batch);
				}
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				xmlStream.toXML(result, exchange.getResponseBody());
		}
		catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exchange.getResponseBody().close();
	}
}
