package de.hdm.itprojekt.noteit.client;

import java.sql.Timestamp;
import java.util.Date;

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

public class CreateNote extends DialogBox {

	private Timestamp timestampe;
	private NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);
	private User currentUser = new User();
	private Notes notes = new Notes();
	private Notebook currentNotebook = new Notebook();

	VerticalPanel vpAddNewNote = new VerticalPanel();

	HorizontalPanel hpTitel = new HorizontalPanel();
	HorizontalPanel hpNoteSubTitel = new HorizontalPanel();
	HorizontalPanel hpNoteText = new HorizontalPanel();
	HorizontalPanel hpNoteShare = new HorizontalPanel();
	HorizontalPanel hpNotePermission = new HorizontalPanel();
	HorizontalPanel hpAddCloseButtons = new HorizontalPanel();
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
	Button btnAddNewNote = new Button("Notiz erstellen");

	TextArea taNewNoteText = new TextArea();

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
		hpTitel.add(tbNoteTitel);

		/**
		 * Create the Panel, Label and TextBox
		 */
		
		hpNoteSubTitel.add(lblNoteSubTitel);
		hpNoteSubTitel.add(tbNoteSubTitel);

		/**
		 * Create note text field
		 */
		taNewNoteText.setText("Deine Notiz..");
		taNewNoteText.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				taNewNoteText.setText("");

			}
		});
		// hpNoteText.add(lblNoteText);
		hpNoteText.add(taNewNoteText);

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
		
		datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date date = event.getValue();
				long time = date.getTime();
				timestampe = new Timestamp(time);
//		        String dateString = DateTimeFormat.getMediumDateFormat().format(date);
//		        text.setText(dateString);
				
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
				CreateNote.this.hide();
			}
		});

		// ClickHandler Add Button
		btnAddNewNote.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				notesAdmin.createNote(tbNoteTitel.getText(), tbNoteSubTitel.getText(), taNewNoteText.getText(),
						timestampe, currentUser, "keine quelle", currentNotebook.getId(), new AsyncCallback<Note>() {
							
							@Override
							public void onSuccess(Note result) {
								CreateNote.this.hide();
								Homepage.updateNotesCellList(currentNotebook.getId());
								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								
								
							}
						});

			}
		});

		
		
		vpAddNewNote.add(hpTitel);
		vpAddNewNote.add(hpNoteSubTitel);
		vpAddNewNote.add(hpNoteText);
		vpAddNewNote.add(hpNoteMaturity);
			hpNoteMaturity.add(lblNoteMaturity);
			hpNoteMaturity.add(datePicker);
		vpAddNewNote.add(hpNoteShare);
		vpAddNewNote.add(hpAddCloseButtons);
			hpAddCloseButtons.add(btnNoteClose);
			hpAddCloseButtons.add(btnAddNewNote);
		
		vpAddNewNote.setSpacing(40);
		setWidget(vpAddNewNote);

	}

	public CreateNote(User user, Notebook notebook) {
		this.currentUser = user;
		this.currentNotebook = notebook;
	}

}
