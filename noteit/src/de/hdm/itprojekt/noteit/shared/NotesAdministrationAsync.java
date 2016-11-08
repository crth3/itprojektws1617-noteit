package de.hdm.itprojekt.noteit.shared;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.User;

public interface NotesAdministrationAsync {

	void createUser(String email, String firstName, String lastName, AsyncCallback<User> callback);

	void updateUser(int userID, String mail, String firstName, String lastName, AsyncCallback<Void> callback);

	void createNote(String title, String subtitle, String text, Timestamp maturity, int creatorID, String source,
			AsyncCallback<Note> callback);

	void createNotebook(String title, int creatorID, AsyncCallback<Notebook> callback);

	void deleteNote(int noteID, int userID, AsyncCallback<Void> callback);

	void deleteNotebook(int notebookID, int userID, AsyncCallback<Void> callback);

	void deleteUser(int UserID, AsyncCallback<Void> callback);

	void findNotebooksByKeyword(int userID, String keyword, AsyncCallback<ArrayList<Notebook>> callback);

	void findNoteByKeyword(int userID, String keyword, int notebookID, AsyncCallback<ArrayList<Note>> callback);

	void getAllNotebooksByUserID(int UserID, AsyncCallback<ArrayList<Notebook>> callback);

	void getAllNotesByNotebookID(int notebookID, AsyncCallback<ArrayList<Note>> callback);

	void init(AsyncCallback<Void> callback);

	void updateNote(String title, String subtitle, String text, Timestamp maturity, int editorID, String source,
			AsyncCallback<Void> callback);

	void updateNotebook(String title, int notebookID, int editorID, ArrayList<String> userEmail,
			ArrayList<Integer> permission, AsyncCallback<Void> callback);

	void findUserByMail(String mail, AsyncCallback<User> callback);
}
