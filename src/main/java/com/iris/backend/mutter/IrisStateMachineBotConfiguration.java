package com.iris.backend.mutter;

import com.iris.backend.mutter.guard.DontHaveAccTypeGuard;
import com.iris.backend.mutter.guard.DontHaveQuoteDetailsGuard;
import com.iris.backend.mutter.guard.HaveAccTypeGuard;
import com.iris.backend.mutter.guard.HaveClaimIdGuard;
import com.iris.backend.mutter.guard.HaveQuoteDetailGuard;
import com.iris.backend.mutter.intent.AccountBalanceIntent;
import com.iris.backend.mutter.intent.AskForQuoteIntent;
import com.iris.backend.mutter.intent.ClaimStatusIntent;
import com.iris.backend.mutter.intent.ExitIntent;
import com.iris.backend.mutter.intent.FindAdvisorIntent;
import com.iris.backend.mutter.intent.GeneralQueryIntent;
import com.iris.backend.mutter.intent.GetAccTypeIntent;
import com.iris.backend.mutter.intent.GetClaimIdIntent;
import com.iris.backend.mutter.intent.MarketTrendIntent;
import com.iris.backend.mutter.intent.StockPriceIntent;
import com.iris.backend.mutter.intent.WeatherIntent;
import com.iris.backend.mutter.matcher.CustomIntentMatcher;
import com.iris.backend.mutter.matcher.CustomSlotMatcher;
import com.iris.backend.mutter.state.AskForQuoteState;
import com.iris.backend.mutter.state.ExitState;
import com.iris.backend.mutter.state.FindAdvisorState;
import com.iris.backend.mutter.state.GeneralQueryState;
import com.iris.backend.mutter.state.GetAccTypeState;
import com.iris.backend.mutter.state.GetAccountBalanceState;
import com.iris.backend.mutter.state.GetClaimIdState;
import com.iris.backend.mutter.state.GetClaimStatus;
import com.iris.backend.mutter.state.GetQuoteState;
import com.iris.backend.mutter.state.GetWeatherState;
import com.iris.backend.mutter.state.MarketTrendState;
import com.iris.backend.mutter.state.StartState;
import com.iris.backend.mutter.state.StockPriceState;
import com.iris.backend.mutter.tokenizer.KeywordTokenizer;
import com.rabidgremlin.mutters.core.Intent;
import com.rabidgremlin.mutters.core.IntentMatcher;
import com.rabidgremlin.mutters.slots.AccTypeSlot;
import com.rabidgremlin.mutters.slots.AlphaNumericSlot;
import com.rabidgremlin.mutters.slots.BooleanLiteralSlot;
import com.rabidgremlin.mutters.slots.CustomNumberSlot;
import com.rabidgremlin.mutters.slots.IpinSlot;
import com.rabidgremlin.mutters.slots.LiteralSlot;
import com.rabidgremlin.mutters.state.Guard;
import com.rabidgremlin.mutters.state.State;
import com.rabidgremlin.mutters.state.StateMachine;

public class IrisStateMachineBotConfiguration implements StateMachineBotConfiguration {

	@Override
	public String getDefaultResponse() {
		return "Sorry, could not get you. Try Again.";
	}

	@Override
	public IntentMatcher getIntentMatcher() {

		KeywordTokenizer tokenizer = new KeywordTokenizer();

		CustomSlotMatcher slotMatcher = new CustomSlotMatcher();

		CustomIntentMatcher matcher = new CustomIntentMatcher(tokenizer, slotMatcher, 0.000000009f, 0.000000007f);

		Intent findAdvisorIntent = new FindAdvisorIntent();

		Intent askForQuoteIntent = new AskForQuoteIntent();
		askForQuoteIntent.addSlot(new CustomNumberSlot("age"));
		askForQuoteIntent.addSlot(new CustomNumberSlot("height"));
		askForQuoteIntent.addSlot(new CustomNumberSlot("weight"));
		askForQuoteIntent.addSlot(new BooleanLiteralSlot("smoked"));

		Intent generalQueryIntent = new GeneralQueryIntent();

		Intent stockPriceIntent = new StockPriceIntent();

		Intent marketTrendIntent = new MarketTrendIntent();

		Intent accountBalanceIntent = new AccountBalanceIntent();
		accountBalanceIntent.addSlot(new AccTypeSlot("accType"));
		accountBalanceIntent.addSlot(new IpinSlot("ipin"));

		Intent getAccTypeIntent = new GetAccTypeIntent();
		getAccTypeIntent.addSlot(new AccTypeSlot("accType"));
		getAccTypeIntent.addSlot(new IpinSlot("ipin"));

		Intent weatherIntent = new WeatherIntent();

		Intent claimStatusIntent = new ClaimStatusIntent();
		claimStatusIntent.addSlot(new AlphaNumericSlot("claimId"));

		Intent getClaimIdIntent = new GetClaimIdIntent();
		getClaimIdIntent.addSlot(new AlphaNumericSlot("claimId"));

		Intent exitIntent = new ExitIntent();

		matcher.addIntent(findAdvisorIntent);
		matcher.addIntent(askForQuoteIntent);
		matcher.addIntent(generalQueryIntent);
		matcher.addIntent(stockPriceIntent);
		matcher.addIntent(marketTrendIntent);
		matcher.addIntent(exitIntent);
		matcher.addIntent(getAccTypeIntent);
		matcher.addIntent(accountBalanceIntent);
		matcher.addIntent(weatherIntent);
		matcher.addIntent(claimStatusIntent);

		return matcher;
	}

	@Override
	public StateMachine getStateMachine() {
		StateMachine stateMachine = new StateMachine();

		State startState = new StartState();

		State askforQuoteState = new AskForQuoteState();
		State getQuoteState = new GetQuoteState();
		Guard haveQuoteDetailGuard = new HaveQuoteDetailGuard();
		Guard dontHaveQuoteDetailsGuard = new DontHaveQuoteDetailsGuard();

		State findAdvisorState = new FindAdvisorState();
		State generalQueryState = new GeneralQueryState();
		State stockPriceState = new StockPriceState();
		State marketTrendState = new MarketTrendState();

		State getAccountBalanceState = new GetAccountBalanceState();
		Guard haveAccTypeGuard = new HaveAccTypeGuard();
		Guard dontHaveAccTypeGuard = new DontHaveAccTypeGuard();
		State getAccTypeState = new GetAccTypeState();

		State getWeatherState = new GetWeatherState();

		State getClaimStatusState = new GetClaimStatus();
		Guard haveClaimIdGuard = new HaveClaimIdGuard();
		State getClaimIdState = new GetClaimIdState();

		State exitState = new ExitState();

		stateMachine.setStartState(startState);

		// state transitions from startState
		stateMachine.addTransition("generalQueryIntent", startState, generalQueryState);
		stateMachine.addTransition("askForQuoteIntent", startState, getQuoteState, haveQuoteDetailGuard);
		stateMachine.addTransition("askForQuoteIntent", startState, askforQuoteState);
		stateMachine.addTransition("findAdvisorIntent", startState, findAdvisorState);
		stateMachine.addTransition("stockPriceIntent", startState, stockPriceState);
		stateMachine.addTransition("marketTrendIntent", startState, marketTrendState);
		stateMachine.addTransition("accountBalanceIntent", startState, getAccountBalanceState,haveAccTypeGuard);
		stateMachine.addTransition("accountBalanceIntent", startState, getAccTypeState);
		stateMachine.addTransition("weatherIntent", startState, getWeatherState);
		stateMachine.addTransition("claimStatusIntent", startState, getClaimStatusState, haveClaimIdGuard);
		stateMachine.addTransition("claimStatusIntent", startState, getClaimIdState);

		// state transiitons from getAccountBalanceState
		stateMachine.addTransition("accountBalanceIntent", getAccountBalanceState, getAccountBalanceState,haveAccTypeGuard);
		stateMachine.addTransition("accountBalanceIntent", getAccountBalanceState, getAccTypeState);
		stateMachine.addTransition("askForQuoteIntent", getAccountBalanceState, askforQuoteState);
		stateMachine.addTransition("marketTrendIntent", getAccountBalanceState, marketTrendState);
		stateMachine.addTransition("findAdvisorIntent", getAccountBalanceState, findAdvisorState);
		stateMachine.addTransition("stockPriceIntent", getAccountBalanceState, stockPriceState);
		stateMachine.addTransition("weatherIntent", getAccountBalanceState, getWeatherState);
		stateMachine.addTransition("claimStatusIntent", getAccountBalanceState, getClaimStatusState, haveClaimIdGuard);
		stateMachine.addTransition("claimStatusIntent", getAccountBalanceState, getClaimIdState);

		stateMachine.addTransition("accountBalanceIntent", getAccTypeState, getAccountBalanceState, haveAccTypeGuard);
		stateMachine.addTransition("accountBalanceIntent", getAccTypeState, getAccTypeState, dontHaveAccTypeGuard);
		stateMachine.addTransition("exitIntent", getAccountBalanceState, exitState);
		stateMachine.addTransition("exitIntent", getAccTypeState, exitState);
		stateMachine.addTransition("askForQuoteIntent", getAccTypeState, askforQuoteState);
		stateMachine.addTransition("findAdvisorIntent", getAccTypeState, findAdvisorState);
		stateMachine.addTransition("stockPriceIntent", getAccTypeState, stockPriceState);
		stateMachine.addTransition("marketTrendIntent", getAccTypeState, marketTrendState);
		stateMachine.addTransition("weatherIntent", getAccTypeState, getWeatherState);
		stateMachine.addTransition("claimStatusIntent", getAccTypeState, getClaimStatusState, haveClaimIdGuard);
		stateMachine.addTransition("claimStatusIntent", getAccTypeState, getClaimIdState);

		//state transitions from generalQueryState
		stateMachine.addTransition("generalQueryIntent", generalQueryState, generalQueryState);
		stateMachine.addTransition("askForQuoteIntent", generalQueryState, askforQuoteState);
		stateMachine.addTransition("stockPriceIntent", generalQueryState, stockPriceState);
		stateMachine.addTransition("marketTrendIntent", generalQueryState, marketTrendState);
		stateMachine.addTransition("accountBalanceIntent", generalQueryState, getAccountBalanceState, haveAccTypeGuard);
		stateMachine.addTransition("accountBalanceIntent", generalQueryState, getAccTypeState);
		stateMachine.addTransition("findAdvisorIntent", generalQueryState, findAdvisorState);
		stateMachine.addTransition("weatherIntent", generalQueryState, getWeatherState);
		stateMachine.addTransition("claimStatusIntent", generalQueryState, getClaimStatusState, haveClaimIdGuard);
		stateMachine.addTransition("claimStatusIntent", generalQueryState, getClaimIdState);
		stateMachine.addTransition("exitIntent", generalQueryState, exitState);

		//state transitions from stockPriceState
		stateMachine.addTransition("stockPriceIntent", stockPriceState, stockPriceState);
		stateMachine.addTransition("findAdvisorIntent", stockPriceState, findAdvisorState);
		stateMachine.addTransition("askForQuoteIntent", stockPriceState, askforQuoteState);
		stateMachine.addTransition("generalQueryIntent", stockPriceState, generalQueryState);
		stateMachine.addTransition("marketTrendIntent", stockPriceState, marketTrendState);
		stateMachine.addTransition("weatherIntent", stockPriceState, getWeatherState);
		stateMachine.addTransition("claimStatusIntent", stockPriceState, getClaimStatusState, haveClaimIdGuard);
		stateMachine.addTransition("claimStatusIntent", stockPriceState, getClaimIdState);
		stateMachine.addTransition("accountBalanceIntent", stockPriceState, getAccountBalanceState, haveAccTypeGuard);
		stateMachine.addTransition("accountBalanceIntent", stockPriceState, getAccTypeState);
		stateMachine.addTransition("exitIntent", stockPriceState, exitState);

		//state transitions from marketTrendState
		stateMachine.addTransition("marketTrendIntent", marketTrendState, marketTrendState);
		stateMachine.addTransition("findAdvisorIntent", marketTrendState, findAdvisorState);
		stateMachine.addTransition("askForQuoteIntent", marketTrendState, askforQuoteState);
		stateMachine.addTransition("generalQueryIntent", marketTrendState, generalQueryState);
		stateMachine.addTransition("weatherIntent", marketTrendState, getWeatherState);
		stateMachine.addTransition("accountBalanceIntent", marketTrendState, getAccountBalanceState, haveAccTypeGuard);
		stateMachine.addTransition("accountBalanceIntent", marketTrendState, getAccTypeState);
		stateMachine.addTransition("claimStatusIntent", marketTrendState, getClaimStatusState, haveClaimIdGuard);
		stateMachine.addTransition("claimStatusIntent", marketTrendState, getClaimIdState);
		stateMachine.addTransition("exitIntent", marketTrendState, exitState);

		//state transitions from askforQuoteState
		stateMachine.addTransition("askForQuoteIntent", askforQuoteState, getQuoteState, haveQuoteDetailGuard);
		stateMachine.addTransition("askForQuoteIntent", askforQuoteState, askforQuoteState, dontHaveQuoteDetailsGuard);
		stateMachine.addTransition("generalQueryIntent", askforQuoteState, generalQueryState);
		stateMachine.addTransition("marketTrendIntent", askforQuoteState, marketTrendState);
		stateMachine.addTransition("findAdvisorIntent", askforQuoteState, findAdvisorState);
		stateMachine.addTransition("accountBalanceIntent", askforQuoteState, getAccountBalanceState, haveAccTypeGuard);
		stateMachine.addTransition("accountBalanceIntent", askforQuoteState, getAccTypeState);
		stateMachine.addTransition("exitIntent", askforQuoteState, exitState);
		stateMachine.addTransition("stockPriceIntent", askforQuoteState, stockPriceState);
		stateMachine.addTransition("claimStatusIntent", askforQuoteState, getClaimStatusState, haveClaimIdGuard);
		stateMachine.addTransition("claimStatusIntent", askforQuoteState, getClaimIdState);
		stateMachine.addTransition("weatherIntent", askforQuoteState, getWeatherState);

		//state transitions from getQuoteState
		stateMachine.addTransition("stockPriceIntent", getQuoteState, stockPriceState);
		stateMachine.addTransition("marketTrendIntent", getQuoteState, marketTrendState);
		stateMachine.addTransition("weatherIntent", getQuoteState, getWeatherState);
		stateMachine.addTransition("generalQueryIntent", getQuoteState, generalQueryState);
		stateMachine.addTransition("findAdvisorIntent", getQuoteState, findAdvisorState);
		stateMachine.addTransition("accountBalanceIntent", getQuoteState, getAccountBalanceState, haveAccTypeGuard);
		stateMachine.addTransition("accountBalanceIntent", getQuoteState, getAccTypeState);
		stateMachine.addTransition("claimStatusIntent", getQuoteState, getClaimStatusState, haveClaimIdGuard);
		stateMachine.addTransition("claimStatusIntent", getQuoteState, getClaimIdState);
		stateMachine.addTransition("askForQuoteIntent", getQuoteState, askforQuoteState);
		stateMachine.addTransition("exitIntent", getQuoteState, exitState);

		//state transitions from findAdvisorState
		stateMachine.addTransition("exitIntent", findAdvisorState, exitState);
		stateMachine.addTransition("marketTrendIntent", findAdvisorState, marketTrendState);
		stateMachine.addTransition("findAdvisorIntent", findAdvisorState, findAdvisorState);
		stateMachine.addTransition("askForQuoteIntent", findAdvisorState, askforQuoteState);
		stateMachine.addTransition("generalQueryIntent", findAdvisorState, generalQueryState);
		stateMachine.addTransition("weatherIntent", findAdvisorState, getWeatherState);
		stateMachine.addTransition("claimStatusIntent", findAdvisorState, getClaimStatusState, haveClaimIdGuard);
		stateMachine.addTransition("claimStatusIntent", findAdvisorState, getClaimIdState);
		stateMachine.addTransition("accountBalanceIntent", findAdvisorState, getAccountBalanceState, haveAccTypeGuard);
		stateMachine.addTransition("accountBalanceIntent", findAdvisorState, getAccTypeState);
		stateMachine.addTransition("stockPriceIntent", findAdvisorState, stockPriceState);

		//state transitions from getClaimStatusState
		stateMachine.addTransition("exitIntent", getClaimIdState, exitState);
		stateMachine.addTransition("marketTrendIntent", getClaimIdState, marketTrendState);
		stateMachine.addTransition("findAdvisorIntent", getClaimIdState, findAdvisorState);
		stateMachine.addTransition("askForQuoteIntent", getClaimIdState, askforQuoteState);
		stateMachine.addTransition("generalQueryIntent", getClaimIdState, generalQueryState);
		stateMachine.addTransition("weatherIntent", getClaimIdState, getWeatherState);
		stateMachine.addTransition("claimStatusIntent", getClaimIdState, getClaimStatusState, haveClaimIdGuard);
		stateMachine.addTransition("claimStatusIntent", getClaimIdState, getClaimIdState);
		stateMachine.addTransition("accountBalanceIntent", getClaimIdState, getAccountBalanceState, haveAccTypeGuard);
		stateMachine.addTransition("accountBalanceIntent", getClaimIdState, getAccTypeState);
		stateMachine.addTransition("stockPriceIntent", getClaimIdState, stockPriceState);

		stateMachine.addTransition("claimStatusIntent", getClaimStatusState, getClaimStatusState, haveClaimIdGuard);
		stateMachine.addTransition("claimStatusIntent", getClaimStatusState, getClaimIdState);
		stateMachine.addTransition("exitIntent", getClaimStatusState, exitState);
		stateMachine.addTransition("marketTrendIntent", getClaimStatusState, marketTrendState);
		stateMachine.addTransition("findAdvisorIntent", getClaimStatusState, findAdvisorState);
		stateMachine.addTransition("askForQuoteIntent", getClaimStatusState, askforQuoteState);
		stateMachine.addTransition("generalQueryIntent", getClaimStatusState, generalQueryState);
		stateMachine.addTransition("weatherIntent", getClaimStatusState, getWeatherState);
		stateMachine.addTransition("accountBalanceIntent", getClaimStatusState, getAccountBalanceState, haveAccTypeGuard);
		stateMachine.addTransition("accountBalanceIntent", getClaimStatusState, getAccTypeState);
		stateMachine.addTransition("stockPriceIntent", getClaimStatusState, stockPriceState);


		//state transitions from exitState
		stateMachine.addTransition("generalQueryIntent", exitState, generalQueryState);
		stateMachine.addTransition("askForQuoteIntent", exitState, askforQuoteState);
		stateMachine.addTransition("findAdvisorIntent", exitState, findAdvisorState);
		stateMachine.addTransition("stockPriceIntent", exitState, stockPriceState);
		stateMachine.addTransition("marketTrendIntent", exitState, marketTrendState);
		stateMachine.addTransition("weatherIntent", exitState, getWeatherState);
		stateMachine.addTransition("claimStatusIntent", exitState, getClaimStatusState, haveClaimIdGuard);
		stateMachine.addTransition("claimStatusIntent", exitState, getClaimIdState);
		stateMachine.addTransition("accountBalanceIntent", exitState, getAccountBalanceState, haveAccTypeGuard);
		stateMachine.addTransition("accountBalanceIntent", exitState, getAccTypeState);

		//state transitions from getWeatherState
		stateMachine.addTransition("generalQueryIntent", getWeatherState, generalQueryState);
		stateMachine.addTransition("askForQuoteIntent", getWeatherState, askforQuoteState);
		stateMachine.addTransition("findAdvisorIntent", getWeatherState, findAdvisorState);
		stateMachine.addTransition("stockPriceIntent", getWeatherState, stockPriceState);
		stateMachine.addTransition("marketTrendIntent", getWeatherState, marketTrendState);
		stateMachine.addTransition("weatherIntent", getWeatherState, getWeatherState);
		stateMachine.addTransition("claimStatusIntent", getWeatherState, getClaimStatusState, haveClaimIdGuard);
		stateMachine.addTransition("claimStatusIntent", getWeatherState, getClaimIdState);
		stateMachine.addTransition("accountBalanceIntent", getWeatherState, getAccountBalanceState, haveAccTypeGuard);
		stateMachine.addTransition("accountBalanceIntent", getWeatherState, getAccTypeState);
		stateMachine.addTransition("exitIntent", getWeatherState, exitState);

		return stateMachine;
	}
}
