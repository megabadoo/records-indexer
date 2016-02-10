package servertester.controllers;

import java.util.*;

import client.ClientException;
import client.communication.ClientCommunicator;
import server.database.DatabaseException;
import servertester.views.*;
import shared.communication.*;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		try {
			//new clientcommunicator
			
			String host = getView().getHost();
			String port = getView().getPort();
			ClientCommunicator cc = new ClientCommunicator(host, Integer.valueOf(port));		
			ValidateUser_Params params = new ValidateUser_Params();
			params.setUser(getView().getParameterValues()[0]);
			params.setPassword(getView().getParameterValues()[1]);
			ValidateUser_Result result = cc.validateUser(params);
			result.setUrlHeader("http://" + host + ":" + port);
			if(result.isValid())
				getView().setResponse(result.toString());
			else
				getView().setResponse("FALSE\n");
		} catch (ClientException e) {
			getView().setResponse("FAILED");
			e.printStackTrace();
		} catch (DatabaseException e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}catch (Exception e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
		
		
	}
	
	private void getProjects() {
		
		try {
			//new clientcommunicator
			//ClientCommunicator cc = new ClientCommunicator();		
			String host = getView().getHost();
			String port = getView().getPort();
			ClientCommunicator cc = new ClientCommunicator(host, Integer.valueOf(port));
			GetProjects_Params params = new GetProjects_Params();
			params.setUser(getView().getParameterValues()[0]);
			params.setPassword(getView().getParameterValues()[1]);
			GetProjects_Result result = cc.getProjects(params);
			result.setUrl_header("http://" + host + ":" + port + "/");
			if(result.isValidUser())
				getView().setResponse(result.toString());
			else
				getView().setResponse("FAILED");
		} catch (ClientException e) {
			getView().setResponse("FAILED");
			e.printStackTrace();
		} catch (DatabaseException e) {
			getView().setResponse("FAILED");
			e.printStackTrace();
		}catch (Exception e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
		
	}
	
	private void getSampleImage() {
		try {
			//new clientcommunicator
			//ClientCommunicator cc = new ClientCommunicator();		
			String host = getView().getHost();
			String port = getView().getPort();
			ClientCommunicator cc = new ClientCommunicator(host, Integer.valueOf(port));
			GetSampleImage_Params params = new GetSampleImage_Params();
			params.setUser(getView().getParameterValues()[0]);
			params.setPassword(getView().getParameterValues()[1]);
			params.setProject_id(Integer.valueOf(getView().getParameterValues()[2]));
			GetSampleImage_Result result = cc.getSampleImage(params);
			result.setUrl_header("http://" + host + ":" + port + "/");
			if(result.isValidUser())
				getView().setResponse(result.toString());
			else
				getView().setResponse("FAILED");
		} catch (ClientException e) {
			getView().setResponse("FAILED");
			e.printStackTrace();
		} catch (DatabaseException e) {
			getView().setResponse("FAILED");
			e.printStackTrace();
		}catch (Exception e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
	}
	
	private void downloadBatch() {
		try {
			//new clientcommunicator
			//ClientCommunicator cc = new ClientCommunicator();		
			String host = getView().getHost();
			String port = getView().getPort();
			ClientCommunicator cc = new ClientCommunicator(host, Integer.valueOf(port));
			DownloadBatch_Params params = new DownloadBatch_Params();
			params.setUser(getView().getParameterValues()[0]);
			params.setPassword(getView().getParameterValues()[1]);
			params.setProject(Integer.valueOf(getView().getParameterValues()[2]));
			DownloadBatch_Result result = cc.downloadBatch(params);
			result.setUrl_header("http://" + host + ":" + port + "/");
			if(result.isValid())
				getView().setResponse(result.toString());
			else
				getView().setResponse("FAILED");
		} catch (ClientException e) {
			getView().setResponse("FAILED");
			e.printStackTrace();
		} catch (DatabaseException e) {
			getView().setResponse("FAILED");
			e.printStackTrace();
		}catch (Exception e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
	}
	
	private void getFields() {
		try {
			//new clientcommunicator
			//ClientCommunicator cc = new ClientCommunicator();		
			String host = getView().getHost();
			String port = getView().getPort();
			ClientCommunicator cc = new ClientCommunicator(host, Integer.valueOf(port));
			GetFields_Params params = new GetFields_Params();
			params.setUser(getView().getParameterValues()[0]);
			params.setPassword(getView().getParameterValues()[1]);
			if(getView().getParameterValues()[2].equals(""))
				params.setProject(-1);
			else
				params.setProject(Integer.valueOf(getView().getParameterValues()[2]));
			GetFields_Result result = cc.getFields(params);
			result.setUrl_header("http://" + host + ":" + port + "/");
			if(result.isValidUser() && !result.toString().equals(""))
				getView().setResponse(result.toString());
			else
				getView().setResponse("FAILED");
		} catch (ClientException e) {
			getView().setResponse("FAILED");
			e.printStackTrace();
		} catch (DatabaseException e) {
			getView().setResponse("FAILED");
			e.printStackTrace();
		} catch (Exception e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
	}
	
	private void submitBatch() {
		try {
			//new clientcommunicator
			//ClientCommunicator cc = new ClientCommunicator();		
			String host = getView().getHost();
			String port = getView().getPort();
			ClientCommunicator cc = new ClientCommunicator(host, Integer.valueOf(port));
			SubmitBatch_Params params = new SubmitBatch_Params();
			params.setUser(getView().getParameterValues()[0]);
			params.setPassword(getView().getParameterValues()[1]);
			params.setBatch(Integer.valueOf(getView().getParameterValues()[2]));
			String str = getView().getParameterValues()[3];
			List<List<String>> parsed_record_values = new ArrayList<List<String>>();
			List<String> parsed_field_values;
			String[] recordValues = str.split(";", -1);
			for(int x=0; x<recordValues.length; x++) {
				String[] fieldValues = recordValues[x].split(",", -1);
				parsed_field_values = new ArrayList<String>();
				for(int y=0; y<fieldValues.length; y++) {
					parsed_field_values.add(fieldValues[y]);
				}
				parsed_record_values.add(parsed_field_values);
			}
			params.setField_values(parsed_record_values);
			SubmitBatch_Result result = cc.submitBatch(params);
			if(result.isSucceeded())
				getView().setResponse("TRUE\n");
			else
				getView().setResponse("FAILED\n");
		} catch (ClientException e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		} catch (DatabaseException e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		} catch (Exception e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
	}
	
	private void search() {
		try {
			//new clientcommunicator
			//ClientCommunicator cc = new ClientCommunicator();		
			String host = getView().getHost();
			String port = getView().getPort();
			ClientCommunicator cc = new ClientCommunicator(host, Integer.valueOf(port));
			Search_Params params = new Search_Params();
			params.setUser(getView().getParameterValues()[0]);
			params.setPassword(getView().getParameterValues()[1]);
			String fieldStr = getView().getParameterValues()[2];
			String searchStr = getView().getParameterValues()[3];
			List<Integer> parsed_field_ids = new ArrayList<Integer>();
			String[] splitFieldIds = fieldStr.split(",", -1);
			for(int x=0; x<splitFieldIds.length; x++) {
				parsed_field_ids.add(Integer.valueOf(splitFieldIds[x]));
			}
			params.setFields(parsed_field_ids);
			List<String> parsed_search_values = new ArrayList<String>();
			String[] splitSearchValues = searchStr.split(",", -1);
			for(int x=0; x<splitSearchValues.length; x++) {
				parsed_search_values.add(splitSearchValues[x]);
			}
			params.setSearch_values(parsed_search_values);
			List<Search_Result> result = cc.search(params);
			
			
			List<String> results_no_duplicates = new ArrayList<String>();
			StringBuilder response = new StringBuilder();
			for(int x=0; x<result.size(); x++) {
				result.get(x).setUrl_header("http://" + host + ":" + port + "/");
				if(!results_no_duplicates.contains(result.get(x).toString())){
					results_no_duplicates.add(result.get(x).toString());
					response.append(result.get(x).toString());
				}
			}
			if(result.size()>0 && result.get(0).isValid())
					getView().setResponse(response.toString());
			else
				getView().setResponse("FAILED\n");
		} catch (ClientException e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		} catch (DatabaseException e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}catch (Exception e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}

	}

}

