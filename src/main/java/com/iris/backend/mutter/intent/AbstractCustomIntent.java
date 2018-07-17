package com.iris.backend.mutter.intent;

import com.rabidgremlin.mutters.core.Intent;

public abstract class AbstractCustomIntent  extends Intent{
	
	private String answer;

	public AbstractCustomIntent(String name) {
		super(name);
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	

}
