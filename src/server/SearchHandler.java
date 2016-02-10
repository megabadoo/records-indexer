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

public class SearchHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("recordindexer"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		Search_Params params = (Search_Params)xmlStream.fromXML(exchange.getRequestBody());
		String user = params.getUser();
		String password = params.getPassword();
		List<Integer> fields = params.getFields();
		List<String> search_values_with_duplicates = params.getSearch_values();
		
		List<String> search_values = new ArrayList<String>();
		if(search_values_with_duplicates.size()>0)
			search_values.add(search_values_with_duplicates.get(0));
		for(int x=1;x<search_values_with_duplicates.size();x++){
			if(!search_values.contains(search_values_with_duplicates.get(x)))
					search_values.add(search_values_with_duplicates.get(x));
			
		}
	
		//Search_Result variables needed
		List<Search_Result> results = new ArrayList<Search_Result>();
		
		try {
				
				Search_Result result;
				for(int i=0; i<fields.size(); i++){
					List<Value> matches = ServerFacade.search(fields.get(i), search_values);
					for(int j=0; j<matches.size(); j++){
							result = new Search_Result();
							result.setBatch_id(matches.get(j).getImage());
							result.setField_id(fields.get(i));
							result.setImage_url(matches.get(j).getImageurl());
							result.setRecord_num(matches.get(j).getRecordnum());
							result.setValid(ServerFacade.validateUser(user, password));
							results.add(result);
					}
					
				}
				
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				xmlStream.toXML(results, exchange.getResponseBody());
		}
		catch (ServerException | DatabaseException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		exchange.getResponseBody().close();
	}
}
