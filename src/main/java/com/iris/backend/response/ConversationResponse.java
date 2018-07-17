package com.iris.backend.response;

/**
 * This class is representation of the response sent from bot to the frontend.
 * 
 * @author Atley Virdee
 * @version 1.0.0
 */
public class ConversationResponse {
	
	/**
	 * Actual reply from the bot
	 */
	private String message;
	
	/**
	 * error details if any
	 */
	private Error error;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public ConversationResponse setMessage(String message) {
		this.message = message;
		return this;
	}
	
	
	/**
	 * @return the error
	 */
	public Error getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public ConversationResponse setError(Error error) {
		this.error = error;
		return this;
	}


	/**
	 * Sub Object of error for error handling
	 * 
	 * @author Atley Virdee
	 * @version 1.0.0
	 */
	public static class Error{
		
		/**
		 * Error code
		 */
		private String errorCode;
		
		/**
		 * User understandable message
		 */
		private String message;

		/**
		 * @return the errorCode
		 */
		public String getErrorCode() {
			return errorCode;
		}

		/**
		 * @param errorCode the errorCode to set
		 */
		public Error setErrorCode(String errorCode) {
			this.errorCode = errorCode;
			return this;
		}

		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * @param message the message to set
		 */
		public Error setMessage(String message) {
			this.message = message;
			return this;
		}
		
	}

}
