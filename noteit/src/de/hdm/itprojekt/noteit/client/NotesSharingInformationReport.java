
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
private HorizontalPanel hp = new HorizontalPanel();
private MultiWordSuggestOracle oracle;
private SuggestBox sb;
private Button btnGenerate = new Button("Generate");
final User user = new User();
int userId;

	
	public NotesSharingInformationReport() {
		
		oracle = new MultiWordSuggestOracle();
		
		reportService = GWT.create(ReportService.class);

		
		notesAdministration.findAllUser(new AsyncCallback<ArrayList<User>>() {
			
			@Override
			public void onSuccess(ArrayList<User> result) {
				System.out.println("result" + result);
				
				for (User user : result) {
					
					String username ="";
					username = user.getId() +" " + user.getFirstName() + " " + user.getLastName()
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
		
		
		 sb.addSelectionHandler(new SelectionHandler<Suggestion>() {

		        public void onSelection(SelectionEvent<Suggestion> event) {
		        	

		           	//ausgewählten Value recieven        	
		            String selectedProperty =   ((SuggestBox)event.getSource()).getValue(); 
		        		  
		            // Alle Zeichen nach der UserId löschen
		            String sfinalProperty = selectedProperty.split(" ")[0];
		            
		            // String in Integer umwandeln
		            int ifinalProperty = Integer.parseInt(sfinalProperty);
		            
		            // userId zuweisen
		            user.setId(ifinalProperty);
		            userId = ifinalProperty;
		            
		            // UserObjekt befüllen
		            notesAdministration.findUserById(userId, new AsyncCallback<User>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onSuccess(User result) {
							// TODO Auto-generated method stub
							user.setFirstName(result.getFirstName());
							user.setLastName(result.getLastName());
						}
		            	
		            });
		            
		        }
		    });

		
		hp.setStyleName("PanelBorder");
		add(hp);
		add(contentPanel);
		hp.add(sb);
		hp.add(btnGenerate);
	
	
	btnGenerate.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
						
			reportService.createReportNotesSharingInformation(user, new AsyncCallback<NotesSharingInformation>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					Window.alert("Fehler "+caught);
				}

				

				@Override
				public void onSuccess(NotesSharingInformation notesSharingInformation) {
					// TODO Auto-generated method stub
					
					//hp.clear();
					
					HTMLReportWriter writerreport = new HTMLReportWriter();
					final	ReportSimple report = notesSharingInformation;
					writerreport.process(report);
					add(new HTML(writerreport.getReportText()));
					
				}
			});
			
			}
		
	});
	
	

	}

}
