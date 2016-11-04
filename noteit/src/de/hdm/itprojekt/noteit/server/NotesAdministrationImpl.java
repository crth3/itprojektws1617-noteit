package de.hdm.itprojekt.noteit.server;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojekt.noteit.server.db.NotePermissionMapper;
import de.hdm.itprojekt.noteit.server.db.NotebookMapper;
import de.hdm.itprojekt.noteit.server.db.NotebookPermissionMapper;
import de.hdm.itprojekt.noteit.server.db.PermissionMapper;
import de.hdm.itprojekt.noteit.server.db.SourceMapper;
import de.hdm.itprojekt.noteit.server.db.UserMapper;
import de.hdm.itprojekt.noteit.server.db.UserPermissionMapper;
import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class NotesAdministrationImpl extends RemoteServiceServlet implements NotesAdministration{

	private static final long serialVersionUID = 1L;
	
	/**
	* Referenz auf das zugehörige Nutzer-Objekt
	*/
	private User user = null;
	
	private UserMapper uMapper = null;
	//private NoteMapper nMapper = null;
	private NotebookMapper nbMapper = null;
	private SourceMapper sMapper = null;
	private PermissionMapper pMapper = null;
	private UserPermissionMapper upMapper = null;
	private NotePermissionMapper npMapper = null;
	private NotebookPermissionMapper nbpMapper = null;
	
	@Override
	public User createUser(String mail, String firstName, String lastName) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateUser(String mail, String firstName, String lastName) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteUser(int UserID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Notebook createNotebook(String title, int creatorID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateNotebook(String title, int notebookID, int editorID, ArrayList<String> userMail,
			ArrayList<Integer> permission) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteNotebook(int notebookID, int userID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Note createNote(String title, String subtitle, String text, Timestamp maturity, int creatorID, String source)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateNote(String title, String subtitle, String text, Timestamp maturity, int editorID, String source)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteNote(int noteID, int userID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ArrayList<Notebook> getAllNotebooksByUserID(int UserID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Note> getAllNotesByNotebookID(int notebookID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Notebook> findNotebooksByKeyword(int userID, String keyword) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Note> findNoteByKeyword(int userID, String keyword, int notebookID)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public User findByMail(String mail) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
