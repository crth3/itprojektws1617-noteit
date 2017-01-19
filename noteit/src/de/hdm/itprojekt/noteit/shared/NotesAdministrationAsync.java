package de.hdm.itprojekt.noteit.shared;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.NotePermission;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.User;

public interface NotesAdministrationAsync {

	void createUser(String email, String firstName, String lastName, AsyncCallback<User> callback);

	void updateUser(int userID, String mail, String firstName, String lastName, AsyncCallback<Void> callback);

	void createNote(String title, String subtitle, String text, Timestamp date, User u, String source,
			int notebookID, AsyncCallback<Note> callback);

	void createNotebook(String title, User creator, AsyncCallback<Notebook> callback);

	void deleteNote(int noteID, int userID, AsyncCallback<Void> callback);

	void deleteNotebook(int notebookID, int userID, AsyncCallback<Void> callback);

	void deleteUser(User userID, AsyncCallback<Void> callback);

	void findNotebooksByKeyword(int userID, String keyword, AsyncCallback<ArrayList<Notebook>> callback);

	void findNoteByKeyword(int userID, String keyword, int notebookID, AsyncCallback<ArrayList<Note>> callback);

	void getAllNotebooksByUserID(int UserID, AsyncCallback<ArrayList<Notebook>> callback);

	void getAllNotesByNotebookID(int notebookID, int userID, AsyncCallback<ArrayList<Note>> callback);

	void init(AsyncCallback<Void> callback);

	void updateNote(String title, String subtitle, String text, Timestamp maturity, int editorID, String source,int notebookID, Note currentNote,
			AsyncCallback<Void> callback);

	void updateNotebook(String title, Notebook currentNotebook, int userId, AsyncCallback<Void> callback);

	void findUserByMail(String mail, AsyncCallback<User> callback);

	void deleteAllNotesByNotebookID(int userID, int notebookID, AsyncCallback<Void> callback);

	void getAllNotes(AsyncCallback<ArrayList<Note>> callback);

	void getAllPermittedUsersByNotebookID(int notebookID, AsyncCallback<ArrayList<User>> callback);

	void deleteUserNotebookPermission(String mail, int permissionID,int notebookID, AsyncCallback<Void> callback);

	void setUserNotebookPermission(String mail, int permissionID, int notebookID, AsyncCallback<Boolean> callback);

	void setUserNotePermission(String mail, int permissionID, int noteID, AsyncCallback<Boolean> callback);

	void getAllPermittedUsersByNoteID(int noteID, AsyncCallback<ArrayList<User>> callback);

	void deleteUserNotePermission(String mail, int permissionID, int noteID, AsyncCallback<Void> asyncCallback);

	void findAllUser(AsyncCallback<ArrayList<User>> callback);

	void findNotePermissionByUserId(int userId, AsyncCallback<ArrayList<NotePermission>> callback);

	void findNoteById(int noteId, AsyncCallback<Note> callback);

	void findUserById(int userId, AsyncCallback<User> callback);

	void findNoteByUserId(int userId, AsyncCallback<ArrayList<Note>> callback);

	void getAllNotebooks(AsyncCallback<ArrayList<Notebook>> callback);

	void findNotebookById(int notebookId, AsyncCallback<Notebook> callback);

}
