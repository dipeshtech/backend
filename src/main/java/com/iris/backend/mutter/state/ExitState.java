package com.iris.backend.mutter.state;

import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.state.IntentResponse;
import com.rabidgremlin.mutters.state.State;

public class ExitState extends State {

	public ExitState() {
		super("exitState");
	}

	@Override
	public IntentResponse execute(IntentMatch intentMatch, Session session) {
		String answer = "Anything else that I may help you with?";
		IntentResponse response = new IntentResponse(true, answer, null, null, null, null);
		return response;
	}

}
