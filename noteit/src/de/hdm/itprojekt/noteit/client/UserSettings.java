package de.hdm.itprojekt.noteit.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class UserSettings extends DialogBox {

	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	private static Logger rootLogger = Logger.getLogger("");

	VerticalPanel vpEditUserSettings = new VerticalPanel();

	User currentUser = new User();

	/**
	 * Konstruktor mit Userobjekt als Übergabeparameter
	 * 
	 * @param user
	 */
	public UserSettings(User user) {
		this.currentUser = user;
	}

	/**
	 * Create the Labels
	 */
	Label lbFirstName = new Label("Vorname");
	Label lbLastName = new Label("Nachname");
	Label lbEmail = new Label("Email");
	Label lbAusgabeEmail = new Label();
//	Label lbAusgabeFirstNAme = new Label (); 

	/**
	 * Create the TextBox
	 */
	TextBox tbFirstName = new TextBox();
	TextBox tbLastName = new TextBox();

	/**
	 * Create Button
	 */
	Button btnabbrechen = new Button("Abbrechen");
	Button btnsichern = new Button("Sichern");

	/**
	 * Crate a Panel
	 */
	HorizontalPanel hpButtonPanel = new HorizontalPanel();

	public void onLoad() {

		setAutoHideEnabled(true);
		setGlassEnabled(true);
		setText("Profil bearbeiten");
		center();
		vpEditUserSettings.setSpacing(40);
		setWidget(vpEditUserSettings);

		//Zum testen angelegt und über ein Label ausgeben lassen (Email) 
//		rootLogger.log(Level.SEVERE, "CurrentUser" + currentUser.getMail());
		currentUser.setMail("Kim.ishola@yahoo.de");
//		currentUser.setFirstName("Kim");
//		currentUser.setLastName("Ishola");
		lbAusgabeEmail.setText(currentUser.getMail());
//		tbFirstName.setText(currentUser.getFirstName());
//		tbLastName.setText(currentUser.getLastName());
		
		
		/**
		 * Add the Labels, TextBox and Buttons
		 */
		vpEditUserSettings.add(lbFirstName);
		vpEditUserSettings.add(tbFirstName);
		vpEditUserSettings.add(lbLastName);
		vpEditUserSettings.add(tbLastName);
		vpEditUserSettings.add(lbEmail);
		vpEditUserSettings.add(lbAusgabeEmail);
		vpEditUserSettings.add(hpButtonPanel);
		hpButtonPanel.add(btnabbrechen);
		hpButtonPanel.add(btnsichern);
		
		
		

		/**
		 * Create the ClickHandler
		 */
		btnabbrechen.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				UserSettings.this.hide();

			}
		});

	}
}
