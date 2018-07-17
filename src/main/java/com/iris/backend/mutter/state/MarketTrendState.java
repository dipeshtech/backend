package com.iris.backend.mutter.state;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.bot.BotResponseAttachment;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.state.IntentResponse;
import com.rabidgremlin.mutters.state.State;

public class MarketTrendState extends State {

	public MarketTrendState() {
		super("marketTrendState");
	}

	@Override
	public IntentResponse execute(IntentMatch intentMatch, Session session) {

		String uri = "https://www.alphavantage.co/query?function=SECTOR&apikey=A7RMMBAZ63KUIH8W";

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);

		String answer = "";

		ObjectMapper mapper = new ObjectMapper();
		try {

			JsonNode actualObj = mapper.readTree(result);
			JsonNode jsonNode1 = actualObj.get("Rank A: Real-Time Performance");

			answer = "Energy Sector is " + jsonNode1.get("Energy").textValue() +
					". Utilities at "+jsonNode1.get("Utilities").textValue() +
					". Real Estate at "+jsonNode1.get("Real Estate").textValue() +
					". Consumer Staples at "+jsonNode1.get("Consumer Staples").textValue() +
					". Health Care at "+jsonNode1.get("Health Care").textValue() +
					". Materials at "+jsonNode1.get("Materials").textValue() +
					". Telecommunication Services at "+jsonNode1.get("Telecommunication Services").textValue() + 
					". Industrials at "+jsonNode1.get("Industrials").textValue() +
					". Information Technology at "+jsonNode1.get("Information Technology").textValue() +
					". Consumer Discretionary at "+jsonNode1.get("Consumer Discretionary").textValue() +
					". Financials at "+jsonNode1.get("Financials").textValue() + "\nWhat else do you want to know?";
		} catch (Exception e) {
			e.printStackTrace();
			result = "I am unable to retrieve this information right now. There is some problem at my end.\nTry asking something else!";
		}

		boolean sessionEnded = false;
		String reprompt = null;
		String hint = null;
		List<BotResponseAttachment> attachments = null;
		List<String> quickReplies = null;

		return new IntentResponse(sessionEnded, answer, reprompt, hint, attachments, quickReplies);
	}

}
