package de.hdm.itprojekt.noteit.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
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
	
	HorizontalPanel contentPanel = new HorizontalPanel();
	HorizontalPanel navPanel = new HorizontalPanel();
	// Panels
	HorizontalPanel headerPanel = new HorizontalPanel();
	private VerticalPanel vpReport = new VerticalPanel();
	private HorizontalPanel headlinePanel = new HorizontalPanel();
	Button b = new Button("djfsk");
	

	VerticalPanel vpBasisPanel = new VerticalPanel();
	// Labels
	Label headlineLabel = new Label("NoteIt Report");
	
	

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		// Set style 
		headerPanel.setStylePrimaryName("headerPanel");
		headlineLabel.setStylePrimaryName("lbheadlineNoteit");
		headlinePanel.setStylePrimaryName("headlinePanel");
		contentPanel.setStylePrimaryName("contentPanel");
		navPanel.setStylePrimaryName("navPanel");
		// Add the Widgets
		headlinePanel.add(headlineLabel);
		headerPanel.add(headlinePanel);
		contentPanel.add(b);

		vpBasisPanel.add(headerPanel);
		vpBasisPanel.add(navPanel);
		vpBasisPanel.add(contentPanel);
		logger.log(Level.SEVERE, "test");
		navPanel.setWidth("100%");
		vpBasisPanel.setWidth("100%");
		headlinePanel.setWidth("100%");
		
		RootPanel.get("head").add(vpBasisPanel);
		

	}

}
