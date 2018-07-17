package com.iris.backend.mutter.matcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iris.backend.IrisContextAware;
import com.iris.backend.response.IntentResponse;
import com.iris.backend.service.IntentService;
import com.rabidgremlin.mutters.core.SlotMatcher;
import com.rabidgremlin.mutters.core.Tokenizer;
import com.rabidgremlin.mutters.core.ml.AbstractMachineLearningIntentMatcher;

import edu.emory.mathcs.backport.java.util.Arrays;


public class CustomIntentMatcher extends AbstractMachineLearningIntentMatcher {

	final static Logger logger = LoggerFactory.getLogger(CustomIntentMatcher.class);

	public CustomIntentMatcher(Tokenizer tokenizer, SlotMatcher slotMatcher, float minMatchScore,
			float maybeMatchScore) {
		super(tokenizer, slotMatcher, minMatchScore, maybeMatchScore);
	}

	@Override
	protected SortedMap<Double, Set<String>> generateSortedScoreMap(String[] utteranceTokens) {

		IntentService intentService = (IntentService)IrisContextAware.getContext().getBean("intentService");

		IntentResponse[] responses = intentService.getIntent(utteranceTokens[0]);

		@SuppressWarnings("unchecked")
		ArrayList<IntentResponse> resArr = new ArrayList<IntentResponse>(Arrays.asList(responses));

		SortedMap<Double, Set<String>> sortedMap = new TreeMap<Double, Set<String>>();

		Iterator<IntentResponse> it = resArr.iterator();

		while (it.hasNext()) {
			IntentResponse ir = it.next();
			TreeSet<String> ts = new TreeSet<String>();
			String intent = "generalQueryIntent";
			switch (ir.getType()) {
			case "FAQ":
				intent = "generalQueryIntent";
				break;
			case "PRU_QUOTE":
				intent = "askForQuoteIntent";
				break;
			case "ADVISOR":
				intent = "findAdvisorIntent";
				break;
			case "PRU_STOCK":
				intent = "stockPriceIntent";
				break;
			case "MARKET_TREND":
				intent = "marketTrendIntent";
				break;
			case "ACCOUNT_BALANCE":
				intent = "accountBalanceIntent";
				break;
			case "EXIT":
				intent = "exitIntent";
				break;
			case "WEATHER":
				intent = "weatherIntent";
				break;
			case "CLAIM":
				intent = "claimStatusIntent";
				break;	
			case "NO_INTENT":
				intent = "nullIntent";
			}

			ts.add(intent);

			sortedMap.put(ir.getProbability(), ts);
		}

		return sortedMap;
	}

}
