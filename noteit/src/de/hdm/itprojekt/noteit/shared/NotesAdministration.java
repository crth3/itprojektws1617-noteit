package de.hdm.itprojekt.noteit.shared;

import java.sql.Timestamp;
import java.util.ArrayList;

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
	 * Nutzer mit Email Vor- und Nachnamen Erstellen und an die DB weiterleiten
	 * 
	 * @param mail
	 * @param firstName
	 * @param lastName
	 * @return User
	 * @throws IllegalArgumentException
	 */
	public User createUser(String mail, String firstName, String lastName) throws IllegalArgumentException;

	/**
	 * Nutzer Updaten
	 * 
	 * @param userID
	 * @param mail
	 * @param firstName
	 * @param lastName
	 * @throws IllegalArgumentException
	 */
	public void updateUser(int userID, String mail, String firstName, String lastName) throws IllegalArgumentException;

	/**
	 * Nutzer Löschen Alle Permission, Notizbücher und Notizen löschen (die
	 * keine Permissions enthalten)
	 * 
	 * @param UserID
	 * @throws IllegalArgumentException
	 */
	public void deleteUser(User UserID) throws IllegalArgumentException;

	/**
	 * Noitzbuch erstellen
	 * 
	 * @param title
	 * @param creator
	 * @return
	 */
	public Notebook createNotebook(String title, User creator);

	/**
	 * Notizbuch updaten
	 * 
	 * @param title
	 * @param currentNotebook
	 * @param userId
	 */
	void updateNotebook(String title, Notebook currentNotebook, int userId);

	/**
	 * Notizbuch löschen. Berechtigungen werden Überprüft ob gelöscht werden
	 * darf
	 * 
	 * @param notebookID
	 * @param userID
	 * @throws IllegalArgumentException
	 */
	public void deleteNotebook(int notebookID, int userID) throws IllegalArgumentException;

	/**
	 * Notiz erstellen
	 * 
	 * @param title
	 * @param subtitle
	 * @param text
	 * @param date
	 * @param u
	 * @param notebookID
	 * @return
	 */
	public Note createNote(String title, String subtitle, String text, Timestamp date, User u, int notebookID);

	/**
	 * Notiz ändern
	 * 
	 * @param title
	 * @param subtitle
	 * @param text
	 * @param maturity
	 * @param userID
	 * @param notebookID
	 * @param currentNote
	 */
	void updateNote(String title, String subtitle, String text, Timestamp maturity, int userID, int notebookID,
			Note currentNote);

	/**
	 * Notiz löschen
	 * 
	 * @param noteID
	 * @param userID
	 * @throws IllegalArgumentException
	 */
	public void deleteNote(int noteID, int userID) throws IllegalArgumentException;

	/**
	 * Alle Notizbücher des Nutzers mit der Nutzer ID holen
	 * 
	 * @param UserID
	 * @return ArrayList<Notebook>
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Notebook> getAllNotebooksByUserID(int UserID) throws IllegalArgumentException;

	/**
	 * Alle Notizen des Notizbuches mit der NotizbuchID und von dem Nutzer mit
	 * der NutzerID holen
	 * 
	 * @param notebookID
	 * @return ArrayList<Note>
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Note> getAllNotesByNotebookID(int notebookID, int userID) throws IllegalArgumentException;

	/**
	 * Alle Notizen holen
	 * 
	 * @return ArrayList<Note>
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Note> getAllNotes() throws IllegalArgumentException;

	/**
	 * Notiz mit der NotizID finden
	 * 
	 * @param noteId
	 * @return Note
	 * @throws IllegalArgumentException
	 */
	public Note findNoteById(int noteId) throws IllegalArgumentException;

	/**
	 * Notizbücher suchen anhand des übergebenen Keywords. Es können nur die
	 * Notizbücher gefunden werden, die der Nutzer erstellt hat, oder die ihm
	 * freigegeben wurden
	 * 
	 * @param userID
	 * @param keyword
	 * @return ArrayList<Notebook> mit enthaltenem Suchbegriff
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Notebook> findNotebooksByKeyword(int userID, String keyword) throws IllegalArgumentException;

	/**
	 * Notizen suchen anhand des übergebenen Keywords. Es können nur die Notiz
	 * gefunden werden, die der Nutzer erstellt hat, und die in diesem Notizbuch
	 * mit der notebookID vorhanden sind
	 * 
	 * @param userID
	 * @param keyword
	 * @param notebookID
	 * @return ArrayList<Note> mit enthaltenem Suchbegriff
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Note> findNoteByKeyword(int userID, String keyword, int notebookID)
			throws IllegalArgumentException;

	/**
	 * Nutzer suchen anhand der Email des Nutzers
	 * 
	 * @param mail
	 * @return User
	 * @throws IllegalArgumentException
	 */
	public User findUserByMail(String mail) throws IllegalArgumentException;

	/**
	 * Alle Notizen dieses Notizbuches löschen
	 * 
	 * @param userID
	 * @param notebookID
	 * @throws IllegalArgumentException
	 */
	public void deleteAllNotesByNotebookID(int userID, int notebookID) throws IllegalArgumentException;

	/**
	 * Alle berechtigten Nutzer dieses Notizbuches holen
	 * 
	 * @param notebookID
	 * @return ArrayList<User> von Nutzern mit Berechtigung
	 */
	public ArrayList<User> getAllPermittedUsersByNotebookID(int notebookID) throws IllegalArgumentException;

	/**
	 * Alle berechtigten Nutzer dieser Notiz holen
	 * 
	 * @param noteID
	 * @return ArrayList<User> von Nutzern mit Berechtigung
	 * @throws IllegalArgumentException
	 */
	public ArrayList<User> getAllPermittedUsersByNoteID(int noteID) throws IllegalArgumentException;

	/**
	 * Nutzer Berechtigung für ein Notizbuch erstellen mit folgender
	 * Berechtigung 1 = Leseberechtigung 2 = Bearbeitenberechtigung 3 =
	 * Bearbeiten und Löschen Berechtigung
	 * 
	 * @param mail
	 * @param permissionID
	 * @param notebookID
	 * @return <tt>true</tt> wenn der Nutzer gefunden wurde.s
	 * @throws IllegalArgumentException
	 */
	public boolean setUserNotebookPermission(String mail, int permissionID, int notebookID)
			throws IllegalArgumentException;

	/**
	 * Berechtiung für ein Notbook löschen
	 * 
	 * @param mail
	 *            des Nutzers der gelöscht werden soll
	 * @param permissionID
	 * @param notebookID
	 */
	public void deleteUserNotebookPermission(String mail, int permissionID, int notebookID);

	/**
	 * Berechtiung für ein Notbook löschen
	 * 
	 * @param mail
	 *            des Nutzers der gelöscht werden soll
	 * @param permissionID
	 * @param noteID
	 */
	public void deleteUserNotePermission(String mail, int permissionID, int noteID);

	/**
	 * Nutzer Berechtigung für eine Notiz erstellen mit folgender Berechtigung 1
	 * = Leseberechtigung 2 = Bearbeitenberechtigung 3 = Bearbeiten und Löschen
	 * Berechtigung
	 * 
	 * @param mail
	 * @param permissionID
	 * @param noteID
	 * @return boolean <tt>true</tt> wenn der Nutzer gefunden wurde.
	 * @throws IllegalArgumentException
	 */
	public boolean setUserNotePermission(String mail, int permissionID, int noteID) throws IllegalArgumentException;

	/**
	 * Alle Nutzer holen
	 * 
	 * @return ArrayList<User> aller Nutzers des Systems
	 * @throws IllegalArgumentException
	 */
	public ArrayList<User> findAllUser() throws IllegalArgumentException;

	/**
	 * Alle Berechtigungen des Nutzers mit übergebener ID finden
	 * 
	 * @param userId
	 * @return ArrayList<NotePermission>
	 * @throws IllegalArgumentException
	 */
	public ArrayList<NotePermission> findNotePermissionByUserId(int userId) throws IllegalArgumentException;

	/**
	 * Nutzer anhand seiner ID finden
	 * 
	 * @param userId
	 * @return
	 */
	public User findUserById(int userId) throws IllegalArgumentException;

	/**
	 * Notizen anahnd der userID finden
	 * 
	 * @param userId
	 * @return ArrayList<Note>
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Note> findNoteByUserId(int userId) throws IllegalArgumentException;

	/**
	 * Alle Notizbücher holen
	 * 
	 * @return ArrayList<Notebook> alle Notizbücher im System
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Notebook> getAllNotebooks() throws IllegalArgumentException;

	/**
	 * Hole ein Notizbuch anhand der notebookID
	 * 
	 * @param notebookId
	 * @return Notebook
	 */
	public Notebook findNotebookById(int notebookId);

}
