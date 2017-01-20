
package de.hdm.itprojekt.noteit.client;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.ReportService;
import de.hdm.itprojekt.noteit.shared.ReportServiceAsync;
import de.hdm.itprojekt.noteit.shared.bo.User;
import de.hdm.itprojekt.noteit.shared.report.HTMLReportWriter;
import de.hdm.itprojekt.noteit.shared.report.NotesSharingInformation;
import de.hdm.itprojekt.noteit.shared.report.ReportSimple;

public class NotesSharingInformationReport extends VerticalPanel {
	
private final static NotesAdministrationAsync notesAdministration = GWT.create(NotesAdministration.class);
private ReportServiceAsync reportService = null;
		
static HorizontalPanel contentPanel = new HorizontalPanel();	
private HorizontalPanel hpSearchPanel = new HorizontalPanel();
private VerticalPanel vpSearchUser = new VerticalPanel();
private VerticalPanel vpList = new VerticalPanel();

private VerticalPanel vpReport = new VerticalPanel();
private VerticalPanel vpGenerate = new VerticalPanel();



private MultiWordSuggestOracle oracle;
private SuggestBox sb;
private ListBox lbPermission = new ListBox();

Label lblSearchUser = new Label("Nutzersuche");
Label lblPermission = new Label("Berechtigung");

//TODO Label ersetzen
Label lblGenerate = new Label("Report");

private Button btnGenerate = new Button("Erstellen");
User user;
int userId;
String sPermission;
int permission;



public void onLoad() {
	
}
	
	public NotesSharingInformationReport() {
		
		
		
		oracle = new MultiWordSuggestOracle();
		
		reportService = GWT.create(ReportService.class);

		
		notesAdministration.findAllUser(new AsyncCallback<ArrayList<User>>() {
			
			@Override
			public void onSuccess(ArrayList<User> result) {
				System.out.println("result" + result);
				
				for (User user : result) {
					
					String username ="";
					username = user.getFirstName() + " " + user.getLastName()
							+ " " + user.getMail();
					oracle.add(username);
					//Window.alert("Suggestion - userId" + userId);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Failure: "+caught);
			}
		});
		
		
		sb = new SuggestBox(oracle);
		sb.setLayoutData(ALIGN_LEFT);
		
		
		sb.addSelectionHandler(new SelectionHandler<Suggestion>() {

	        public void onSelection(SelectionEvent<Suggestion> event) {
	        	

	           	//ausgewählten Value recieven        	
	            String selectedProperty =   ((SuggestBox)event.getSource()).getValue(); 
	            	        		  
	            // Split, damit Emailadresse übrig bleibt
	            String sfinalProperty = selectedProperty.split(" ")[2];
	            
	            String mail = sfinalProperty;
	            
	            notesAdministration.findUserByMail(mail, new AsyncCallback<User>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(User result) {
						// TODO Auto-generated method stub
						
						user = new User();
						
						user.setId(result.getId());
						user.setFirstName(result.getFirstName());
						user.setLastName(result.getLastName());						
					}
	            	
	            });
	            
	            
	        }
	        
	    });
		 
		// Setzen der Auswahl-Optionen
		lbPermission.addItem("Alle Berechtigungen");
		lbPermission.addItem("Lesen");
		lbPermission.addItem("Lesen / Bearbeiten");
		lbPermission.addItem("Lesen / Bearbeiten / Löschen");
		
		// Zeige alle Optionenn an
		lbPermission.setVisibleItemCount(4);

		vpSearchUser.add(lblSearchUser);
		vpSearchUser.add(sb);
		
		vpList.add(lblPermission);
		vpList.add(lbPermission);
		
		vpGenerate.add(lblGenerate);
		vpGenerate.add(btnGenerate);
		
		hpSearchPanel.add(vpSearchUser);
		hpSearchPanel.add(vpList);
		hpSearchPanel.add(vpGenerate);
		 
		 

		add(hpSearchPanel);
		add(vpReport);
		
		
	
	
	btnGenerate.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
			
			// Get ausgewählte Berechtigung
			if (lbPermission.getSelectedItemText() == "Alle Berechtigungen") {
				permission = 0;
			}else if (lbPermission.getSelectedItemText() == "Lesen"){
				permission = 1;				
			}else if (lbPermission.getSelectedItemText() == "Lesen / Bearbeiten"){
				permission = 2;				
			}else if (lbPermission.getSelectedItemText() == "Lesen / Bearbeiten / Löschen"){
				permission = 3;				
			}
						
			if (user != null) {
			
			reportService.createReportNotesSharingInformation(user, permission, new AsyncCallback<NotesSharingInformation>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					Window.alert("Fehler "+caught);
				}

				

				@Override
				public void onSuccess(NotesSharingInformation notesSharingInformation) {
					// TODO Auto-generated method stub
	
					vpReport.clear();
			
					HTMLReportWriter writerreport = new HTMLReportWriter();
					final	ReportSimple report = notesSharingInformation;
					writerreport.process(report);
					vpReport.add(new HTML(writerreport.getReportText()));
					
				}
			});
			
			}else {
				Window.alert("Bitte wähle einen Nutzer aus!");
				
			}
			
			}
		
	});
	
	

	}

}
