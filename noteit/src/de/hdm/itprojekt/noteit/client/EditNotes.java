package de.hdm.itprojekt.noteit.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
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
		 * Create the Panel, Label and TextBox
		 */
		HorizontalPanel titelPanel = new HorizontalPanel();
		Label titel = new Label("Titel");
		TextBox titelTextBox = new TextBox();
		titelPanel.add(titel);
		titelPanel.add(titelTextBox);

		/**
		 * Create the Panel, Label and TextBox
		 */
		HorizontalPanel subTitelPanel = new HorizontalPanel();
		Label subTitel = new Label("Subtitel");
		TextBox subTitelTextBox = new TextBox();
		subTitelPanel.add(subTitel);
		subTitelPanel.add(subTitelTextBox);

		/**
		 * Create the Panel, Label and TextBox
		 */
		HorizontalPanel teilenPanel = new HorizontalPanel();
		Label teilen = new Label("Teilen mit");
		TextBox teilenTextBox = new TextBox();
		Button hinzufuegenButton = new Button("+");
		teilenPanel.add(teilen);
		teilenPanel.add(teilenTextBox);
		teilenPanel.add(hinzufuegenButton);

		/**
		 * Create the Panel and Label
		 */
		HorizontalPanel berechtigungsPanel = new HorizontalPanel();
		Label darf = new Label("Darf:");

		/**
		 * Create the RadioButton
		 */
		RadioButton rbBerechtigungen1 = new RadioButton("myRadioGroup",
				"anzeigen + bearbeiten");
		RadioButton rbBerechtigungen2 = new RadioButton("myRadioGroup",
				"anzeigen");
		// RadioButton berechtigungen1 = new RadioButton("myRadioGroup", "foo");
		berechtigungsPanel.add(darf);
		berechtigungsPanel.add(rbBerechtigungen1);
		berechtigungsPanel.add(rbBerechtigungen2);

		/**
		 * Create the Panel, Label and TextBox
		 */
		HorizontalPanel hinzufuegenPanel = new HorizontalPanel();
		Label hinzufuegen = new Label("Hinzufügen zu");
		TextBox hinzufuegenTextBox = new TextBox();
		hinzufuegenPanel.add(hinzufuegen);
		hinzufuegenPanel.add(hinzufuegenTextBox);

		/**
		 * Create the Panel, Label and DatePicker
		 */
		HorizontalPanel faelligkeitsPanel = new HorizontalPanel();
		Label faelligkeitsdatum = new Label("Fälligkeitsdatum");
		DatePicker datePicker = new DatePicker();
		faelligkeitsPanel.add(faelligkeitsdatum);
		faelligkeitsPanel.add(datePicker);

		/**
		 * Create the Panel, Label and TextArea
		 */
		HorizontalPanel textPanel = new HorizontalPanel();
		Label text = new Label("Text");
		TextArea textArea = new TextArea();
		textPanel.add(text);
		textPanel.add(textArea);

		/**
		 * Create the Panel and the Buttons
		 */
		HorizontalPanel buttonPanel = new HorizontalPanel();
		Button loeschen = new Button("Löschen");
		Button abbrechen = new Button("Abbrechen");
		Button sichern = new Button("Sichern");
		buttonPanel.add(loeschen);
		buttonPanel.add(abbrechen);
		buttonPanel.add(sichern);

		// ClickHandler für Löschen Button
		loeschen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

			}
		});

		// ClickHandler für Abbrechen Button
		abbrechen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				VerticalPanel homepage = new Homepage();

				RootPanel.get("content").clear();
				RootPanel.get("content").add(homepage);
			}
		});

		// ClickHandler für Sichern Button
		sichern.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

			}
		});

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
