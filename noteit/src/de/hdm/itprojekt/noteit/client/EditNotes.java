package de.hdm.itprojekt.noteit.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class EditNotes extends DialogBox {
	
	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);
	private static Logger rootLogger = Logger.getLogger("");
	Note note = new Note();
	User currentUser = new User();
	VerticalPanel vpEditNotesPanel = new VerticalPanel();
	
	
	HorizontalPanel titelPanel = new HorizontalPanel();
	HorizontalPanel subTitelPanel = new HorizontalPanel();
	HorizontalPanel teilenPanel = new HorizontalPanel();
	HorizontalPanel berechtigungsPanel = new HorizontalPanel();
	HorizontalPanel hinzufuegenPanel = new HorizontalPanel();
	HorizontalPanel faelligkeitsPanel = new HorizontalPanel();
	HorizontalPanel textPanel = new HorizontalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();

	
	TextBox teilenTextBox = new TextBox();
	TextBox titelTextBox = new TextBox();
	TextBox subTitelTextBox = new TextBox();
	TextBox hinzufuegenTextBox = new TextBox();


	
	TextArea textArea = new TextArea();
	
	Button hinzufuegenButton = new Button("+");
	Button loeschen = new Button("Löschen");
	Button abbrechen = new Button("Abbrechen");
	Button sichern = new Button("Sichern");
	
	RadioButton rbBerechtigungen1 = new RadioButton("myRadioGroup",
			"anzeigen + bearbeiten");
	RadioButton rbBerechtigungen2 = new RadioButton("myRadioGroup",
			"anzeigen");
	
	Label darf = new Label("Darf:");
	Label subTitel = new Label("Subtitel");
	Label titel = new Label("Titel");
	Label teilen = new Label("Teilen mit");	
	Label hinzufuegen = new Label("Hinzufügen zu");
	Label faelligkeitsdatum = new Label("Fälligkeitsdatum");
	Label text = new Label("Text");
	
	DatePicker datePicker = new DatePicker();

	public EditNotes(User user) {
		this.currentUser = user;
	}
	
	public void onLoad() {

		note = Homepage.selectedNote;
		
		titelTextBox.setText(note.getText());
		textArea.setText(note.getText());
		subTitelTextBox.setText(note.getSubTitle());
		
		
	
		titelPanel.add(titel);
		titelPanel.add(titelTextBox);

		/**
		 * Create the Panel, Label and TextBox
		 */
	
	
		
		subTitelPanel.add(subTitel);
		subTitelPanel.add(subTitelTextBox);

		/**
		 * Create the Panel, Label and TextBox
		 */
	

		
		
		teilenPanel.add(teilen);
		teilenPanel.add(teilenTextBox);
		teilenPanel.add(hinzufuegenButton);

		/**
		 * Create the Panel and Label
		 */
	
		

		/**
		 * Create the RadioButton
		 */

		// RadioButton berechtigungen1 = new RadioButton("myRadioGroup", "foo");
		berechtigungsPanel.add(darf);
		berechtigungsPanel.add(rbBerechtigungen1);
		berechtigungsPanel.add(rbBerechtigungen2);

		/**
		 * Create the Panel, Label and TextBox
		 */

		hinzufuegenPanel.add(hinzufuegen);
		hinzufuegenPanel.add(hinzufuegenTextBox);

		/**
		 * Create the Panel, Label and DatePicker
		 */

		faelligkeitsPanel.add(faelligkeitsdatum);
		faelligkeitsPanel.add(datePicker);

		/**
		 * Create the Panel, Label and TextArea
		 */
		
		textPanel.add(text);
		textPanel.add(textArea);

		/**
		 * Create the Panel and the Buttons
		 */

		buttonPanel.add(loeschen);
		buttonPanel.add(abbrechen);
		buttonPanel.add(sichern);
		
		vpEditNotesPanel.add(titel);
		vpEditNotesPanel.add(titelTextBox);
		vpEditNotesPanel.add(subTitel);
		vpEditNotesPanel.add(subTitelTextBox);
		vpEditNotesPanel.add(textArea);
		vpEditNotesPanel.add(buttonPanel);
		
		vpEditNotesPanel.setSpacing(40);
		setWidget(vpEditNotesPanel);

		// ClickHandler für Löschen Button
		loeschen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rootLogger.log(Level.SEVERE, "UserID"+note.getUserId());
				notesAdmin.deleteNote(note.getId(), note.getUserId(), new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						Homepage.updateNotesCellList(note.getNotebookId());
						EditNotes.this.hide();						
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});

		// ClickHandler für Abbrechen Button
		abbrechen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditNotes.this.hide();
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
