package com.iris.backend.mutter.state;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iris.backend.IrisContextAware;
import com.iris.backend.service.IntentService;
import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.bot.BotResponseAttachment;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.state.IntentResponse;
import com.rabidgremlin.mutters.state.State;

import edu.emory.mathcs.backport.java.util.Arrays;

public class GeneralQueryState  extends State  {

	public GeneralQueryState() {
		super("generalQueryState");
	}

	@Override
	public IntentResponse execute(IntentMatch intentMatch, Session session) {
		String answer = "I am so Sorry, I do not have any information related to your query. Can I help you with something else?";

		IntentService intentService = (IntentService)IrisContextAware.getContext().getBean("intentService");
		String utterance = intentMatch.getUtterance();

		// This is to get answer of the question. Consider it as FAQ service - getAnswer
		com.iris.backend.response.IntentResponse[] responses = intentService.getIntent(utterance);
		@SuppressWarnings("unchecked")
		ArrayList<com.iris.backend.response.IntentResponse> resArr = new ArrayList<com.iris.backend.response.IntentResponse>(Arrays.asList(responses));

		Iterator<com.iris.backend.response.IntentResponse> it = resArr.iterator();

		while (it.hasNext()) {
			com.iris.backend.response.IntentResponse intenRes = it.next();
			if(!"FAQ".equalsIgnoreCase(intenRes.getAnswer()) && "FAQ".equalsIgnoreCase(intenRes.getType())) {
				boolean sessionEnded = false;
				String reprompt = null;
				String hint = null;
				List<BotResponseAttachment> attachments = null;
				List<String> quickReplies = null;
				answer = intenRes.getAnswer() + ". Anything else that you wanna know?";
				return new IntentResponse(sessionEnded, answer, reprompt, hint, attachments, quickReplies);	 
			}
			else{
				String uri = "https://www.prudential.com/prusearchservice/sitesearch/getresult?startIndex=0&endIndex=5&&inputSearchString=";
				uri = uri + utterance;

				RestTemplate restTemplate = new RestTemplate();
				String result = restTemplate.getForObject(uri, String.class);

				

				ObjectMapper mapper = new ObjectMapper();
				try {
					JsonNode actualObj = mapper.readTree(result);
					JsonNode jsonNode1 = actualObj.get("searchResponse");
					JsonNode jsonNode2 = jsonNode1.get("searchList");
					JsonNode jsonNode3 = jsonNode2.get(0);

					answer = "Sorry I do not have an exact answer to this right now. "
							+ "You may get some details on the page - "+jsonNode3.get("documentTitle").textValue() +". Click here -> " + jsonNode3.get("URL").textValue() +" for more info."
									+ "\nAnything else that you would like to ask?";

				} catch (Exception e) {
					e.printStackTrace();
					answer = "I am so Sorry, I do not have any information related to your query. Can I help you with something else?";
				}
				boolean sessionEnded = false;
				String reprompt = null;
				String hint = null;
				List<BotResponseAttachment> attachments = null;
				List<String> quickReplies = null;
				return new IntentResponse(sessionEnded, answer, reprompt, hint, attachments, quickReplies);	
			}
		}
		return null;
	}
}
