package client.communication;

import java.io.*;
import java.net.*;
import shared.model.*;
import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import client.*;


/**
 * @author meganarnell
 *
 */
public class ClientCommunicator {

	private static String SERVER_HOST = "localhost";
	private static int SERVER_PORT = 8080;
	private static String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";

	private XStream xmlStream;

	
	/**
	 * Creates new ClientCommunicator
	 */
	public ClientCommunicator(String host, int port) {
		SERVER_HOST = host;
		SERVER_PORT = port;
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
		xmlStream = new XStream(new DomDriver());
		
	}
	
	public ClientCommunicator() {
		xmlStream = new XStream(new DomDriver());
		
	}

	/**Calls validateUser method on database
	 * @param params				ValidateUser_Params object
	 * @return						ValidateUser_Result object
	 * @throws ClientException		throws ClientException if operation fails for any reason
	 */
	public ValidateUser_Result validateUser(ValidateUser_Params params) throws ClientException {
		return (ValidateUser_Result)doPost("/ValidateUser", params);
	}
	
	/**Calls getProjects method on database
	 * @param params				GetProjects_Params object
	 * @return						GetProjects_Result object
	 * @throws ClientException		throws ClientException if operation fails for any reason
	 */
	public GetProjects_Result getProjects(GetProjects_Params params) throws ClientException {
		return (GetProjects_Result)doPost("/GetProjects", params);
	}
	
	/**Calls getSampleImage method on database
	 * @param params				GetSampleImage_Params object
	 * @return						GetSampleImage_Result object
	 * @throws ClientException		throws ClientException if operation fails for any reason
	 */
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params) throws ClientException {
		return (GetSampleImage_Result)doPost("/GetSampleImage", params);
	}
	
	/**Calls downloadBatch method on database
	 * @param params				DownloadBatch_Params object
	 * @return						DownloadBatch_Result object
	 * @throws ClientException		throws ClientException if operation fails for any reason
	 */
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params) throws ClientException {
		return (DownloadBatch_Result)doPost("/DownloadBatch", params);
	}
	
	/**Calls submitBatch method on database
	 * @param params				SubmitBatch_Params object
	 * @return						SubmitBatch_Result object
	 * @throws ClientException		throws ClientException if operation fails for any reason
	 */
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params) throws ClientException {
		return (SubmitBatch_Result)doPost("/SubmitBatch", params);
	}
	
	/**Calls getFields method on database
	 * @param params				GetFields_Params object
	 * @return						GetFields_Result object
	 * @throws ClientException		throws ClientException if operation fails for any reason
	 */
	public GetFields_Result getFields(GetFields_Params params) throws ClientException {
		return (GetFields_Result)doPost("/GetFields", params);
	}
	
	/**Calls search method on database
	 * @param params				Search_Params object
	 * @return						Search_Result object
	 * @throws ClientException		throws ClientException if operation fails for any reason
	 */
	@SuppressWarnings("unchecked")
	public List<Search_Result> search(Search_Params params) throws ClientException {
		return (List<Search_Result>)doPost("/Search", params);
	}
	
	/**Calls downloadFile method on database
	 * @param params				DownloadFile_Params object
	 * @return						DownloadFile_Result object
	 * @throws ClientException		throws ClientException if operation fails for any reason
	 */
	public DownloadFile_Result downloadFile(DownloadFile_Params params) throws ClientException {
		return (DownloadFile_Result)doPost("/", params);
	}
	
	private Object doPost(String urlPath, Object postData) throws ClientException {
		try {
			XStream xstream = new XStream(new DomDriver());
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.connect();
			xstream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				Object result = xstream.fromXML(connection.getInputStream());
				return result;
			}
			else {
				throw new ClientException(String.format("doPost failed: %s (http code %d)",
						urlPath, connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new ClientException(String.format("doPost failed: %s", e.getMessage()), e);
		}
	}
}
