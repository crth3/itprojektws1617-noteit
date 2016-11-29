package de.hdm.itprojekt.noteit.client;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.bo.User;

public class Homepage extends VerticalPanel {
	final static DialogBox dbeditNotebookDialogBox = new DialogBox();
	public void onLoad() {

		// local User
		final User user = new User();
		user.setId(1);

		HorizontalPanel navPanel = new HorizontalPanel();
		HorizontalPanel navNotebookPanel = new HorizontalPanel();
		HorizontalPanel navNotesPanel = new HorizontalPanel();
		HorizontalPanel contentPanel = new HorizontalPanel();
		final HorizontalPanel contentNotebookPanel = new HorizontalPanel();
		final HorizontalPanel contentNotesPanel = new HorizontalPanel();

		final Notebooks notebooks = new Notebooks();
		notebooks.getAllNotebooks(1);
		contentNotebookPanel.add(notebooks.getAllNotebooks(1));
		
		final Notes notes = new Notes();
		notes.getAllNotes(1);
		contentNotesPanel.add(notes.getAllNotes(1));

		Label lbheadlineNotebookLabel = new Label("Notizbücher");
		Label lbheadlineNotesLabel = new Label("Notizen");
		Label lblTitleAddNotebook = new Label("Name des Notizbuches");

		Button btnaddNoteButton = new Button("<img src='Images/plus.png'/ width=\"20\" height=\"20\">");
		Button btnaddNotebookButton = new Button("<img src='Images/plus.png'/ width=\"20\" height=\"20\">");
//		Button btnsearchNoteButton = new Button("Notiz suchen");
		Button btnAddNewNotebook = new Button("Notizbuch erstellen");
		Button btnEditNotebook = new Button("<img src='Images/stift.png'/ width=\"20\" height=\"20\">");
		Button btnEditNote = new Button("<img src='Images/stift.png'/ width=\"20\" height=\"20\">");		

		lbheadlineNotebookLabel.setStylePrimaryName("headlineNotebookLabel");
		lbheadlineNotesLabel.setStylePrimaryName("headlineNotesLabel");

		navNotebookPanel.setStylePrimaryName("navNotebookPanel");
		navNotesPanel.setStylePrimaryName("navNotesPanel");
		contentNotebookPanel.setStylePrimaryName("contentNotebookPanel");
		contentNotesPanel.setStylePrimaryName("contentNotesPanel");

		navNotebookPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		navNotesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		contentNotebookPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		contentNotesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		navPanel.setWidth("1000px");
		contentPanel.setWidth("1000px");
		navNotebookPanel.setWidth("500px");
		navNotesPanel.setWidth("500px");
		contentNotebookPanel.setWidth("500px");
		contentNotesPanel.setWidth("500px");

		contentNotebookPanel.setHeight("300px");
		contentNotesPanel.setHeight("300px");

		navNotebookPanel.add(lbheadlineNotebookLabel);
		navNotesPanel.add(lbheadlineNotesLabel);
		navNotesPanel.add(btnaddNoteButton);
		navNotesPanel.add(btnEditNote);
		navNotebookPanel.add(btnaddNotebookButton);
		navNotebookPanel.add(btnEditNotebook);
//		navNotesPanel.add(btnsearchNoteButton);

		/**
		 * create the TextBox for Notebook Search, and include it to the Panel
		 */
		final TextBox tbSearchNotebook = new TextBox();
		tbSearchNotebook.setText("Notizbücher suchen...");
		navNotebookPanel.add(tbSearchNotebook);
		
		
		/**
		 * create the TextBox for Notebook Search, and include it to the Panel
		 */
		final TextBox tbSearchNote = new TextBox();
		tbSearchNote.setText("Notizen suchen...");
		navNotesPanel.add(tbSearchNote);
		
		

		final TextBox tbAddNewNotebook = new TextBox();

		/**
		 * add to the Panels
		 */
		navPanel.add(navNotebookPanel);
		navPanel.add(navNotesPanel);
		contentPanel.add(contentNotebookPanel);
		contentPanel.add(contentNotesPanel);
		contentNotebookPanel.add(notebooks);
		contentNotesPanel.add(notes);
		
		/**
		 * Create the DialoBox and Panel, this is the Popup for the
		 * editNotebookButton
		 */
		
		dbeditNotebookDialogBox.setGlassEnabled(true);
		dbeditNotebookDialogBox.setAnimationEnabled(true);
		dbeditNotebookDialogBox.setText("Notizbuch bearbeiten?");

		VerticalPanel editNotebook = new EditNotebook();
		editNotebook.setSpacing(40);
		dbeditNotebookDialogBox.setWidget(editNotebook);

		/**
		 * create the DialogBox
		 */
		final DialogBox dbAddNotebook = new DialogBox();
		dbAddNotebook.setGlassEnabled(true);
		dbAddNotebook.setAnimationEnabled(true);
		dbAddNotebook.setText("Notizbuch hinzufügen");
		VerticalPanel vpAddNewNotebookPanel = new VerticalPanel();
		Button btnNotebookClose = new Button("abbrechen");
		vpAddNewNotebookPanel.add(lblTitleAddNotebook);
		vpAddNewNotebookPanel.add(tbAddNewNotebook);
		vpAddNewNotebookPanel.add(btnAddNewNotebook);
		vpAddNewNotebookPanel.add(btnNotebookClose);

		vpAddNewNotebookPanel.setSpacing(40);
		dbAddNotebook.setWidget(vpAddNewNotebookPanel);

		
		btnNotebookClose.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				dbAddNotebook.hide();
				
			}
		});
		
		
		btnAddNewNotebook.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				/**
				 * create new Notebook
				 */
				notebooks.createNotebooks(tbAddNewNotebook.getText(), user);

				tbAddNewNotebook.setText("");

				/**
				 * close the Popup
				 */
				dbAddNotebook.hide();

			}
		});

		/**
		 * Create the Button and the ClickHandler
		 */
		btnaddNotebookButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// VerticalPanel editNotebook = new EditNotebook();

				dbAddNotebook.center();
				dbAddNotebook.show();

				// RootPanel.get("content").clear();
				// RootPanel.get("content").add(editNotebook);
			}
		});

		btnaddNoteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				VerticalPanel createNote = new CreateNote();

				RootPanel.get("content").clear();
				RootPanel.get("content").add(createNote);
			}
		});
		
		btnEditNote.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				VerticalPanel editNotes = new EditNotes();

				RootPanel.get("content").clear();
				RootPanel.get("content").add(editNotes);
			}
		});

		btnEditNotebook.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dbeditNotebookDialogBox.setGlassEnabled(true);
				dbeditNotebookDialogBox.setAnimationEnabled(true);
				dbeditNotebookDialogBox.setText("Notizbuch bearbeiten?");

				VerticalPanel editNotebook = new EditNotebook();
				editNotebook.setSpacing(40);
				dbeditNotebookDialogBox.setWidget(editNotebook);
				dbeditNotebookDialogBox.center();
				dbeditNotebookDialogBox.show();

			}
		});

		/**
		 * Create the ChangeHandler for TextBox for Search Notebook Function.
		 */
		tbSearchNotebook.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {

				contentNotebookPanel.clear();
				contentNotebookPanel.add(notebooks.getAllNotebooksByKeyword(1, event.getValue()));
			}
		});

//		btnsearchNoteButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				VerticalPanel searchNotes = new SearchNotes();
//
//				RootPanel.get("content").clear();
//				RootPanel.get("content").add(searchNotes);
//			}
//		});
		
		/**
		 * Create the ChangeHandler for TextBox for Search Note Function.
		 */
		tbSearchNote.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				
				contentNotesPanel.clear();
				contentNotesPanel.add(notes.getAllNotesByKeyword(1, event.getValue(), 1));
				
			}
		});
		
		

		this.add(navPanel);
		this.add(contentPanel);

	}
public static void close_db(){

	dbeditNotebookDialogBox.hide();
	
}
}
