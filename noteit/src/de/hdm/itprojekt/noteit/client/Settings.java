package de.hdm.itprojekt.noteit.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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
	 * Konstruktor mit Userobjekt als Übergabeparameter
	 * 
	 * @param user
	 */
	public Settings(User user) {
		this.currentUser = Homepage.getCurrentUser();
	}

	/**
	 * Create the Label
	 */
	Label lbFirstName = new Label("Vorname");
	Label lbLastName = new Label("Nachname");
	Label lbEmail = new Label("Email");
	Label lbAusgabeEmail = new Label("");

	/**
	 * Create the Textbox
	 */
	TextBox tbFirstName = new TextBox();
	TextBox tbLastName = new TextBox();

	/**
	 * Create the Panel
	 */
	HorizontalPanel hpButtonPanel = new HorizontalPanel();

	/**
	 * Create the Button
	 */
	Button btnAbrrechenButton = new Button("Abbrechen");
	Button btnSichernButton = new Button("Sichern");

	protected void onLoad() {

		// currentUser.setFirstName("Kim");

		vpSettingsPanel.add(lbFirstName);
		vpSettingsPanel.add(tbFirstName);
		vpSettingsPanel.add(lbLastName);
		vpSettingsPanel.add(tbLastName);
		vpSettingsPanel.add(lbEmail);
		vpSettingsPanel.add(lbAusgabeEmail);
		vpSettingsPanel.add(hpButtonPanel);
		hpButtonPanel.add(btnAbrrechenButton);
		hpButtonPanel.add(btnSichernButton);

		tbFirstName.setText(currentUser.getFirstName());
		tbLastName.setText(currentUser.getLastName());
		lbAusgabeEmail.setText(currentUser.getMail());

		this.add(lbFirstName);
		this.add(tbFirstName);
		this.add(lbLastName);
		this.add(tbLastName);
		this.add(lbEmail);
		this.add(lbAusgabeEmail);

		// Wenn ich nur das ButtonPanel add, sind die Buttons nachher im Browser
		// nach Rechts verzogen. Hier müsste man dann es dann durch css anpassen
		// oder wir lassen die Buttons untereinander erscheinen.Wie möchtest du das haben?
		//this.add(hpButtonPanel);
		
		
		this.add(btnAbrrechenButton);
		this.add(btnSichernButton);

		btnAbrrechenButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Homepage homepage = new Homepage();

				Homepage.contentPanel.remove(1);
				Homepage.contentPanel.setHeight("300px");
				Homepage.contentPanel.setWidth("500px");

				// Hier muss rein, dass die Homepage Klasse wieder geladen wird.

				// wenn das mit drin ist, lädt er das das ContentPanel nicht mal
				// mehr leer rein.
				// Homepage.contentPanel.add(homepage);

			}
		});

		btnSichernButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				notesAdmin.updateUser(currentUser.getId(), currentUser.getMail(), currentUser.getFirstName(),
						currentUser.getLastName(), new AsyncCallback<Void>() {

							// Das hat damals noch geklappt als wir es an der
							// HDM gecodet haben, Müsste das nicht eigentlich
							// noch genauso funktionieren?
							@Override
							public void onSuccess(Void result) {
								VerticalPanel homepage = new Homepage();
								Noteit.setWelcomeName(tbFirstName.getText());
								currentUser.setFirstName(tbFirstName.getText());
								currentUser.setFirstName(tbFirstName.getText());
							}

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}
						});
			}
		});

	}

}
