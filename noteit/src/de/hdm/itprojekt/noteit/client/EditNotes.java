package de.hdm.itprojekt.noteit.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class EditNotes extends VerticalPanel {

	/**
	 * This is the entry point method.
	 */
	public void onLoad() {
		
		/**
		 * Anlegen der Labels mit den Texboxen. Jeweils in ein HotizontalPanel
		 */
		
			HorizontalPanel titelPanel = new HorizontalPanel();
			Label titel = new Label("Titel");
			TextBox titelTextBox = new TextBox();
			titelPanel.add(titel);
			titelPanel.add(titelTextBox);
		
				HorizontalPanel subTitelPanel = new HorizontalPanel();
				Label subTitel = new Label("Subtitel");
				TextBox subTitelTextBox = new TextBox();
				subTitelPanel.add(subTitel);
				subTitelPanel.add(subTitelTextBox);
		
					HorizontalPanel teilenPanel = new HorizontalPanel();
					Label teilen = new Label("Teilen mit");
					TextBox teilenTextBox = new TextBox();
					Button hinzufuegenButton = new Button("+");
					teilenPanel.add(teilen);
					teilenPanel.add(teilenTextBox);
					teilenPanel.add(hinzufuegenButton);
		
						/**
						 * Anlegen des RadioButton
						 */
						HorizontalPanel berechtigungsPanel = new HorizontalPanel();
						Label darf = new Label("Darf:");
						RadioButton rbBerechtigungen1 = new RadioButton("myRadioGroup", "anzeigen + bearbeiten");
						RadioButton rbBerechtigungen2 = new RadioButton("myRadioGroup", "anzeigen");
						//RadioButton berechtigungen1 = new RadioButton("myRadioGroup", "foo");
						berechtigungsPanel.add(darf);
						berechtigungsPanel.add(rbBerechtigungen1);
						berechtigungsPanel.add(rbBerechtigungen2);
		
							HorizontalPanel hinzufuegenPanel = new HorizontalPanel();
							Label hinzufuegen = new Label("Hinzufügen zu");
							TextBox hinzufuegenTextBox = new TextBox();
							hinzufuegenPanel.add(hinzufuegen);
							hinzufuegenPanel.add(hinzufuegenTextBox);
		
								HorizontalPanel faelligkeitsPanel = new HorizontalPanel();
								Label faelligkeitsdatum = new Label("Fälligkeitsdatum");
								DatePicker datePicker = new DatePicker();
								faelligkeitsPanel.add(faelligkeitsdatum);
								faelligkeitsPanel.add(datePicker);
		
									HorizontalPanel textPanel = new HorizontalPanel();
									Label text = new Label("Text");
									TextArea textArea = new TextArea();
									textPanel.add(text);
									textPanel.add(textArea);
		
										HorizontalPanel buttonPanel = new HorizontalPanel();
										Button loeschen = new Button("Löschen");
										Button abbrechen = new Button("Abbrechen");
										Button sichern = new Button("Sichern");
										buttonPanel.add(loeschen);
										buttonPanel.add(abbrechen);
										buttonPanel.add(sichern);
										
			this.add(titelPanel);
			this.add(subTitelPanel);
			this.add(teilenPanel);
			this.add(berechtigungsPanel);
			this.add(hinzufuegenPanel);
			this.add(faelligkeitsPanel);
			this.add(textPanel);
			this.add(buttonPanel);
	    
	}
	
}
