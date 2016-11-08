package de.hdm.itprojekt.noteit.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.appengine.api.rdbms.AppEngineDriver;

public class DBConnection {
	
		
		/**
		 * This class enables the connection to the mySQL database
		 * TODO: Add more explanations 
		 */
				
			/**
			 * private and static instance of DBConnection
			 * the volatile modifier is needed to prevent errors caused by
			 * the Java Memory Model
			 */
			private static volatile DBConnection instance = null;
			private static volatile Connection con = null;
		
			// Local Access Variables
			// private static final String localURL = "jdbc:mysql://localhost:8889/itproject";
			// private static final String driverLocal = "com.mysql.jdbc.Driver";
			// Google Access Variables
			
			// Deployment URL
			//private static final String googleURL = "jdbc:google:rdbms://projektname-groupfive:dbname/projektname?user=root&password=hdmgroupfive";
			
			// Local Dev URL
		private static final String googleURL = "jdbc:mysql://104.154.134.231:3306/projekt-tidy-cortex-148515?user=root&password=noteit";
			
			
			
			/**
			 * Private Constructor to prevent the creation of new objects
			 * via "new"
			 */
			private DBConnection() {
				try {
					// Load Driver
					// Class.forName(googleDriver);
					 DriverManager.registerDriver(new AppEngineDriver());
				} //catch (ClassNotFoundException e) {
					//e.printStackTrace();
					catch (SQLException error) {
					instance = null;
				}
			}
			
			 /** 
			  * Establish DB Connection
			  * 
			  * @return con
			  */
			private Connection createConnection() {
				con = null;
				try{
					con = DriverManager.getConnection(googleURL);
					// con = DriverManager.getConnection(localURL, user, password);
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Connection Error");
				}
				return con;
			}
			
			/**
			 * Synchronized double-checked locking singleton
			 * Synchronization is needed to handle multithreading
			 * double-checking is implemented for perfomance reasons
			 */
			// Method to create new Singleton Connections
//			public static Connection connection() {
//				if(instance == null) {
//					synchronized(DBConnection.class) {
//						if(instance == null)
//							instance = new DBConnection();
//						
//					}
//					
//					
//				} return instance.createConnection();
//			} 
			
			public static Connection connection() {
			if(instance == null || con == null) {
				synchronized(DBConnection.class) {
					if(instance == null)
						instance = new DBConnection();
					
				}
				con = instance.createConnection();
				
			}
			return con;
		
		} 
	}	
	
