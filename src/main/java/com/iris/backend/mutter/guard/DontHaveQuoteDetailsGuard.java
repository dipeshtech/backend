package com.iris.backend.mutter.guard;

import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.core.util.SessionUtils;
import com.rabidgremlin.mutters.state.Guard;

public class DontHaveQuoteDetailsGuard implements Guard{

	@Override
	public boolean passes(IntentMatch match, Session session) {
		
		// save or slots into session
		SessionUtils.saveSlotsToSession(match, session);

		String age = SessionUtils.getStringFromSlotOrSession(match, session, "age", null);
		String smoked = SessionUtils.getStringFromSlotOrSession(match, session, "smoked", null);
		String height = SessionUtils.getStringFromSlotOrSession(match, session, "height", null);
		String weight = SessionUtils.getStringFromSlotOrSession(match, session, "weight", null);

		// if we dont have all, we need to return true so that askquote quotestate is executed again
		return (age == null || smoked == null || height == null || weight == null);
	}

	@Override
	public String getDescription() {
		return "Don't Have all the Quote Details";
	}

}
