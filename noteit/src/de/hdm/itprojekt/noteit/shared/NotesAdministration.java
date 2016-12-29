package de.hdm.itprojekt.noteit.shared;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojekt.noteit.shared.bo.*;

/**
 * 
 * @author maikzimmermann
 *
 */

@RemoteServiceRelativePath("notesadministration")
public interface NotesAdministration extends RemoteService {

	/**
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;

	/**
	 * 
	 * @param mail
	 * @param firstName
	 * @param lastName
	 * @return
	 * @throws IllegalArgumentException
	 */
	public User createUser(String mail, String firstName, String lastName) throws IllegalArgumentException;

	/**
	 * 
	 * @param userID
	 * @param mail
	 * @param firstName
	 * @param lastName
	 * @throws IllegalArgumentException
	 */
	public void updateUser(int userID, String mail, String firstName, String lastName) throws IllegalArgumentException;

	/**
	 * 
	 * @param UserID
	 * @throws IllegalArgumentException
	 */
	public void deleteUser(User UserID) throws IllegalArgumentException;

	Notebook createNotebook(String title, User creator);

	// TODO Übergabeparmater für Berechtigungen überarbeiten evtl ein Objekt
	// -> auch bei update Note hinzufügen
	/**
	 * 
	 * @param title
	 * @param notebookID
	 * @param userId
	 * @param permission
	 * @throws IllegalArgumentException
	 */
	public void updateNotebook(String title, int notebookID, int userId) throws IllegalArgumentException;

	/**
	 * 
	 * @param notebookID
	 * @param userID
	 * @throws IllegalArgumentException
	 */
	public void deleteNotebook(int notebookID, int userID) throws IllegalArgumentException;

	/**
	 * 
	 * @param title
	 * @param subtitle
	 * @param text
	 * @param date
	 * @param u
	 * @param source
	 * @param notebookID
	 * @return
	 */
	Note createNote(String title, String subtitle, String text, Timestamp date, User u, String source,
			int notebookID);

	// TODO Übergabeparmeter für Berechtigungen siehe Notebook
	/**
	 * 
	 * @param title
	 * @param subtitle
	 * @param text
	 * @param maturity
	 * @param editorID
	 * @param source
	 * @throws IllegalArgumentException
	 */
	public void updateNote(String title, String subtitle, String text, Timestamp maturity, int editorID, String source,
			int notebookID, int noteID) throws IllegalArgumentException;

	/**
	 * 
	 * @param noteID
	 * @param userID
	 * @throws IllegalArgumentException
	 */
	public void deleteNote(int noteID, int userID) throws IllegalArgumentException;

	/**
	 * 
	 * @param UserID
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Notebook> getAllNotebooksByUserID(int UserID) throws IllegalArgumentException;

	/**
	 * 
	 * @param notebookID
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Note> getAllNotesByNotebookID(int notebookID, int userID) throws IllegalArgumentException;

	/**
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Note> getAllNotes() throws IllegalArgumentException;
	
	/**
	 * 
	 * @param noteId
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Note findNoteById(int noteId) throws IllegalArgumentException;

	/**
	 * 
	 * @param userID
	 * @param keyword
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Notebook> findNotebooksByKeyword(int userID, String keyword) throws IllegalArgumentException;

	/**
	 * 
	 * @param userID
	 * @param keyword
	 * @param notebookID
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Note> findNoteByKeyword(int userID, String keyword, int notebookID)
			throws IllegalArgumentException;

	/**
	 * 
	 * @param mail
	 * @return
	 * @throws IllegalArgumentException
	 */
	public User findUserByMail(String mail) throws IllegalArgumentException;
	

	
	/**
	 * 
	 * @param userID
	 * @param notebookID
	 * @throws IllegalArgumentException
	 */
	void deleteAllNotesByNotebookID(int userID, int notebookID) throws IllegalArgumentException;

	/**
	 * return all permitted user from this notbook
	 * 
	 * @param notebookID
	 * @return
	 */
	public ArrayList<User> getAllPermittedUsersByNotebookID(int notebookID) throws IllegalArgumentException;
	
	
	/**
	 *  return all permitted user from this note
	 * @param noteID
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ArrayList<User> getAllPermittedUsersByNoteID(int noteID) throws IllegalArgumentException;
	
	/**
	 * set User Notebook Permission
	 * @param mail
	 * @param permissionID
	 * @param notebookID
	 * @return
	 * @throws IllegalArgumentException
	 */
	public boolean setUserNotebookPermission(String mail, int permissionID, int notebookID) throws IllegalArgumentException;
	
	/**
	 * deleteUserNotebookPermission
	 * @param mail
	 * @param permissionID
	 * @throws IllegalArgumentException
	 */
	public void deleteUserNotebookPermission(String mail, int permissionID, int notebookID) throws IllegalArgumentException;
	
	/**
	 * 
	 * @param mail
	 * @param permissionID
	 * @param noteID
	 * @throws IllegalArgumentException
	 */
	public void deleteUserNotePermission(String mail, int permissionID, int noteID) throws IllegalArgumentException;
	
	/**
	 * set User Note Permission
	 * @param mail
	 * @param permissionID
	 * @param noteID
	 * @return
	 * @throws IllegalArgumentException
	 */
	public boolean setUserNotePermission(String mail, int permissionID, int noteID) throws IllegalArgumentException;
	
	/**
	 * get All User
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ArrayList<User> findAllUser() throws IllegalArgumentException;

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ArrayList<NotePermission> findNotePermissionByUserId(int userId) throws IllegalArgumentException;

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public User findUserById(int userId) throws IllegalArgumentException;
	
	public ArrayList<Note> findNoteByUserId(int userId) throws IllegalArgumentException;
	
	public ArrayList<Note> findNoteByMaturity(Timestamp maturity) throws IllegalArgumentException;
	public ArrayList<Note> findNoteByCreationDate(Timestamp creationDate) throws IllegalArgumentException;
	public ArrayList<Note> findNoteByModificationDate(Timestamp modificationDate) throws IllegalArgumentException;
	public ArrayList<Notebook> getAllNotebooks() throws IllegalArgumentException;
	
}
