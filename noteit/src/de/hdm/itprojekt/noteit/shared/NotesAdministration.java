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
public interface NotesAdministration extends RemoteService{
	
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
	public User createUser(String mail,String firstName,String lastName)
		      throws IllegalArgumentException;
	
	/**
	 * 
	 * @param userID
	 * @param mail
	 * @param firstName
	 * @param lastName
	 * @throws IllegalArgumentException
	 */
	public void updateUser(int userID, String mail,String firstName,String lastName) throws IllegalArgumentException;
	
	/**
	 * 
	 * @param UserID
	 * @throws IllegalArgumentException
	 */
	public void deleteUser(int UserID) throws IllegalArgumentException;
	
	/**
	 * 
	 * @param title
	 * @param creatorID
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Notebook createNotebook(String title, int creatorID) throws IllegalArgumentException;
	
	//TODO Übergabeparmater für Berechtigungen überarbeiten evtl ein Objekt -> auch bei update Note hinzufügen
	/**
	 * 
	 * @param title
	 * @param notebookID
	 * @param editorID
	 * @param userMail
	 * @param permission
	 * @throws IllegalArgumentException
	 */
	public void updateNotebook(String title, int notebookID, int editorID, ArrayList<String> userMail, ArrayList<Integer> permission) throws IllegalArgumentException;
	
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
	 * @param maturity
	 * @param creatorID
	 * @param source
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Note createNote(String title, String subtitle, String text, Timestamp maturity, int creatorID, String source) throws IllegalArgumentException;
	
	//TODO Übergabeparmeter für Berechtigungen siehe Notebook
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
	public void updateNote(String title, String subtitle, String text, Timestamp maturity, int editorID, String source  ) throws IllegalArgumentException;
	
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
	public ArrayList<Note> getAllNotesByNotebookID(int notebookID) throws IllegalArgumentException;
	
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
	public ArrayList<Note> findNoteByKeyword(int userID, String keyword, int notebookID) throws IllegalArgumentException;
	
	/**
	 * 
	 * @param mail
	 * @return
	 * @throws IllegalArgumentException
	 */
	public User findUserByMail(String mail) throws IllegalArgumentException;
}
