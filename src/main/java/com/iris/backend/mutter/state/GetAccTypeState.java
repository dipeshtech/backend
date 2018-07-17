package com.iris.backend.mutter.state;

import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.core.util.SessionUtils;
import com.rabidgremlin.mutters.state.IntentResponse;
import com.rabidgremlin.mutters.state.State;

public class GetAccTypeState extends State {

	public GetAccTypeState() {
		super("getAccTypeState");
	}

	@Override
	public IntentResponse execute(IntentMatch intentMatch, Session session) {
		SessionUtils.saveSlotsToSession(intentMatch, session);

		String reply = null;
		
		if(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "ipin", null) == null){
			if(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "getipinprompt", null)==null)
				reply = "Sure I will help you with that! Since this is a confidential information, I will need additional details to verify "
						+ "your identity. Can you tell me your 6 digits IPIN please?";
			else
				reply = "Either you have not entered 6 digits code or the IPIN entered by you is incorrect. Please verify and type again !";
			session.setAttribute("SLOT_JLA1974_getipinprompt", "flag1");
		}
		
		else if(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "accType", null) == null){
			if(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "getaccTypeprompt", null)==null)
				reply = "Your IPIN was successfully verified. Are you looking for Annuities balance or 401k account balance?";
			else
				reply = "I did not understand that. Did you say annuities or 401k?";
			session.setAttribute("SLOT_JLA1974_getaccTypeprompt", "flag1");
		}
		return new IntentResponse(false, reply, null, null, null, null);
	}

}
