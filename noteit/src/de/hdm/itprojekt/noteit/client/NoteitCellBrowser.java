package de.hdm.itprojekt.noteit.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.util.log.Log;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SetSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.User;

/**
 * The model that defines the nodes in the tree.
 */
public class NoteitCellBrowser implements TreeViewModel {
	private static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	private static ListDataProvider<Notebook> notebooksListDataProvider = new ListDataProvider<Notebook>();
	private static ListDataProvider<Note> notesListDataProvider = new ListDataProvider<Note>();

	private User currentUser = Noteit.currentUser;
	private static Notebook selectedNotebook = new Notebook();
	private static Note selectedNote = new Note();

	private Notebook firstNotebook = new Notebook();

	private static final NoSelectionModel<Notebook> selectionModelNotebook = new NoSelectionModel<Notebook>();
	private final NoSelectionModel<Note> selectionModelNote = new NoSelectionModel<Note>();

	private static Logger rootLogger = Logger.getLogger("");

	/**
	 * Get the {@link NodeInfo} that provides the children of the specified
	 * value.
	 */

	public <T> NodeInfo<?> getNodeInfo(T value) {

		if (value == null) {
			
			// LEVEL 0.
			// We passed null as the root value. Return the notebooks.
			Notebook addNotebook = new Notebook();
			addNotebook.setId(0);
			addNotebook.setTitle("");
			selectionModelNotebook.isSelected(addNotebook);

			notebooksListDataProvider.getList().add(addNotebook);
			notesAdmin.getAllNotebooksByUserID(currentUser.getId(), new AsyncCallback<ArrayList<Notebook>>() {

				@Override
				public void onSuccess(ArrayList<Notebook> result) {
					for (Notebook notebook : result) {
						notebooksListDataProvider.getList().add(notebook);

					}

				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}
			});

			// Return a node info that pairs the data provider and the cell.
			return new DefaultNodeInfo<Notebook>(notebooksListDataProvider, new NotebookCell(), selectionModelNotebook,
					null);

		} else if (value instanceof Notebook) {

			selectedNotebook = selectionModelNotebook.getLastSelectedObject();
			EditNotebook.setNotebook(selectedNotebook);
			EditNotebook.getAllPermittedUsersbyNotebookID(selectedNotebook.getId());
			Homepage.setSelectedNotebook(selectedNotebook);

			Homepage.editNotebookView();
			notesListDataProvider.getList().clear();
			// LEVEL 1.
			// We want the children of the notebook. Return the notes.
			if(((Notebook) value).getId() != 0){
			Note addNote = new Note();
			addNote.setId(0);
			addNote.setTitle("");
			notesListDataProvider.getList().add(addNote);
			}
			notesAdmin.getAllNotesByNotebookID(((Notebook) value).getId(), currentUser.getId(),
					new AsyncCallback<ArrayList<Note>>() {

						@Override
						public void onSuccess(ArrayList<Note> result) {

							for (Note note : result) {
								notesListDataProvider.getList().add(note);
							}

						}

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}
					});

			return new DefaultNodeInfo<Note>(notesListDataProvider, new NoteCell(), selectionModelNote, null);
		} else if (value instanceof Note) {

			// selectionModelNote.setSelected(selectionModelNote.getSelectedObject(),
			// true);
			// ShowNote.setNote(selectionModelNote.getLastSelectedObject());
			if (((Note) value).getId() != 0) {
				ShowNote.getAllPermittedUsersbyNoteID(selectionModelNote.getLastSelectedObject().getId());
				ShowNote.showNote(selectionModelNote.getLastSelectedObject());
			}
			else {
				ShowNote.tbNoteTitel.setText("");
				ShowNote.tbNoteSubTitel.setText("");
				ShowNote.content.setText("");
				ShowNote.lblHeaderTitel.setText("Neue Notiz");
			}
			Homepage.showNoteView();
		}

		return null;
	}

	/**
	 * Check if the specified value represents a leaf node. Leaf nodes cannot be
	 * opened.
	 */
	public boolean isLeaf(Object value) {
		// The leaf nodes are the songs, which are Strings.
		if (value instanceof String) {
			return true;
		}
		return false;
	}

	public static void searchNotebookByKeyword(int userID, String keyword) {

		notesAdmin.findNotebooksByKeyword(userID, keyword, new AsyncCallback<ArrayList<Notebook>>() {

			@Override
			public void onSuccess(ArrayList<Notebook> result) {
				notebooksListDataProvider.setList(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Error find notebook " + caught);

			}
		});
	}

	public static void searchNoteByKeyword(int userID, String keyword) {
		rootLogger.log(Level.SEVERE,
				"userid: " + userID + "searchtext: " + keyword + "notebook: " + selectedNotebook.getTitle());
		notesAdmin.findNoteByKeyword(userID, keyword, selectedNotebook.getId(), new AsyncCallback<ArrayList<Note>>() {

			@Override
			public void onSuccess(ArrayList<Note> result) {
				notesListDataProvider.setList(result);
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	public static void addNotebook() {
		Notebook newNotebook = new Notebook();
		newNotebook.setId(400000);
		newNotebook.setTitle("Neues Notebook");
		notebooksListDataProvider.getList().add(newNotebook);
	}

	public static void deleteNotebook() {
		int newID = selectedNotebook.getId();
		newID--;
		Window.alert("neue id "+newID);
		notebooksListDataProvider.getList().remove(selectedNotebook);
		selectedNotebook.setId(newID);
		selectionModelNotebook.setSelected(selectedNotebook, true);
	}

	public static void addNote() {
		if (selectedNotebook == null) {
			Window.alert("kein Notebook ausgewählt");
		} else {

			Note newNote = new Note();
			newNote.setId(400000);
			newNote.setTitle("Neue Notiz");
			newNote.setNotebookId(selectedNotebook.getId());
			// ShowNote.setNote(newNote);
			notesListDataProvider.getList().add(newNote);
			Window.alert("noteID" + newNote.getId());
		}
	}
	
	public static void getNotebookList(Notebook notebook){
		rootLogger.log(Level.SEVERE, "getNotebookList Methode");
		notebooksListDataProvider.getList().add(notebook);
		//notebooksListDataProvider.refresh();
	}
	public static void getNoteList(Note note){
		notesListDataProvider.getList().add(note);

	}

	public static void deleteNote() {
		notesListDataProvider.getList().remove(selectedNote.getId());
		
		
	}


}
