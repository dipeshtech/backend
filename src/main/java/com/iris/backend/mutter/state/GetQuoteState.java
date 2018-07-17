package com.iris.backend.mutter.state;

import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.core.util.SessionUtils;
import com.rabidgremlin.mutters.state.IntentResponse;
import com.rabidgremlin.mutters.state.State;

public class GetQuoteState extends State {

	public GetQuoteState() {
		super("getQuoteState");
	}

	@Override
	public IntentResponse execute(IntentMatch intentMatch, Session session) {
		SessionUtils.saveSlotsToSession(intentMatch, session);

		boolean eligible = true;
		String answer = "";

		int age = Integer.parseInt(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "age", null));
		String smoked = SessionUtils.getStringFromSlotOrSession(intentMatch, session, "smoked", null);
		int weight = Integer.parseInt(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "weight", null));
		int height = Integer.parseInt(SessionUtils.getStringFromSlotOrSession(intentMatch, session, "height", null));

		if(age > 60 || age < 18)
			eligible = false;
		if(smoked.equalsIgnoreCase("yes"))
			eligible = false;

		double weightInKilos = weight * 0.453592;
		double heightInMeters = ((double)height)/100;
		double bmi = weightInKilos / Math.pow(heightInMeters, 2.0);

		if(bmi>33)
			eligible = false;

		if(eligible){
			answer = "Great News! You are eligible for an accelerated UW Decision.\nPlease proceed with your application "
					+ "at this link: https://client.prudential.com/retail/portal/PruRDAlias/_NoNavigation/pru.rp.registration?Site=Public.\n"
					+ "Anything else that I could help you with?";
		}
		else{
			answer = "Unfortunately, You are not eligible for an Accelerated UW Decision.\nPlease register at https://www.prudential.com/register "
					+ "and our representatives will contact with you shortly to further process your application\n"
					+ "Anything else that I could help you with?";
		}
		session.removeAttribute("SLOT_JLA1974_getageprompt");
		session.removeAttribute("SLOT_JLA1974_getsmokedprompt");
		session.removeAttribute("SLOT_JLA1974_getheightprompt");
		session.removeAttribute("SLOT_JLA1974_getweightprompt");
		session.removeAttribute("askquotelastquestion");
		
		session.removeAttribute("SLOT_JLA1974_height");
		session.removeAttribute("SLOT_JLA1974_age");
		session.removeAttribute("SLOT_JLA1974_smoked");
		session.removeAttribute("SLOT_JLA1974_weight");
		return new IntentResponse(false, answer, null, null, null, null);
	}

}
