package shared.communication;

import server.database.DatabaseException;
import shared.model.*;
import java.util.*;

/**
 * @author meganarnell
 *
 */
public class SubmitBatch_Result {
	
	private boolean valid;

	/**
	 * Boolean for if batch was successfully submitted
	 */
	private boolean succeeded;
	
	/**Creates new SubmitBatch_Result
	 * @throws DatabaseException		throws DatabaseException if operation fails for any reason
	 */
	public SubmitBatch_Result() throws DatabaseException{
		succeeded = false;
	}

	
	
	public boolean isValid() {
		return valid;
	}



	public void setValid(boolean valid) {
		this.valid = valid;
	}



	/**Retrieves boolean for if batch was successfully submitted
	 * @return succeeded		Boolean for if batch was successfully submitted
	 */
	public boolean isSucceeded() {
		return succeeded;
	}

	/**Sets boolean for if batch was successfully submitted to given parameter value
	 * @param succeeded		Boolean for if batch was successfully submitted
	 */
	public void setSucceeded(boolean succeeded) {
		this.succeeded = succeeded;
	}
	
	
}
