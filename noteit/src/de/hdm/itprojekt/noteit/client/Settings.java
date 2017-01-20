package de.hdm.itprojekt.noteit.client;

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

public class Settings extends VerticalPanel {

	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	static VerticalPanel vpSettingsPanel = new VerticalPanel();

	User currentUser = new User();

	/**
	 * Create the Label
	 */
	Label lbFirstName = new Label("Vorname");
	Label lbLastName = new Label("Nachname");
	Label lbEmail = new Label("Email");
	Label lbAusgabeEmail = new Label("");

	/**
	 * Create the Panel
	 */
	HorizontalPanel hpButtonPanel = new HorizontalPanel();

	VerticalPanel vDialog = new VerticalPanel();
	HorizontalPanel hDialog = new HorizontalPanel();

	/**
	 * Create the Button
	 */
	Button btnAbrrechenButton = new Button("Abbrechen");
	Button btnSichernButton = new Button("Speichern");
	Button btnDeleteAccount = new Button("Profil löschen");

	static Button btnNo = new Button("Nein");
	static Button btnYes = new Button("Ja");

	protected void run() {

		this.setStyleName("vpLeft");
		this.setWidth("600px");

		currentUser = Homepage.getCurrentUser();

		vDialog.setSpacing(10);
		vDialog.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vDialog.add(hDialog);
		hDialog.add(btnYes);
		hDialog.add(btnNo);

		
		vpSettingsPanel.add(lbEmail);
		vpSettingsPanel.add(lbAusgabeEmail);

		hpButtonPanel.add(btnAbrrechenButton);
		hpButtonPanel.add(btnSichernButton);
		hpButtonPanel.add(btnDeleteAccount);
		vpSettingsPanel.add(hpButtonPanel);

		
					
			

		this.add(vpSettingsPanel);

		// Wenn ich nur das ButtonPanel add, sind die Buttons nachher im Browser
		// nach Rechts verzogen. Hier müsste man dann es dann durch css
		// anpassen
		// oder wir lassen die Buttons untereinander erscheinen.Wie möchtest du
		// das haben?
		// this.add(hpButtonPanel);

	
	}

}