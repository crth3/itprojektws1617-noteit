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
	 * Create the Textbox
	 */
	TextBox tbFirstName = new TextBox();
	TextBox tbLastName = new TextBox();

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

		// currentUser.setFirstName("Kim");
		vpSettingsPanel.add(lbFirstName);
		vpSettingsPanel.add(tbFirstName);
		vpSettingsPanel.add(lbLastName);
		vpSettingsPanel.add(tbLastName);
		vpSettingsPanel.add(lbEmail);
		vpSettingsPanel.add(lbAusgabeEmail);

		hpButtonPanel.add(btnAbrrechenButton);
		hpButtonPanel.add(btnSichernButton);
		hpButtonPanel.add(btnDeleteAccount);
		vpSettingsPanel.add(hpButtonPanel);

		
			if (currentUser.getFirstName() == "null") {
				tbFirstName.setText("");
			}else{
				tbFirstName.setText(currentUser.getFirstName());
			}
			if (currentUser.getLastName() == "null") {
				tbLastName.setText("");
			}else{
				tbLastName.setText(currentUser.getLastName());
			}

			lbAusgabeEmail.setText(currentUser.getMail());
		
			

		this.add(vpSettingsPanel);

		// Wenn ich nur das ButtonPanel add, sind die Buttons nachher im Browser
		// nach Rechts verzogen. Hier müsste man dann es dann durch css
		// anpassen
		// oder wir lassen die Buttons untereinander erscheinen.Wie möchtest du
		// das haben?
		// this.add(hpButtonPanel);

		btnAbrrechenButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Homepage homepage = new Homepage();

				Homepage.showNoteView();

				// Hier muss rein, dass die Homepage Klasse wieder geladen wird.

				// wenn das mit drin ist, lädt er das das ContentPanel nicht
				// mal
				// mehr leer rein.
				// Homepage.contentPanel.add(homepage);

			}
		});

		btnSichernButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				notesAdmin.updateUser(currentUser.getId(), currentUser.getMail(), tbFirstName.getText(),
						tbLastName.getText(), new AsyncCallback<Void>() {

							// Das hat damals noch geklappt als wir es an der
							// HDM gecodet haben, Müsste das nicht eigentlich
							// noch genauso funktionieren?
							@Override
							public void onSuccess(Void result) {
								// VerticalPanel homepage = new Homepage();
								Window.alert("Eingaben erfolgreich gespeichert");
								Homepage.showNoteView();
							}

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}
						});
			}
		});

		btnDeleteAccount.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub

				final DialogBox dlbQuestion = new DialogBox();

				dlbQuestion.setAnimationEnabled(true);
				dlbQuestion.setText("Sind sie sicher, dass Sie ihr Profil löschen möchten?");
				dlbQuestion.setWidth("300px");
				dlbQuestion.setWidget(vDialog);
				dlbQuestion.setModal(true);
				dlbQuestion.setGlassEnabled(true);
				dlbQuestion.center();

				int width = Window.getClientWidth() / 2;
				int height = Window.getClientHeight() / 2;
				dlbQuestion.setPopupPosition(width, height);
				dlbQuestion.show();

				btnYes.addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent event) {

						notesAdmin.deleteUser(currentUser, new AsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {

								Noteit.loginInfo.getLogoutUrl();
								Window.open(Noteit.loginInfo.getLogoutUrl(), "_self", "");
								Window.alert("Ihr Profil wurde gelöscht");
								Noteit.loadLogin();

							}

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}
						});
						// DialogBox ausblenden
						dlbQuestion.hide();

					}
				});

				btnNo.addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent event) {

						// DialogBox ausblenden
						dlbQuestion.hide();

					}
				});

			}
		});

	}

}