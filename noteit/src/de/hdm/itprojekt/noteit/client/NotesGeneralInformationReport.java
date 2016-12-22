package de.hdm.itprojekt.noteit.client;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.ReportService;
import de.hdm.itprojekt.noteit.shared.ReportServiceAsync;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class NotesGeneralInformationReport extends VerticalPanel{
	
	private NotesAdministrationAsync notesAdministration = null; 
	private ReportServiceAsync reportService = null;
	private MultiWordSuggestOracle oracle;
	private SuggestBox sb;
	private Button btnGenerate = new Button("Generate");
	
	
	
	private HorizontalPanel hp = new HorizontalPanel();
	
	public NotesGeneralInformationReport() {
		
		notesAdministration = ClientsideSettings.getAdministrationService();
		reportService = ClientsideSettings.getReportService();
		oracle = new MultiWordSuggestOracle();
		
		notesAdministration.findAllUser(new AsyncCallback<ArrayList<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(ArrayList<User> result) {
				
				for (User user : result) {
					
					String username ="";
					username = user.getFirstName() + " " + user.getLastName()
							+ " " + user.getMail();

					oracle.add(username);

				}

			}
		});
		
		
		sb = new SuggestBox(oracle);
		hp.setStyleName("PanelBorder");
		add(hp);	
		hp.add(sb);
	
	hp.add(btnGenerate);
		
			
		
	}
	

}
