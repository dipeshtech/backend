package com.iris.backend.mutter.state;

import java.util.Random;

import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.core.util.SessionUtils;
import com.rabidgremlin.mutters.state.IntentResponse;
import com.rabidgremlin.mutters.state.State;

public class GetAccountBalanceState extends State{

	public GetAccountBalanceState() {
		super("getAccountBalanceState");
	}

	@Override
	public IntentResponse execute(IntentMatch intentMatch, Session session) {
		String reply = null;
		Random rand = new Random();
		String accType = SessionUtils.getStringFromSlotOrSession(intentMatch, session, "accType", null);
		if(accType.equalsIgnoreCase("Annuities")){
			reply = "Your Annuities account balance is: "+ (rand.nextInt(1000) + 100)+"."
					+ "\nAnything else that I can do for you?";
		}
		else if(accType.equalsIgnoreCase("401k"))
			reply = "Your 401K account balance is: "+ (rand.nextInt(4000) + 500)+"."
					+ "\nAnything else that you want to know?";
		else
			reply = "Sorry, I am not able to retrieve your "+ accType + " balance right now.\nHow else can I help you?";
		session.removeAttribute("slot_jla1974_acctype");
		session.removeAttribute("SLOT_JLA1974_getaccTypeprompt");
		session.removeAttribute("SLOT_JLA1974_getipinprompt");
		session.removeAttribute("SLOT_JLA1974_ipin");
		
		return new IntentResponse(false, reply, null, null, null, null);
	}

}
