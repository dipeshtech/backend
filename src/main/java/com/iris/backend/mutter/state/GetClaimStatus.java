package com.iris.backend.mutter.state;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.rabidgremlin.mutters.core.IntentMatch;
import com.rabidgremlin.mutters.core.session.Session;
import com.rabidgremlin.mutters.core.util.SessionUtils;
import com.rabidgremlin.mutters.state.IntentResponse;
import com.rabidgremlin.mutters.state.State;

public class GetClaimStatus extends State {
	Logger logger = Logger.getLogger(GetClaimStatus.class);

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/test";
	//CREATE USER 'myremoteuser123'@'%' IDENTIFIED BY 'SLOT_JLA1974_pass';

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "";

	public GetClaimStatus() {
		super("getClaimStatus");
	}

	@Override
	public IntentResponse execute(IntentMatch intentMatch, Session session) {
		Connection conn = null;
		Statement stmt = null;
		String status = null;
		String claimId = SessionUtils.getStringFromSlotOrSession(intentMatch, session, "claimId", null);
		String answer = "We dont have any info related to "+ claimId + " in our system.\n"
				+ "Please contact our call service representative for further enquiry on the number 1800 333 0333 between Mondays to Fridays, 8:30 am to 5:30 pm.\n"
				+ "If you're dialling from overseas or via a payphone, please call +65 633 30333.\nIs there anything else I can help you with?";
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			logger.info("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT status FROM test1 where claimId='"+claimId+"'";
			logger.info("executing SQL - "+ sql);
			ResultSet rs = stmt.executeQuery(sql);

			//STEP 5: Extract data from result set
			while(rs.next()){
				logger.info("record fetched");
				status = rs.getString("status");
			}

			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}


		if(status!=null){
			answer = "The status of your claim for claimId "+claimId+" is  - "+ status+".\nContact prudential representatives at 1800 333 0333 "
					+ "between Mondays to Fridays, 8:30am to 5:30pm if you want to enquire more. Anything else that you want to know as of now?";	
		}
		session.removeAttribute("SLOT_JLA1974_getclaimidprompt");
		session.removeAttribute("slot_jla1974_claimid");
		return new IntentResponse(false, answer, null, null, null, null);
	}

}
