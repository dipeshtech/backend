package com.iris.backend.exceptions;

public class InvalidRequestException extends RuntimeException{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1891709875929033198L;

	
	public InvalidRequestException(String message) {
		super(message);
	}
}
