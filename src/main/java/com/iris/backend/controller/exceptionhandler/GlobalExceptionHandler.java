package com.iris.backend.controller.exceptionhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.iris.backend.exceptions.InvalidRequestException;
import com.iris.backend.response.ConversationResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidRequestException.class) 
	public ConversationResponse handleException(final InvalidRequestException ire) {
		
		return new ConversationResponse().setMessage(ire.getMessage());
		
	}
	
}
