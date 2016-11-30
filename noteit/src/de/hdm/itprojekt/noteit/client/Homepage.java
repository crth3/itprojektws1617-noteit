package de.hdm.itprojekt.noteit.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.cellview.client.CellList;
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
import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class Homepage extends VerticalPanel {

	private final static NotesAdministrationAsync notesAdmin = GWT
			.create(NotesAdministration.class);

	// --------- Horizontal Panel -----------//
	HorizontalPanel navPanel = new HorizontalPanel();
	HorizontalPanel navNotebookPanel = new HorizontalPanel();
	HorizontalPanel navNotesPanel = new HorizontalPanel();
	HorizontalPanel contentPanel = new HorizontalPanel();
	final HorizontalPanel contentNotebookPanel = new HorizontalPanel();
	final static HorizontalPanel contentNotesPanel = new HorizontalPanel();

	// --------- Label -----------//
	Label lbheadlineNotebookLabel = new Label("Notizbücher");
	Label lbheadlineNotesLabel = new Label("Notizen");
	Label lblTitleAddNotebook = new Label("Name des Notizbuches");

	// --------- Button -----------//
	Button btnaddNoteButton = new Button(
			"<img src='Images/plus.png'/ width=\"20\" height=\"20\">");
	Button btnaddNotebookButton = new Button(
			"<img src='Images/plus.png'/ width=\"20\" height=\"20\">");
	Button btnAddNewNotebook = new Button("Notizbuch erstellen");
	Button btnEditNotebook = new Button(
			"<img src='Images/stift.png'/ width=\"20\" height=\"20\">");
	Button btnEditNote = new Button(
			"<img src='Images/stift.png'/ width=\"20\" height=\"20\">");

	// --------- Dialog Box -----------//
	final static DialogBox dbeditNotebookDialogBox = new DialogBox();
	final DialogBox dbAddNotebook = new DialogBox();
	final TextBox tbAddNewNotebook = new TextBox();

	// --------- Text Box -----------//
	final TextBox tbSearchNotebook = new TextBox();
	final TextBox tbSearchNote = new TextBox();

	// --------- Custom Classes -----------//
	final User user = new User();
	final Notebooks notebooks = new Notebooks();
	final NotebookCellList notebookCellList = new NotebookCellList();

	final CellList<Notebook> clNotebook = new NotebookCellList()
			.createNotebookCellList();
	final static CellList<Note> clNote = new NoteCellList()
			.createNoteCellList();

	static Notebook selectedNotebook;

	public void onLoad() {

		user.setId(1);

		lbheadlineNotebookLabel.setStylePrimaryName("headlineNotebookLabel");
		lbheadlineNotesLabel.setStylePrimaryName("headlineNotesLabel");

		navNotebookPanel.setStylePrimaryName("navNotebookPanel");
		navNotesPanel.setStylePrimaryName("navNotesPanel");
		contentNotebookPanel.setStylePrimaryName("contentNotebookPanel");
		contentNotesPanel.setStylePrimaryName("contentNotesPanel");

		navNotebookPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		navNotesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		contentNotebookPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		contentNotesPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

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

		/**
		 * create the TextBox for Notebook Search, and include it to the Panel
		 */

		tbSearchNotebook.setText("Notizbücher suchen...");
		navNotebookPanel.add(tbSearchNotebook);

		/**
		 * create the TextBox for Notebook Search, and include it to the Panel
		 */

		tbSearchNote.setText("Notizen suchen...");
		navNotesPanel.add(tbSearchNote);

		/**
		 * add to the Panels
		 */
		navPanel.add(navNotebookPanel);
		navPanel.add(navNotesPanel);
		contentPanel.add(contentNotebookPanel);
		contentPanel.add(contentNotesPanel);

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

		/**
		 * Add all notebooks at start to the panel
		 */
		notesAdmin.getAllNotebooksByUserID(user.getId(),
				new AsyncCallback<ArrayList<Notebook>>() {

					@Override
					public void onSuccess(ArrayList<Notebook> result) {
						System.out.println("result" + result);
						clNotebook.setRowData(result);
						contentNotebookPanel.add(clNotebook);

						notesAdmin.getAllNotesByNotebookID(
								result.get(0).getId(),
								new AsyncCallback<ArrayList<Note>>() {

									@Override
									public void onSuccess(
											ArrayList<Note> result) {
										System.out.println("result" + result);

										clNote.setRowData(result);
										contentNotesPanel.add(clNote);
									}

									@Override
									public void onFailure(Throwable caught) {
										System.out.println("Error" + caught);
									}
								});
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error" + caught);
					}
				});

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
		tbSearchNotebook
				.addValueChangeHandler(new ValueChangeHandler<String>() {

					@Override
					public void onValueChange(ValueChangeEvent<String> event) {

						notesAdmin.findNotebooksByKeyword(user.getId(),
								event.getValue(),
								new AsyncCallback<ArrayList<Notebook>>() {

									@Override
									public void onSuccess(
											ArrayList<Notebook> result) {
										clNotebook.setRowData(result);

									}

									@Override
									public void onFailure(Throwable caught) {
										System.out
												.println("Error find notebook "
														+ caught);

									}
								});
					}
				});

		/**
		 * Create the ChangeHandler for TextBox for Search Note Function.
		 */
		tbSearchNote.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {

				notesAdmin.findNoteByKeyword(user.getId(), event.getValue(),
						selectedNotebook.getId(),
						new AsyncCallback<ArrayList<Note>>() {

							@Override
							public void onSuccess(ArrayList<Note> result) {
								clNote.setRowData(result);

							}

							@Override
							public void onFailure(Throwable caught) {
								System.out.println("Error find note " + caught);

							}
						});

			}
		});

		this.add(navPanel);
		this.add(contentPanel);

	}

	/**
	 * set all notes from selected notebook
	 * 
	 * @param notebook
	 */
	public static void setNotesWhenNotebookSelected(Notebook notebook) {
		selectedNotebook = notebook;

		notesAdmin.getAllNotesByNotebookID(notebook.getId(),
				new AsyncCallback<ArrayList<Note>>() {

					@Override
					public void onSuccess(ArrayList<Note> result) {
						System.out.println("result" + result);

						clNote.setRowData(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error" + caught);
					}
				});
	};

	public static void close_db() {

		dbeditNotebookDialogBox.hide();

	}
}
