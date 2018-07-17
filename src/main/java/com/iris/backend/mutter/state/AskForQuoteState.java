package com.iris.backend.mutter.state;

import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.core.util.SessionUtils;
import com.rabidgremlin.mutters.state.IntentResponse;
import com.rabidgremlin.mutters.state.State;

public class AskForQuoteState extends State{

	public AskForQuoteState() {
		super("askForQuoteState");
	}

	@Override
	public IntentResponse execute(IntentMatch intentMatch, Session session) {

		SessionUtils.saveSlotsToSession(intentMatch, session);

		String reply = "I am having trouble understanding...";

		if(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "age", null) == null){
			session.setAttribute("askQuoteLastQuestion", "age");
			if(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "getageprompt", null)==null)
				reply = "Sure, I will help you with that. May I know your age?";
			else
				reply = "I am not sure if I got your age right. Please type again";
			session.setAttribute("SLOT_JLA1974_getageprompt", "flag1");
		}
		else if(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "smoked", null) == null){
			session.setAttribute("askQuoteLastQuestion", "smoked");
			if(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "getsmokedprompt", null)==null)
				reply = "Have you smoked in the last 12 years?";
			else
				reply = "Say Yes or No";
			session.setAttribute("SLOT_JLA1974_getsmokedprompt", "flag1");
		}
		else if(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "height", null) == null){
			session.setAttribute("askQuoteLastQuestion", "height");
			if(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "getheightprompt", null)==null)
				reply = "What's your height (in centimeters)?";
			else
				reply = "I did not get your height right. Please help me understand again?";
			session.setAttribute("SLOT_JLA1974_getheightprompt", "flag1");
		}
		else if(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "weight", null) == null){
			session.setAttribute("askQuoteLastQuestion", "weight");
			if(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "getweightprompt", null)==null)
				reply = "What's your weight (in pounds)?";
			else
				reply = "Ah! I tried to understand that, but did not get it. Tell me your weight again";
			session.setAttribute("SLOT_JLA1974_getweightprompt", "flag1");
		}
		return new IntentResponse(false, reply, null, null, null, null);
	}

}
