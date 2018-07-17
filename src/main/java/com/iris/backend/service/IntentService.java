package com.iris.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iris.backend.conf.ApplicationConf;
import com.iris.backend.response.IntentResponse;


@Service("intentService")
public class IntentService {

	@Autowired // Created automatically by Spring Cloud
	protected RestTemplate restTemplate;

	@Autowired
	protected ApplicationConf conf;

	final static Logger logger = LoggerFactory.getLogger(IntentService.class);

	public IntentResponse[] getIntent(String message) {

		IntentResponse[] person = new IntentResponse[1];
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		String responses = restTemplate.getForObject(conf.getIntentUrl() + "/question/" + message,
				String.class);

		/*IntentResponse resp = new IntentResponse();
		resp.setAnswer("CLAIM");
		resp.setProbability(50.0);
		resp.setQuestion(message);
		resp.setType("CLAIM"); 
		person[0] = resp;*/
		
		try {
			person = mapper.readValue(responses, IntentResponse[].class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return person;
	}

}
