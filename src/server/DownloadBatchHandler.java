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

public class DownloadBatchHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("recordindexer"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		DownloadBatch_Params params = (DownloadBatch_Params)xmlStream.fromXML(exchange.getRequestBody());
		String user = params.getUser();
		String password = params.getPassword();
		int projectId = params.getProject();
	
		//DownloadBatch_Result variables needed
		String succeeded = null;
		String failed = null;
		int batch_id = -1;
		List<Field> fields;
		String image_url = null;
		int first_y_coord = -1;
		int record_height = -1;
		int num_records = -1;
		int num_fields = -1;
		
		try {
				succeeded = null;
				failed = null;
				batch_id = ServerFacade.getBatchId(projectId);
				fields = ServerFacade.getFields(projectId);
				image_url = ServerFacade.getImageUrl(batch_id);
				first_y_coord = ServerFacade.getFirstYCoord(projectId);
				record_height = ServerFacade.getRecordHeight(projectId);
				num_records = ServerFacade.getNumRecords(projectId);
				num_fields = ServerFacade.getNumFields(projectId);
		}
		catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		DownloadBatch_Result result;
		try {

			result = new DownloadBatch_Result();
			result.setSucceeded(succeeded);
			result.setFailed(failed);
			result.setBatch_id(batch_id);
			result.setProject_id(projectId);
			result.setFields(fields);
			result.setImage_url(image_url);
			result.setFirst_y_coord(first_y_coord);
			result.setRecord_height(record_height);
			result.setNum_records(num_records);
			result.setNum_fields(num_fields);
			result.setValid(ServerFacade.validateUser(user, password));
			if(ServerFacade.hasBatch(user, password))
				result.setValid(false);
			if(ServerFacade.batchChecked(batch_id))
				result.setValid(false);
			if(result.isValid())
				ServerFacade.updateCurrentBatch(user, password, batch_id);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			
		} catch (DatabaseException | ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exchange.getResponseBody().close();
	}
}
