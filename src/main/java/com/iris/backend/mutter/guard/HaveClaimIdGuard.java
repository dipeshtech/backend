package com.iris.backend.mutter.guard;

import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.core.util.SessionUtils;
import com.rabidgremlin.mutters.state.Guard;

public class HaveClaimIdGuard implements Guard {

	@Override
	public boolean passes(IntentMatch request, Session session) {
		SessionUtils.saveSlotsToSession(request, session);
		String claimId =  SessionUtils.getStringFromSlotOrSession(request, session, "claimId", null);
		return (claimId!=null);
	}

	@Override
	public String getDescription() {
		return "haveClaimIdGuard";
	}

}
