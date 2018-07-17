package com.iris.backend.mutter.guard;

import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.core.util.SessionUtils;
import com.rabidgremlin.mutters.state.Guard;

public class HaveAccTypeGuard implements Guard {

	@Override
	public boolean passes(IntentMatch match, Session session) {
		SessionUtils.saveSlotsToSession(match, session);
		String accType =  SessionUtils.getStringFromSlotOrSession(match, session, "accType", null);
		String ipin =  SessionUtils.getStringFromSlotOrSession(match, session, "ipin", null);
		return (accType!=null && ipin!=null);
	}

	@Override
	public String getDescription() {
		return "HaveAccTypeGuard";
	}

}
