package com.miracle.backlog.exception;

import com.miracle.cognitive.exception.CognitiveErrorCodes;
import com.miracle.cognitive.exception.CognitiveServiceException;

public class BacklogException extends CognitiveServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4633876870673211026L;

	public BacklogException(Throwable cause, String errorCode, String errorDescription) {
		super(cause, errorCode, errorDescription);
		if (getErrorCode().equalsIgnoreCase(CognitiveErrorCodes.UNKOWN_ERROR)) {
			setErrorCode(getBacklogErrorCode(cause));
		}
	}

	public BacklogException(Throwable cause, String errorDescription) {
		super(cause, errorDescription);
		if (getErrorCode().equalsIgnoreCase(CognitiveErrorCodes.UNKOWN_ERROR)) {
			setErrorCode(getBacklogErrorCode(cause));
		}
	}

	public String getBacklogErrorCode(Throwable cause) {
		String errorCode = null;
		// Check exception instance
		return errorCode;
	}
}
