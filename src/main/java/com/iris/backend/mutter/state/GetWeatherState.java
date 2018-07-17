package com.iris.backend.mutter.state;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.bot.BotResponseAttachment;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.state.IntentResponse;
import com.rabidgremlin.mutters.state.State;

import edu.emory.mathcs.backport.java.util.Arrays;

public class GetWeatherState extends State {

	public GetWeatherState() {
		super("getWeatherState");
	}

	@Override
	public IntentResponse execute(IntentMatch intentMatch, Session session) {
		String answer = "I am unable to get the weather report right now. But I hope it be a nice and charming day for you :) ";

		String uri = "http://api.openweathermap.org/data/2.5/weather?appid=1a2aace11122bb4eee7996d651729a66&q=";
		String cityName = "letterkenny";
		ArrayList<String> removedWords = new ArrayList<String>();
		removedWords.add("weather");
		removedWords.add("what's");
		removedWords.add("whats");
		removedWords.add("hows");
		removedWords.add("how's");
		removedWords.add("what");
		removedWords.add("is");
		removedWords.add("today");
		removedWords.add("now");
		removedWords.add("could");
		removedWords.add("please");
		removedWords.add("how");
		removedWords.add("city");
		removedWords.add("temperature");
		removedWords.add("temp");
		removedWords.add("now");
		removedWords.add("can");
		removedWords.add("you");
		removedWords.add("tell");
		removedWords.add("me");
		removedWords.add("the");
		removedWords.add("in");
		removedWords.add("?");
		removedWords.add("and");
		removedWords.add("could");
		removedWords.add("then");
		removedWords.add("like");

		String utterance = intentMatch.getUtterance();
		ArrayList<String> utteranceTokens = new ArrayList<String>(Arrays.asList(utterance.split("\\s+")));
		utteranceTokens.replaceAll(String::toUpperCase);
		for(String token: removedWords){
			if(utteranceTokens.contains(token.toUpperCase())){
				utteranceTokens.remove(token.toUpperCase());
			}
		}

		String listString = "";

		for (String s : utteranceTokens)
		{
			listString += s + " ";
		}

		cityName = listString.toLowerCase().trim();
		cityName = cityName.contains("?")?cityName.replace("?", ""):cityName;
		cityName = cityName.contains(".")?cityName.replace(".", ""):cityName;
		uri = uri + cityName.trim();

		try {
			RestTemplate restTemplate = new RestTemplate();
			String result = restTemplate.getForObject(uri, String.class);

			ObjectMapper mapper = new ObjectMapper();

			JsonNode actualObj = mapper.readTree(result);
			ArrayNode jsonNode1 = (ArrayNode) actualObj.get("weather");
			JsonNode jsonNode2 = actualObj.get("main");

			String description = jsonNode1.get(0).get("description").textValue();

			String temperature = jsonNode2.get("temp").toString();
			Double tempInCelsius = Double.parseDouble(temperature) - 273.15;
			double roundOff = Math.round(tempInCelsius * 100.0) / 100.0;
			String humidity = jsonNode2.get("humidity").toString();

			answer = "It seems to be "+description+ " at the moment in "+ cityName + ". The temperature is "+roundOff+" degrees. Humidity"
					+ " is close to "+humidity+".\n I wish I were human to feel it. Anyways, what else do you want to know from me? ";

		} catch (Exception e) {
			e.printStackTrace();
			answer = "I am unable to get the weather report right now. But I hope it be a nice and charming day for you :) \n"
					+ "I am still learning, Sorry for that. Could I help you with anything else?";
		}

		boolean sessionEnded = false;
		String reprompt = null;
		String hint = null;
		List<BotResponseAttachment> attachments = null;
		List<String> quickReplies = null;

		return new IntentResponse(sessionEnded, answer, reprompt, hint, attachments, quickReplies);
	}

}
