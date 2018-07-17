package com.rabidgremlin.mutters.slots;

import com.rabidgremlin.mutters.core.Context;
import com.rabidgremlin.mutters.core.Slot;
import com.rabidgremlin.mutters.core.SlotMatch;

public class IpinSlot extends Slot {
	private String name;

	public IpinSlot(String name)
	{
		this.name = name;
	}

	@Override
	public SlotMatch match(String token, Context context) {
		if (token.matches("[0-9]+") && token.length() == 6 && token.equalsIgnoreCase("123456")) {
			return new SlotMatch(this, token, token);
		}
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

}
