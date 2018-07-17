package com.iris.backend.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.core.Context;

@Service("userSessionsService")
public class UserSessionsService {
	Logger logger = Logger.getLogger(UserSessionsService.class);

	class SessionWithTime{

		Session session;
		Long timestamp;

		public SessionWithTime(Session session, Long timestamp){
			this.session = session;
			this.timestamp = timestamp;
		}	
	}

	Map<String, SessionWithTime> sessions = new HashMap<String, SessionWithTime>();
	Map<String, Context> contexts = new HashMap<String, Context>();
	int minutes = 30;
	long milliseconds = minutes * 60000;

	public Session getOrCreateSession(String uid) {
		if(!sessions.containsKey(uid) || (sessions.get(uid).timestamp + milliseconds) < System.currentTimeMillis()){
			if(sessions.containsKey(uid) && (sessions.get(uid).timestamp + milliseconds) < System.currentTimeMillis()){
				logger.info("session timeout as 30 minutes have passed");
			}
			else
				logger.info("starting new session");
			sessions.remove(uid);
			Session session = new Session();
			SessionWithTime sessionWithTime = new SessionWithTime(session,System.currentTimeMillis());
			sessions.put(uid, sessionWithTime);
		}
		return sessions.get(uid).session;
	}

	public Context getOrCreateContext(String uid) {
		if (!contexts.containsKey(uid)) {
			Context context = new Context();
			contexts.put(uid, context);
		}
		return contexts.get(uid);
	}
}