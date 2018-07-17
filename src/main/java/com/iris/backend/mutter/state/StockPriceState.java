package com.iris.backend.mutter.state;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.bot.BotResponseAttachment;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.state.IntentResponse;
import com.rabidgremlin.mutters.state.State;

public class StockPriceState extends State {

	public StockPriceState() {
		super("stockPriceState");
	}

	@Override
	public IntentResponse execute(IntentMatch intentMatch, Session session) {

		String uri = "https://www.alphavantage.co/query?apikey=A7RMMBAZ63KUIH8W&function=TIME_SERIES_DAILY&outputsize=compact&symbol=PRU";

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);

		String answer = "I am somehow unable to retrieve stock price details right now. But I will be able to help you with your other queries.";

		ObjectMapper mapper = new ObjectMapper();
		try {
			Date date = new Date();
			String today = new SimpleDateFormat("yyyy-MM-dd").format(date);
			String yday = new SimpleDateFormat("yyyy-MM-dd").format(yesterday(1));
			String dayBeforeYday = new SimpleDateFormat("yyyy-MM-dd").format(yesterday(2));

			JsonNode actualObj = mapper.readTree(result);
			JsonNode jsonNode1 = actualObj.get("Time Series (Daily)");
			JsonNode jsonNode2 = jsonNode1.get(today);
			JsonNode jsonNode3 = jsonNode1.get(yday);
			JsonNode jsonNode4 = jsonNode1.get(dayBeforeYday);
			if(jsonNode2!=null){
				answer = "Today PRU stock opened at " + jsonNode2.get("1. open").textValue() + " and closed at "+jsonNode2.get("4. close").textValue();
				answer = answer + " It saw an intraday high of "+jsonNode2.get("2. high").textValue() + " and an intraday low of "+ jsonNode2.get("3. low").textValue();
				answer = answer + ". Total volume traded is "+jsonNode2.get("5. volume").textValue()+"\n"
						+ "How else can I help you?";
			}
			else if(jsonNode3!=null){
				answer = "I dont have the trading info for today as of now, but Yesterday PRU stock opened at " + jsonNode3.get("1. open").textValue() + " and closed at "+jsonNode3.get("4. close").textValue();
				answer = answer + " It saw an intraday high of "+jsonNode3.get("2. high").textValue() + " and an intraday low of "+ jsonNode3.get("3. low").textValue();
				answer = answer + ". Total volume traded is "+jsonNode3.get("5. volume").textValue()+"\n"
						+ "How else can I help you?";
			}
			else if(jsonNode4!=null){
				answer = "On friday, before weekend, PRU stock opened at " + jsonNode4.get("1. open").textValue() + " and closed at "+jsonNode4.get("4. close").textValue();
				answer = answer + " It saw an intraday high of "+jsonNode4.get("2. high").textValue() + " and an intraday low of "+ jsonNode4.get("3. low").textValue();
				answer = answer + ". Total volume traded is "+jsonNode4.get("5. volume").textValue()+"\n"
						+ "How else can I help you?";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean sessionEnded = false;
		String reprompt = null;
		String hint = null;
		List<BotResponseAttachment> attachments = null;
		List<String> quickReplies = null;

		return new IntentResponse(sessionEnded, answer, reprompt, hint, attachments, quickReplies);
	}
	private Date yesterday(int days) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -days);
		return cal.getTime();
	}

}
