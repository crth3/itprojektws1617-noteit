package de.hdm.itprojekt.noteit.client;

import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class EditNotes extends DialogBox {
	

	private Timestamp timestampe;
	private NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);
	private User currentUser = new User();
	private Note currentNote = Homepage.selectedNote;
	private Notebook currentNotebook = Homepage.selectedNotebook;

	VerticalPanel vpEditNote = new VerticalPanel();

	HorizontalPanel hpTitel = new HorizontalPanel();
	HorizontalPanel hpNoteSubTitel = new HorizontalPanel();
	HorizontalPanel hpNoteText = new HorizontalPanel();
	HorizontalPanel hpNoteShare = new HorizontalPanel();
	HorizontalPanel hpNotePermission = new HorizontalPanel();
	HorizontalPanel hpUpdateDeleteCloseButtons = new HorizontalPanel();
	HorizontalPanel hpNoteMaturity = new HorizontalPanel();

	Label lblNoteTitel = new Label("Titel");
	Label lblNoteSubTitel = new Label("Subtitel");
	Label lblNoteShare = new Label("Teilen mit");
	Label lblNoteText = new Label("Deine Notiz");
	Label lblNoteMaturity = new Label("Fälligkeitsdatum");

	DatePicker datePicker = new DatePicker();

	TextBox tbNoteSubTitel = new TextBox();
	TextBox tbNoteTitel = new TextBox();
	TextBox tbNoteShare = new TextBox();

	Button btnNoteShareAdd = new Button("+");
	Button btnNoteClose = new Button("Abbrechen");
	Button btnUpdateNote = new Button("Notiz speichern");
	Button btnDeleteNote = new Button("Notiz löschen");

	TextArea taUpdateNoteText = new TextArea();

	RadioButton rbtnPermission1 = new RadioButton("myRadioGroup", "anzeigen + bearbeiten");
	RadioButton rbtnPermission2 = new RadioButton("myRadioGroup", "anzeigen");

	/**
	 * This is the entry point method.
	 */
	public void onLoad() {

		setAutoHideEnabled(true);
		setGlassEnabled(true);
		setText("Neue Notiz erstellen");

		/**
		 * Create the Panel, Label and TextBox
		 */

		hpTitel.add(lblNoteTitel);
		tbNoteTitel.setText(currentNote.getTitle());
		hpTitel.add(tbNoteTitel);

		/**
		 * Create the Panel, Label and TextBox
		 */

		hpNoteSubTitel.add(lblNoteSubTitel);
		tbNoteSubTitel.setText(currentNote.getSubTitle());
		hpNoteSubTitel.add(tbNoteSubTitel);

		/**
		 * Create note text field
		 */
		taUpdateNoteText.setText(currentNote.getText());
		// hpNoteText.add(lblNoteText);
		hpNoteText.add(taUpdateNoteText);

		/**
		 * Create the Panel, Label and TextBox
		 */

		hpNoteShare.add(lblNoteShare);
		hpNoteShare.add(tbNoteShare);
		hpNoteShare.add(btnNoteShareAdd);

		/**
		 * Create the Panel and Label
		 */
		HorizontalPanel berechtigungsPanel = new HorizontalPanel();
		Label darf = new Label("Darf:");

		/**
		 * Create the RadioButton
		 */
		RadioButton rbBerechtigungen1 = new RadioButton("myRadioGroup", "anzeigen + bearbeiten");
		RadioButton rbBerechtigungen2 = new RadioButton("myRadioGroup", "anzeigen");
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
		Date date = new Date(currentNote.getMaturityDate().getTime());
		datePicker.setValue(date);
		datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date date = event.getValue();
				long time = date.getTime();
				timestampe = new Timestamp(time);
				// String dateString =
				// DateTimeFormat.getMediumDateFormat().format(date);
				// text.setText(dateString);

			}
		});

		/**
		 * Create the Panel, Label and TextArea
		 */
		HorizontalPanel textPanel = new HorizontalPanel();
		Label text = new Label("Text");
		final TextArea textArea = new TextArea();
		textPanel.add(text);
		textPanel.add(textArea);

		/**
		 * Create the Panel and the Buttons
		 */
		HorizontalPanel buttonPanel = new HorizontalPanel();
		Button abbrechen = new Button("Abbrechen");
		Button sichern = new Button("Sichern");
		buttonPanel.add(abbrechen);
		buttonPanel.add(sichern);

		// ClickHandler für Abbrechen Button
		btnNoteClose.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditNotes.this.hide();
			}
		});

		// ClickHandler Add Button
		btnUpdateNote.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				notesAdmin.updateNote(tbNoteTitel.getText(), tbNoteSubTitel.getText(), taUpdateNoteText.getText(), timestampe, currentUser.getId(), "keine quelle", currentNotebook.getId(), currentNote, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						EditNotes.this.hide();
						Homepage.updateNotesCellList(currentNotebook.getId());
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
				

			}
		});
		
		btnDeleteNote.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				notesAdmin.deleteNote(currentNote.getId(), currentNote.getUserId(), new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						Homepage.updateNotesCellList(currentNote.getNotebookId());
						EditNotes.this.hide();						
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});



		vpEditNote.add(hpTitel);
		vpEditNote.add(hpNoteSubTitel);
		vpEditNote.add(hpNoteText);
		vpEditNote.add(hpNoteMaturity);
		hpNoteMaturity.add(lblNoteMaturity);
		hpNoteMaturity.add(datePicker);
		vpEditNote.add(hpNoteShare);
		vpEditNote.add(hpUpdateDeleteCloseButtons);
		hpUpdateDeleteCloseButtons.add(btnNoteClose);
		hpUpdateDeleteCloseButtons.add(btnUpdateNote);

		vpEditNote.setSpacing(40);
		setWidget(vpEditNote);
	}
	
	public EditNotes(User user){
		this.currentUser = user;
	}

}
