package de.hdm.itprojekt.noteit.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class Settings extends DialogBox{
	
	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	VerticalPanel vpSettingsPanel = new VerticalPanel();
	
	User currentUser = new User ();
	
	/**
	 * Konstruktor mit Userobjekt als Übergabeparameter
	 * @param user
	 */
	public Settings (User user){
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
	
	
	public void onLoad(){
		
	//currentUser.setFirstName("Kim");
		
		
		
		setAutoHideEnabled(true);
		setGlassEnabled(true);
		setText("Profil bearbeiten");
		center();
	
		vpSettingsPanel.add(lbFirstName);
		vpSettingsPanel.add(tbFirstName);
		vpSettingsPanel.add(lbLastName);
		vpSettingsPanel.add(tbLastName);
		vpSettingsPanel.add(lbEmail);
		vpSettingsPanel.add(lbAusgabeEmail);
		vpSettingsPanel.add(hpButtonPanel);
		hpButtonPanel.add(btnAbrrechenButton);
		hpButtonPanel.add(btnSichernButton);
		
		vpSettingsPanel.setSpacing(40);
		setWidget(vpSettingsPanel);
		
		tbFirstName.setText(currentUser.getFirstName());
		tbLastName.setText(currentUser.getLastName());
		lbAusgabeEmail.setText(currentUser.getMail());
		
		btnAbrrechenButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Settings.this.hide();
				
			}
		});
		
		btnSichernButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				notesAdmin.updateUser(currentUser.getId(), currentUser.getMail(), 
						currentUser.getFirstName(), currentUser.getLastName(),
						new AsyncCallback<Void>() {
							
							@Override
							public void onSuccess(Void result) {
								VerticalPanel homepage = new Homepage();
								Noteit.setWelcomeName(tbFirstName.getText());
								currentUser.setFirstName(tbFirstName.getText());
								Settings.this.hide();
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
