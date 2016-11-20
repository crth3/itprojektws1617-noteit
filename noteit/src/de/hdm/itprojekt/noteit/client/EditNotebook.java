package de.hdm.itprojekt.noteit.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EditNotebook extends VerticalPanel{
	
	public void onLoad() {
		
		HorizontalPanel titelPanel = new HorizontalPanel();
		Label titel = new Label("Titel");
		TextBox titelTextBox = new TextBox();
		titelPanel.add(titel);
		titelPanel.add(titelTextBox);
		
		HorizontalPanel teilenPanel = new HorizontalPanel();
		Label teilen = new Label("Teilen mit");
		TextBox teilenTextBox = new TextBox();
		Button hinzufuegenButton = new Button("+");
		teilenPanel.add(teilen);
		teilenPanel.add(teilenTextBox);
		teilenPanel.add(hinzufuegenButton);
		
		HorizontalPanel berechtigungsPanel = new HorizontalPanel();
		Label darf = new Label("Darf:");
		RadioButton rbBerechtigungen1 = new RadioButton("myRadioGroup", "anzeigen + bearbeiten");
		RadioButton rbBerechtigungen2 = new RadioButton("myRadioGroup", "anzeigen");
		//RadioButton berechtigungen1 = new RadioButton("myRadioGroup", "foo");
		berechtigungsPanel.add(darf);
		berechtigungsPanel.add(rbBerechtigungen1);
		berechtigungsPanel.add(rbBerechtigungen2);
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		Button loeschen = new Button("Löschen");
		Button abbrechen = new Button("Abbrechen");
		Button sichern = new Button("Sichern");
		buttonPanel.add(loeschen);
		buttonPanel.add(abbrechen);
		buttonPanel.add(sichern);
		
		this.add(titelPanel);
		this.add(teilenPanel);
		this.add(berechtigungsPanel);
		this.add(buttonPanel);
		
	}
	
}