package com.iris.backend.mutter.state;

import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.state.IntentResponse;
import com.rabidgremlin.mutters.state.State;

public class StartState extends State  {

	public StartState() {
		super("startState");
	}

	@Override
	public IntentResponse execute(IntentMatch intentMatch, Session session) {
		 throw new IllegalStateException("You shouldn't be executing this state!");
	}

}
