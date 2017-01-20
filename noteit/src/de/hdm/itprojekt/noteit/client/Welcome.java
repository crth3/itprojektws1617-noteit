package de.hdm.itprojekt.noteit.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class Welcome extends VerticalPanel {

	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	static VerticalPanel vpWelcomePanel = new VerticalPanel();
	private static Logger rootLogger = Logger.getLogger("");


	User currentUser = new User();
	Settings settings = new Settings();
	Label lblWelcome = new Label();
	String firstName="";
	int userId;


	/**
	 * Create the Panel
	 */
	HorizontalPanel hpButtonPanel = new HorizontalPanel();

	VerticalPanel vDialog = new VerticalPanel();
	HorizontalPanel hDialog = new HorizontalPanel();


	protected void run() {

		this.setStyleName("vpLeft");
		this.setWidth("600px");
		
		userId = Homepage.currentUser.getId();
		rootLogger.log(Level.SEVERE, "userID vor RPC: "+userId);
		firstName = Homepage.currentUser.getFirstName();

		
//		if(Homepage.currentUser.getFirstName() != settings.currentUser.getFirstName()) {
//		
//			firstName = settings.currentUser.getFirstName();
//
//		}else {
//				
//		firstName = Homepage.currentUser.getFirstName();
//		}
		
		rootLogger.log(Level.SEVERE, "firstName vor RPC: "+firstName);

		notesAdmin.findUserById(userId, new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(User result) {
				// TODO Auto-generated method stub
				rootLogger.log(Level.SEVERE, "result: "+result.getFirstName());

				
				firstName = result.getFirstName();
				
				
			}
		});
		
		rootLogger.log(Level.SEVERE, "firstName nach RPC: "+firstName);
		
		lblWelcome.setText("Herzlich Willkommen, " +firstName+"!");
		lblWelcome.setStyleName("lblWelcome");


		vDialog.setSpacing(10);
		vDialog.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vDialog.add(hDialog);
		
		vDialog.add(lblWelcome);
		
		vpWelcomePanel.add(vDialog);

		this.add(vpWelcomePanel);



	}

}