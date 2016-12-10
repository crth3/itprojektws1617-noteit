package de.hdm.itprojekt.noteit.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import de.hdm.itprojekt.noteit.shared.ReportAdmin;
import de.hdm.itprojekt.noteit.shared.ReportAdminAsync;

/**
 * 
 * @Entry point classes define <code>onModuleLoad()</code>.
 *s
 */
public class ReportGen implements EntryPoint {
	
	/**
	 * Create a remote service proxy to talk to the server-side Report
	 * service.
	 */
	private final ReportAdminAsync report = GWT.create(ReportAdmin.class);
	
	private static Logger rootLogger = Logger.getLogger("");

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		
		rootLogger.log(Level.SEVERE, "test");

	}

}
