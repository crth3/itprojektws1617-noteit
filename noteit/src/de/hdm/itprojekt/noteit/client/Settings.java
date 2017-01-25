package de.hdm.itprojekt.noteit.client;

import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.User;

/**
 * Diese Klasse erzeugt die SettingsView und deren Eventhandler
 * 
 * @author Tobias Dahms
 *
 */
public class Settings extends VerticalPanel {

	private final static NotesAdministrationAsync notesAdmin = ClientsideSettings.getAdministrationService();

	static VerticalPanel vpSettingsPanel = new VerticalPanel();
	static HorizontalPanel hpHeader = new HorizontalPanel();

	User currentUser = new User();
	public String userFirstName;

	/**
	 * Create the Label
	 */
	Label lbFirstName = new Label("Vorname");
	Label lbLastName = new Label("Nachname");
	Label lbEmail = new Label("E-Mail");
	Label lbAusgabeEmail = new Label("");
	Label lblInfoName = new Label("Bitte vervollständigen Sie Ihren Namen");

	/**
	 * Create the Textbox
	 */
	TextBox tbFirstName = new TextBox();
	TextBox tbLastName = new TextBox();

	/**
	 * Create the Panel
	 */
	HorizontalPanel hpButtonPanel = new HorizontalPanel();
	static HorizontalPanel hpSettings = new HorizontalPanel();

	VerticalPanel vDialog = new VerticalPanel();
	HorizontalPanel hDialog = new HorizontalPanel();
	static VerticalPanel vpRight = new VerticalPanel();

	/**
	 * Create the Button
	 */
	Button btnAbrrechenButton = new Button("Abbrechen");
	Button btnSichernButton = new Button("Speichern");
	Button btnDeleteAccount = new Button("Profil löschen");

	static Label lblHeaderTitel = new Label("Profil Einstellungen");

	static Button btnNo = new Button("Nein");
	static Button btnYes = new Button("Ja");

	/**
	 * Diese Methode generiert die View und wird nach der Instanziierung
	 * aufgerufen
	 */
	protected void run() {

		hpSettings.setWidth("600px");
		vpSettingsPanel.setWidth("300px");
		vpRight.setWidth("300px");

		currentUser = Homepage.getCurrentUser();
		hpHeader.setStyleName("headerDetailView");
		hpSettings.setStyleName("showDetailContent");
		lblInfoName.setStyleName("lblInfoName");
		lblHeaderTitel.setStyleName("lblHeaderTitel");
		vpSettingsPanel.setStyleName("vpLeft");
		vpRight.setStyleName("vpRight");
		vDialog.setSpacing(10);
		vDialog.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vDialog.add(hDialog);
		hDialog.add(btnYes);
		hDialog.add(btnNo);

		hpHeader.add(lblHeaderTitel);

		lblInfoName.setVisible(false);
		vpSettingsPanel.add(lblInfoName);
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
		hpSettings.add(vpSettingsPanel);
		hpSettings.add(vpRight);
		
		

		tbFirstName.getElement().setPropertyString("placeholder", "Vorname");
		tbLastName.getElement().setPropertyString("placeholder", "Nachname");

		if (currentUser.getFirstName() == "null") {
			tbFirstName.setText("");
		} else {
			tbFirstName.setText(currentUser.getFirstName());
		}
		if (currentUser.getLastName() == "null") {
			tbLastName.setText("");
		} else {
			tbLastName.setText(currentUser.getLastName());
		}
		
		if(tbFirstName.getText() == "" || tbLastName.getText() == "" ){
			lblInfoName.setVisible(true);
		}else{
			lblInfoName.setVisible(false);
		}

		lbAusgabeEmail.setText(currentUser.getMail());

		btnAbrrechenButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Homepage.hideView();
			}
		});

		btnSichernButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				
				
				notesAdmin.updateUser(currentUser.getId(), currentUser.getMail(), tbFirstName.getText(),
						tbLastName.getText(), new AsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {

								Homepage.hideView();
								if(tbFirstName.getText() == "" || tbLastName.getText() == "" ){
									lblInfoName.setVisible(true);
								}else{
									lblInfoName.setVisible(false);
								}
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
								Window.alert("Ihr Profil wurde gelöscht!");
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
		this.add(hpHeader);
		this.add(hpSettings);

	}

}