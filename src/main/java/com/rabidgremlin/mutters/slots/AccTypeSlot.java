package com.rabidgremlin.mutters.slots;

import com.rabidgremlin.mutters.core.Context;
import com.rabidgremlin.mutters.core.Slot;
import com.rabidgremlin.mutters.core.SlotMatch;

public class AccTypeSlot extends Slot{

	private String name;

	public AccTypeSlot(String name)
	{
		this.name = name;
	}
	@Override
	public SlotMatch match(String token, Context context) {
		if(token.toLowerCase().contains("annuities") || token.toLowerCase().contains("annuity")){
			return new SlotMatch(this, "annuities", "annuities");
		}
		else if(token.toLowerCase().contains("401k") || token.toLowerCase().contains("retirement") || 
				token.toLowerCase().contains("401") || token.toLowerCase().contains("401 k")){
			return new SlotMatch(this, "401k", "401k");
		}
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

}
