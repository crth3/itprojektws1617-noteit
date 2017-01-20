package de.hdm.itprojekt.noteit.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.ReportService;
import de.hdm.itprojekt.noteit.shared.ReportServiceAsync;


/**
 * 
 * @Entry point classes define <code>onModuleLoad()</code>.
 *s
 */
public class NotesReport implements EntryPoint {

	
	private static Logger rootLogger = Logger.getLogger("");

	
	/**
	 * Create a remote service proxy to talk to the server-side Report
	 * service.
	 */
	private final ReportServiceAsync report = GWT.create(ReportService.class);
	private NotesAdministrationAsync notesAdministration = null; 

	//Logger
	private static Logger logger = Logger.getLogger("");

	private HorizontalPanel headlinePanel = new HorizontalPanel();
	HorizontalPanel headerPanel = new HorizontalPanel();
	private VerticalPanel vpReport = new VerticalPanel();
	HorizontalPanel contentPanel = new HorizontalPanel();
	HorizontalPanel navPanel = new HorizontalPanel();
	VerticalPanel vpLeft = new VerticalPanel();
	VerticalPanel vpRight = new VerticalPanel();
	VerticalPanel vpBasisPanel = new VerticalPanel();
	Button bGeneral = new Button("Allgemeine Informationen zu Notizen");
	Button bSharing = new Button("Informationen zu Notizen und Berechtigungen eines Nutzers");
	Label headlineLabel = new Label("NoteIt Report");
	
	@Override
	public void onModuleLoad() {
		 		
		
		/*-------------Styles -------------*/

		headerPanel.setStylePrimaryName("headerPanel");
		headlineLabel.setStylePrimaryName("lbheadlineNoteit");
		headlinePanel.setStylePrimaryName("headlinePanel");
		contentPanel.setStylePrimaryName("contentPanel");
		navPanel.setStylePrimaryName("navPanel");

		
		/*-----------------Width-------------*/
		
		navPanel.setWidth("100%");
		vpBasisPanel.setWidth("100%");
		headlinePanel.setWidth("100%");
		headerPanel.setWidth("100%");
		contentPanel.setWidth("100%");
		bGeneral.setWidth("100%");
		bSharing.setWidth("100%");
		
		/*-------------Widgets-------------*/

		headlinePanel.add(headlineLabel);
		headerPanel.add(headlinePanel);
		vpBasisPanel.add(headerPanel);
		vpBasisPanel.add(navPanel);
		vpBasisPanel.add(contentPanel);
		navPanel.add(bGeneral);
		navPanel.add(bSharing);
			
		RootPanel.get("Reporthead").add(vpBasisPanel);
		
		bSharing.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
			
			contentPanel.clear();
			NotesSharingInformationReport notesSharingInformation = new NotesSharingInformationReport();
			contentPanel.add(notesSharingInformation);
			
			}
		
	});
		
		bGeneral.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				contentPanel.clear();
				NotesGeneralInformationReport notesGeneralInformation = new NotesGeneralInformationReport();
				contentPanel.add(notesGeneralInformation);
				}
			
		});
			
	
		}
	}
