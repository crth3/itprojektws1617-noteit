package de.hdm.itprojekt.noteit.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class UserSettings extends DialogBox{
	
	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	VerticalPanel vpEditUserSettings = new VerticalPanel();
	
	User currentUser = new User(); 
	
	/**
	 * Konstruktor mit Userobjekt als Übergabeparameter
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
	
	/**
	 * Create the TextBox
	 */
	TextBox tbFirstName = new TextBox();
	TextBox tbLastName = new TextBox();
	TextBox tbEmail = new TextBox();
	
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
		
		/**
		 * Add the Labels, TextBox and Buttons
		 */
		vpEditUserSettings.add(lbFirstName);
		vpEditUserSettings.add(tbFirstName);
		vpEditUserSettings.add(lbLastName);
		vpEditUserSettings.add(tbLastName);;
		vpEditUserSettings.add(lbEmail);;
		vpEditUserSettings.add(tbEmail);
		vpEditUserSettings.add(hpButtonPanel);
		hpButtonPanel.add(btnabbrechen);
		hpButtonPanel.add(btnsichern);

	}
}
