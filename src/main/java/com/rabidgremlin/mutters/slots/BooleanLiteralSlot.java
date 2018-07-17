package com.rabidgremlin.mutters.slots;

import com.rabidgremlin.mutters.core.Context;
import com.rabidgremlin.mutters.core.Slot;
import com.rabidgremlin.mutters.core.SlotMatch;

public class BooleanLiteralSlot extends Slot {

	private String name;

	public BooleanLiteralSlot(String name)
	{
		this.name = name;
	}

	@Override
	public SlotMatch match(String utterance, Context context)
	{
		if(utterance.toLowerCase().contains("yes") || utterance.toLowerCase().contains("yeah") || 
				utterance.toLowerCase().contains("ya") || utterance.toLowerCase().contains("yup"))
		{
			return new SlotMatch(this, utterance, "yes");
		}
		else if(utterance.toLowerCase().contains("no") || utterance.toLowerCase().contains("na") || 
				utterance.toLowerCase().contains("nopes") || utterance.toLowerCase().contains("noo") ||
				utterance.toLowerCase().contains("nope") || utterance.toLowerCase().contains("dont") ||
				utterance.toLowerCase().contains("don't") || utterance.toLowerCase().contains("do not"))
		{
			return new SlotMatch(this, utterance, "no");
		}
		return null;
	}


	@Override
	public String getName() {
		return name;
	}

}
