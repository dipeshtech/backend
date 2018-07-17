package com.iris.backend.mutter.matcher;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabidgremlin.mutters.core.Context;
import com.rabidgremlin.mutters.core.Intent;
import com.rabidgremlin.mutters.core.Slot;
import com.rabidgremlin.mutters.core.SlotMatch;
import com.rabidgremlin.mutters.core.SlotMatcher;
import com.rabidgremlin.mutters.core.session.Session;

public class CustomSlotMatcher implements SlotMatcher {
	private Logger logger = LoggerFactory.getLogger(CustomSlotMatcher.class);

	@Override
	public HashMap<Slot, SlotMatch> match(Session session, Context context, Intent intent, String utterance) {
		HashMap<Slot, SlotMatch> matchedSlots = new HashMap<Slot, SlotMatch>();

		for (Slot slot : intent.getSlots())
		{
			logger.info("handling slot:"+ slot.getName());
			String slotCheck = String.valueOf(session.getAttribute("askQuoteLastQuestion"));
			if(slot.getName().equalsIgnoreCase(slotCheck) || slotCheck.equalsIgnoreCase("null")){
				logger.info("matching slot:"+ slot.getName());
				SlotMatch match = slot.match(utterance, context);
				if (match != null && match.getValue()!="null")
				{
					logger.info("matched slot:"+ match.toString());
					matchedSlots.put(slot, match);
				}
			}
		}
		return matchedSlots;
	}
}
