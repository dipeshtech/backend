package com.iris.backend.exceptions;

public class IntentServiceNotRespondingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -272519426838552315L;

	public IntentServiceNotRespondingException(String message) {
		super(message);
	}
}
