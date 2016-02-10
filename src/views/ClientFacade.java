package views;

import client.ClientException;
import client.communication.*;
import server.database.DatabaseException;
import shared.communication.*;


public class ClientFacade {
	
	private ClientCommunicator cc;
	
	public ClientFacade() {
		cc = new ClientCommunicator();
	}
	
	public ValidateUser_Result validateUser(String username, String password) {
		
		ValidateUser_Params params;
		try {
			params = new ValidateUser_Params();
			params.setUser(username);
			params.setPassword(password);
			ValidateUser_Result result = cc.validateUser(params);
			return result;
		} catch (DatabaseException | ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public GetProjects_Result getProjects(String username, String password) {
		
		GetProjects_Params params;
		try {
			params = new GetProjects_Params();
			params.setUser(username);
			params.setPassword(password);
			GetProjects_Result result = cc.getProjects(params);
			return result;
		} catch (DatabaseException | ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public GetSampleImage_Result getSampleImage(String username, String password, int project) {
		
		GetSampleImage_Params params;
		try {
			params = new GetSampleImage_Params();
			params.setUser(username);
			params.setPassword(password);
			params.setProject_id(project);
			GetSampleImage_Result result = cc.getSampleImage(params);
			result.setUrl_header(cc.getURL_PREFIX());
			return result;
		} catch (DatabaseException | ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public DownloadBatch_Result downloadBatch(String username, String password, int project) {
		
		DownloadBatch_Params params;
		try {
			params = new DownloadBatch_Params();
			params.setUser(username);
			params.setPassword(password);
			params.setProject(project);
			DownloadBatch_Result result = cc.downloadBatch(params);
			result.setUrl_header(cc.getURL_PREFIX());
			return result;
		} catch (DatabaseException | ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params) {
		
		try {
			SubmitBatch_Result result = cc.submitBatch(params);
			return result;
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
}