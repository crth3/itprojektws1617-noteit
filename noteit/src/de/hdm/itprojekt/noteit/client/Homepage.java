package de.hdm.itprojekt.noteit.client;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.TreeViewModel;
import com.google.gwt.view.client.TreeViewModel.NodeInfo;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class Homepage extends VerticalPanel {

	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	private static Logger rootLogger = Logger.getLogger("");

	// --------- Horizontal Panel -----------//
	HorizontalPanel navPanel = new HorizontalPanel();
	HorizontalPanel navNotebookPanel = new HorizontalPanel();
	HorizontalPanel navNotesPanel = new HorizontalPanel();
	static VerticalPanel contentPanel = new VerticalPanel();
	final HorizontalPanel contentNotebookPanel = new HorizontalPanel();
	final static HorizontalPanel contentNotesPanel = new HorizontalPanel();

	// --------- Label -----------//
	Label lbheadlineNotebookLabel = new Label("Notizbücher");
	Label lbheadlineNotesLabel = new Label("Notizen");

	// --------- Button -----------//
	Button btnAddNewNoteButton = new Button("<img src='Images/plus.png'/ width=\"15\" height=\"15\">");
	Button btnAddNewNotebookButton = new Button("<img src='Images/plus.png'/ width=\"15\" height=\"15\">");
	Button btnEditNotebook = new Button("<img src='Images/stift.png'/ width=\"15\" height=\"15\">");
	Button btnEditNote = new Button("<img src='Images/stift.png'/ width=\"15\" height=\"15\">");
	Button btnSearchNote = new Button("<img src='Images/Search.png'/ width=\"15\" height=\"15\">");
	Button btnSearchNotebook = new Button("<img src='Images/Search.png'/ width=\"15\" height=\"15\">");

	// --------- Text Box -----------//
	final TextBox tbSearchNotebook = new TextBox();
	final TextBox tbSearchNote = new TextBox();

	// --------- Noteit Class -----------//
	static User currentUser = new User();
	final Notebooks notebooks = new Notebooks();
	final NotebookCellList notebookCellList = new NotebookCellList();
	static Notebook selectedNotebook = new Notebook();
	static Note selectedNote = new Note();
	
	
	
    

	// --------- Cell List -----------//
	final static CellList<Notebook> clNotebook = new NotebookCellList().createNotebookCellList();
	final static CellList<Note> clNote = new NoteCellList().createNoteCellList();
	
	

	public void onLoad() {
		

		lbheadlineNotebookLabel.setStylePrimaryName("headlineNotebookLabel");
		lbheadlineNotesLabel.setStylePrimaryName("headlineNotesLabel");

		navNotebookPanel.setStylePrimaryName("navNotebookPanel");
		navNotesPanel.setStylePrimaryName("navNotesPanel");
		contentNotebookPanel.setStylePrimaryName("contentNotebookPanel");
		contentNotesPanel.setStylePrimaryName("contentNotesPanel");
		
		contentPanel.setStylePrimaryName("contentPanel");
		
		btnSearchNotebook.setStylePrimaryName("btnSearchNotebook");
		btnAddNewNoteButton.setStylePrimaryName("btnAddNewNoteButton");
		btnAddNewNotebookButton.setStylePrimaryName("btnAddNewNotebookButton");
		btnEditNotebook.setStylePrimaryName("btnEditNotebook");
		btnEditNote.setStylePrimaryName("btnEditNote");
		btnSearchNote.setStylePrimaryName("btnSearchNote");

		navNotebookPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		navNotesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		contentNotebookPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		contentNotesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		navPanel.setWidth("1000px");
		contentPanel.setWidth("1000px");
		navNotebookPanel.setWidth("500px");
		navNotesPanel.setWidth("500px");
		contentNotebookPanel.setWidth("500px");
		contentNotesPanel.setWidth("500px");

		contentNotebookPanel.setHeight("500px");
		contentNotesPanel.setHeight("500px");

		
		navNotebookPanel.add(lbheadlineNotebookLabel);
		navNotesPanel.add(lbheadlineNotesLabel);
		navNotesPanel.add(btnAddNewNoteButton);
		navNotesPanel.add(btnEditNote);
		navNotebookPanel.add(btnAddNewNotebookButton);
		navNotebookPanel.add(btnEditNotebook);

		/**
		 * create the TextBox for Notebook Search, and include it to the Panel
		 */

		tbSearchNotebook.getElement().setPropertyString("placeholder", "Notizbücher suchen...");
		tbSearchNotebook.setStylePrimaryName("tbSearchNotebook");
		navNotebookPanel.add(tbSearchNotebook);

		/**
		 * Add the Search Button to the Panel
		 */

		navNotebookPanel.add(btnSearchNotebook);

		/**
		 * create the TextBox for Notebook Search, and include it to the Panel
		 */

		tbSearchNote.getElement().setPropertyString("placeholder", "Notizen suchen...");
		tbSearchNote.setStylePrimaryName("tbSearchNote");
		navNotesPanel.add(tbSearchNote);

		/**
		 * Add the Search Button to the Panel
		 */

		navNotesPanel.add(btnSearchNote);

		/**
		 * add to the Panels
		 */
		
		navPanel.add(navNotebookPanel);
		navPanel.add(navNotesPanel);
		contentPanel.add(contentNotesPanel);
		
		getCurrentUser();

		/**
		 * Add all notebooks at start to the panel
		 */
		notesAdmin.getAllNotebooksByUserID(currentUser.getId(), new AsyncCallback<ArrayList<Notebook>>() {

			@Override
			public void onSuccess(ArrayList<Notebook> result) {
				System.out.println("result" + result);
				clNotebook.setRowData(result);
				contentNotebookPanel.add(clNotebook);
				notesAdmin.getAllNotesByNotebookID(result.get(0).getId(),currentUser.getId() , new AsyncCallback<ArrayList<Note>>() {

					@Override
					public void onSuccess(ArrayList<Note> result) {

						clNote.setRowData(result);
						contentNotesPanel.add(clNote);
						
					}

					@Override
					public void onFailure(Throwable caught) {

					}
				});
			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Error" + caught);
			}
		});
		


		/**
		 * Create the Button and the ClickHandler
		 */
		btnAddNewNotebookButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				CreateNotebook createNotebook = new CreateNotebook(currentUser);
				createNotebook.show();
				createNotebook.center();
			}
		});

		btnAddNewNoteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				CreateNote createNote = new CreateNote(currentUser, selectedNotebook);
				createNote.show();
				createNote.center();
			}
		});

		btnEditNote.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditNotes editNotes = new EditNotes(currentUser);
				editNotes.show();
				editNotes.center();
			}
		});

		btnEditNotebook.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(selectedNotebook.getId() != 0){
					EditNotebook editNotebook = new EditNotebook(currentUser);
					editNotebook.show();
					editNotebook.center();
				}else{
					Window.alert("Dieses Notizbuch kann nicht bearbeitet werden");
				}
			}
		});

		/**
		 * Create the ChangeHandler for TextBox for Search Notebook Function.
		 */
		tbSearchNotebook.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				searchNotebookByKeyword(currentUser.getId(), event.getValue());
			}
		});
		
		tbSearchNotebook.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				tbSearchNotebook.setText("");
				
			}
		});

		/**
		 * Create the ChangeHandler for TextBox for Search Note Function.
		 */
		tbSearchNote.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				searchNoteByKeyword(currentUser.getId(), event.getValue(), selectedNotebook.getId());
			}
		});

		/**
		 * Create the ClickHandler for Button for Search Notebook Function.
		 */
		btnSearchNotebook.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				searchNotebookByKeyword(currentUser.getId(), tbSearchNotebook.getText());
			}
		});

		/**
		 * Create the ClickHandler for Button for Search Note Function.
		 */
		btnSearchNote.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				searchNoteByKeyword(currentUser.getId(), tbSearchNote.getText(), selectedNotebook.getId());	

			}
		});

		this.add(navPanel);
		this.add(contentPanel);

	}
	
	public Homepage(){
		
	}
	
	public Homepage(User currentUser){
		this.currentUser = currentUser;
	}
	

	public static void showCurrentNote(VerticalPanel vpShowNote){
		
		contentPanel.remove(1);
		vpShowNote.setHeight("300px");
		vpShowNote.setWidth("500px");
		contentPanel.add(vpShowNote);
		
		
	}
	
	public static void showNotes(){
		contentPanel.remove(1);
		contentPanel.add(contentNotesPanel);
		
	}

	/**
	 * set all notes from selected notebook
	 * 
	 * @param notebook
	 */
	public static void setNotesWhenNotebookSelected(Notebook notebook) {
		selectedNotebook = notebook;
		rootLogger.log(Level.SEVERE, "ID" + notebook.getId() + "NotebookID" + notebook.getId());

		notesAdmin.getAllNotesByNotebookID(notebook.getId(),getCurrentUser().getId(), new AsyncCallback<ArrayList<Note>>() {

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
	
	public static void setSelectedNote (Note note){
		selectedNote = note;
		
	}

	public void searchNotebookByKeyword(int userID, String keyword) {
		notesAdmin.findNotebooksByKeyword(userID, keyword, new AsyncCallback<ArrayList<Notebook>>() {

			@Override
			public void onSuccess(ArrayList<Notebook> result) {
				clNotebook.setRowData(result);

			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Error find notebook " + caught);

			}
		});
	}

	public void searchNoteByKeyword(int userID, String keyword, int notebookID) {
		rootLogger.log(Level.SEVERE, "userid: " + userID + "searchtext: " + keyword + "notebookID: " + notebookID);
		notesAdmin.findNoteByKeyword(userID, keyword, notebookID, new AsyncCallback<ArrayList<Note>>() {

			@Override
			public void onSuccess(ArrayList<Note> result) {
				clNote.setRowData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	public static void updateNotebookCellList(int userID) {
		notesAdmin.getAllNotebooksByUserID(userID, new AsyncCallback<ArrayList<Notebook>>() {

			@Override
			public void onSuccess(ArrayList<Notebook> result) {
				clNotebook.setRowData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}
	public static void updateNotesCellList (int notebookId){
		
		notesAdmin.getAllNotesByNotebookID(notebookId, getCurrentUser().getId(), new AsyncCallback<ArrayList<Note>>() {

			@Override
			public void onFailure(Throwable caught) {
				
				
			}

			@Override
			public void onSuccess(ArrayList<Note> result) {
				clNote.setRowCount(0);
				clNote.setRowData(result);
				
			}
		});
	}

	public Notebook getSelectedNotebook() {
		return selectedNotebook;
	}
	
	public static User getCurrentUser(){
		currentUser.setId(1);
		currentUser.setFirstName("Max");
		currentUser.setLastName("Mustermann");
		currentUser.setMail("max@mustermann.de");
		return currentUser;
	}


}
