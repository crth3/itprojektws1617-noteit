package de.hdm.itprojekt.noteit.server;

import de.hdm.itprojekt.noteit.server.db.NotePermissionMapper;
import de.hdm.itprojekt.noteit.server.db.NotebookMapper;
import de.hdm.itprojekt.noteit.server.db.NotebookPermissionMapper;
import de.hdm.itprojekt.noteit.server.db.PermissionMapper;
import de.hdm.itprojekt.noteit.server.db.SourceMapper;
import de.hdm.itprojekt.noteit.server.db.UserMapper;
import de.hdm.itprojekt.noteit.server.db.UserPermissionMapper;

public class NotesAdministrationImpl extends RemoteServiceServlet implements NotesAdministration {

	private static final long serialVersionUID = 1L;
	
	/**
	* Referenz auf das zugehörige Nutzer-Objekt
	*/
	private User user = null;
	
	private UserMapper uMapper = null;
	private NoteMapper nMapper = null;
	private NotebookMapper nbMapper = null;
	private SourceMapper sMapper = null;
	private PermissionMapper pMapper = null;
	private UserPermissionMapper upMapper = null;
	private NotePermissionMapper npMapper = null;
	private NotebookPermissionMapper nbpMapper = null;

}
