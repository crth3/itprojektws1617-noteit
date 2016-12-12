package de.hdm.itprojekt.noteit.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.ReportAdmin;
import de.hdm.itprojekt.noteit.shared.ReportAdminAsync;

/**
 * 
 * @Entry point classes define <code>onModuleLoad()</code>.
 *s
 */
public class NotesReport implements EntryPoint {
	
	/**
	 * Create a remote service proxy to talk to the server-side Report
	 * service.
	 */
	private final ReportAdminAsync report = GWT.create(ReportAdmin.class);
	
	//Logger
	private static Logger logger = Logger.getLogger("");
	
	// Panels
	HorizontalPanel headerPanel = new HorizontalPanel();
	private VerticalPanel vpReport = new VerticalPanel();
	private HorizontalPanel headlinePanel = new HorizontalPanel();
	
	// Labels
	Label headlineLabel = new Label("NoteIt Report");
	
	

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		
		// Set style 
		headerPanel.setStylePrimaryName("headerPanel");
		headlineLabel.setStylePrimaryName("headlineLabel");
		headlinePanel.setStylePrimaryName("headlinePanel");

	
		// Add the Widgets
		headlinePanel.add(headlineLabel);
		headerPanel.add(headlinePanel);

		
		logger.log(Level.SEVERE, "test");
	
		
		RootPanel.get("head").add(headerPanel);
		



	}

}
