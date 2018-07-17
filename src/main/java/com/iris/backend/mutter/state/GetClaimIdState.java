package com.iris.backend.mutter.state;

import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.core.util.SessionUtils;
import com.rabidgremlin.mutters.state.IntentResponse;
import com.rabidgremlin.mutters.state.State;

public class GetClaimIdState extends State {

	public GetClaimIdState() {
		super("getClaimIdState");
	}

	@Override
	public IntentResponse execute(IntentMatch intentMatch, Session session) {
		SessionUtils.saveSlotsToSession(intentMatch, session);

		String reply = "I am not sure if I have hungover right now. Cant seem to follow you right.";
		if(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "claimId", null) == null){
			if(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "getclaimidprompt", null)==null)
				reply = "No Problem. Could you tell me the Claim Id Please?";
			else
				reply = "Sorry, I did not get the claim ID. Can you please re-enter it?";
		}
		session.setAttribute("SLOT_JLA1974_getclaimidprompt", "flag1");
		return new IntentResponse(false, reply, null, null, null, null);
	}

}
