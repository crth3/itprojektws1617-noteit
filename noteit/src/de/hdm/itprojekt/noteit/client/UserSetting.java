package de.hdm.itprojekt.noteit.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class UserSetting extends DialogBox{
	
	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	VerticalPanel vpEditUserSetting = new VerticalPanel();
	
	User currentUser = new User();
	
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
	
	



}
