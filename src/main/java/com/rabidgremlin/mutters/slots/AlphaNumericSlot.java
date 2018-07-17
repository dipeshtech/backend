package com.rabidgremlin.mutters.slots;

import java.util.ArrayList;

import com.rabidgremlin.mutters.core.Context;
import com.rabidgremlin.mutters.core.Slot;
import com.rabidgremlin.mutters.core.SlotMatch;

import edu.emory.mathcs.backport.java.util.Arrays;

public class AlphaNumericSlot extends Slot {
	private String name;

	public AlphaNumericSlot(String name) {
		this.name = name;
	}

	@Override
	public SlotMatch match(String utterance, Context context) {
		ArrayList<String> utteranceTokens = new ArrayList<String>(Arrays.asList(utterance.split("\\s+")));
		String claimId = null;
		for(String token : utteranceTokens){
			if(!token.matches("[a-zA-Z]+")){
				token = token.replace(".", "");
				token = token.trim();
				claimId = token;
				return new SlotMatch(this, claimId, claimId);
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

}
