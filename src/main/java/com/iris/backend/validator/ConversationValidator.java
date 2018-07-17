package com.iris.backend.validator;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.iris.backend.exceptions.InvalidRequestException;
import com.iris.backend.request.ConversationRequest;

@Service("conversationValidator")
public class ConversationValidator {
	
	
	public void validate(ConversationRequest req) {
		if(StringUtils.isEmpty(req.getMessage())) {
			throw new InvalidRequestException("Message is null");
		};
		
		if(StringUtils.isEmpty(req.getSender())) {
			throw new InvalidRequestException("Sender is null");
		};
		
		if(req.getSeq() == null) {
			throw new InvalidRequestException("seq is null");
		};
		
		
		if(req.getTimestamp() == null) {
			throw new InvalidRequestException("timestamp is null");
		};
	}

}
