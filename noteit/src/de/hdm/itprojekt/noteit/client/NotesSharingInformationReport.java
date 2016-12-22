
package de.hdm.itprojekt.noteit.client;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.ReportServiceAsync;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class NotesSharingInformationReport extends VerticalPanel {
	
	private final static NotesAdministrationAsync notesAdministration = GWT.create(NotesAdministration.class);

		
	
private HorizontalPanel hp = new HorizontalPanel();
//private NotesAdministrationAsync notesAdministration = null; 
private MultiWordSuggestOracle oracle;
private SuggestBox sb;
private Button btnGenerate = new Button("Generate");
	
	public NotesSharingInformationReport() {
		
		oracle = new MultiWordSuggestOracle();
		
		
		notesAdministration.findAllUser(new AsyncCallback<ArrayList<User>>() {
			
			@Override
			public void onSuccess(ArrayList<User> result) {
				System.out.println("result" + result);
				
				for (User user : result) {
					
					String username ="";
					username = user.getFirstName() + " " + user.getLastName()
							+ " " + user.getMail();

					oracle.add(username);

				}

			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Failure: "+caught);
			}
		});
		
	
		
		sb = new SuggestBox(oracle);
		hp.setStyleName("PanelBorder");
		add(hp);	
		hp.add(sb);
	
	hp.add(btnGenerate);

	}

}
