package com.iris.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iris.backend.mutter.IrisStateMachineBot;
import com.iris.backend.mutter.IrisStateMachineBotConfiguration;
import com.iris.backend.request.ConversationRequest;
import com.iris.backend.response.ConversationResponse;
import com.iris.backend.validator.ConversationValidator;
import com.rabidgremlin.mutters.core.Context;
import com.rabidgremlin.mutters.core.bot.BotException;
import com.rabidgremlin.mutters.core.bot.BotResponse;
import com.rabidgremlin.mutters.core.session.Session;

@Service("conversationService")
public class ConversationService {

	private static IrisStateMachineBot irisBot;

	@Autowired
	private UserSessionsService userSessionsService;

	@Autowired
	ConversationValidator conversationValidator;

	public ConversationService() {
		IrisStateMachineBotConfiguration conf = new IrisStateMachineBotConfiguration();
		irisBot = new IrisStateMachineBot(conf);
	}

	public ConversationResponse getResponse(ConversationRequest req) {

		conversationValidator.validate(req);
		ConversationResponse res = new ConversationResponse();

		if(req.getMessage().equalsIgnoreCase("hi") || req.getMessage().equalsIgnoreCase("hi there") ||
				req.getMessage().equalsIgnoreCase("hello") || req.getMessage().equalsIgnoreCase("hey") ||
				req.getMessage().equalsIgnoreCase("hello iris") || req.getMessage().equalsIgnoreCase("hi iris") ||
				req.getMessage().equalsIgnoreCase("hi askiris") || req.getMessage().equalsIgnoreCase("hola") || 
				req.getMessage().equalsIgnoreCase("hey iris")){



			res.setMessage("Hi There! My name is IRIS (isn't it nice! My creators gave me this name). I am here to help you answer your queries, get you the status of your claims,"
					+ " tell you about prudential stock price, find you a financial advisor, inform you about current market trends, help you check your life insurance eligibility "
					+ "or provide you your account balance information.\n"
					+ "Hey, you know what, I can also tell you about current weather in your city. Try asking me out ! ");
			return res;
		}

		Session session = userSessionsService.getOrCreateSession(req.getSender());
		Context context = userSessionsService.getOrCreateContext(req.getSender());

		try {
			BotResponse botRes = irisBot.respond(session, context, req.getMessage());
			res.setMessage(botRes.getResponse());
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.setMessage("Umm...I apologise. Either I am not yet trained to answer that or I think I have had a lot of Guinness today. "
					+ "I am unable to answer that at the moment. "
					+ "Could you try asking something else Please !");
			return res;
		}
	}

}
