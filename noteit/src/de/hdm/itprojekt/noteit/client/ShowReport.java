package de.hdm.itprojekt.noteit.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.ReportService;
import de.hdm.itprojekt.noteit.shared.ReportServiceAsync;

public class ShowReport extends Composite  {
	
	private LoginInfo loginInfo = null;

	private NotesGeneralInformationReport GeneralInformationPanel;
	private NotesSharingInformationReport SharingInformationPanel;

	
	/**
	 * Create a remote service proxy to talk to the server-side Report
	 * service.
	 */
	private final ReportServiceAsync report = GWT.create(ReportService.class);
	
	//Logger
	private static Logger logger = Logger.getLogger("");
	
	HorizontalPanel contentPanel = new HorizontalPanel();
	HorizontalPanel navPanel = new HorizontalPanel();
	// Panels
	HorizontalPanel headerPanel = new HorizontalPanel();
	private VerticalPanel vpReport = new VerticalPanel();
	private HorizontalPanel headlinePanel = new HorizontalPanel();
	
	private HorizontalPanel hpGeneral = new HorizontalPanel();
	private HorizontalPanel hpSharing = new HorizontalPanel();
	Button b = new Button("sdd");
	Button c = new Button("sdd");
	
	VerticalPanel vpBasisPanel = new VerticalPanel();
	// Labels
	Label headlineLabel = new Label("NoteIt Report");
	Label lblGeneralReport = new Label("General Information");
	Label lblSharingReport = new Label("Sharing Information");
	
	
	public void onModuleLoad() {
		
		
		// TODO Auto-generated method stub 
		// Set style 
		headerPanel.setStylePrimaryName("headerPanel");
		headlineLabel.setStylePrimaryName("lbheadlineNoteit");
		headlinePanel.setStylePrimaryName("headlinePanel");
		contentPanel.setStylePrimaryName("contentPanel");
		hpGeneral.setStylePrimaryName("hpGeneral");
		hpSharing.setStylePrimaryName("hpSharing");
		navPanel.setStylePrimaryName("navPanel");
		
		
		// Add the Widgets
		headlinePanel.add(headlineLabel);
		headerPanel.add(headlinePanel);
		
		
		vpBasisPanel.add(headerPanel);
		vpBasisPanel.add(navPanel);
		vpBasisPanel.add(contentPanel);
		
		
		hpGeneral.add(lblGeneralReport);
		
		hpSharing.add(lblSharingReport);
		
		
		
		contentPanel.add(hpGeneral);
		contentPanel.add(hpSharing);
		
		logger.log(Level.SEVERE, "test");
		navPanel.setWidth("100%");
		vpBasisPanel.setWidth("100%");
		headlinePanel.setWidth("100%");
		
		GeneralInformationPanel = new NotesGeneralInformationReport();
		SharingInformationPanel = new NotesSharingInformationReport();
		
		//vpBasisPanel.add(SharingInformationPanel);
		
		
		RootPanel.get("content").add(vpBasisPanel);
		
	}
}
