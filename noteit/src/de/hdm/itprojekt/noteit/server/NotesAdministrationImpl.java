package de.hdm.itprojekt.noteit.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.apphosting.client.serviceapp.AuthService.UserPermissions;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojekt.noteit.server.db.NoteMapper;
import de.hdm.itprojekt.noteit.server.db.NotePermissionMapper;
import de.hdm.itprojekt.noteit.server.db.NotebookMapper;
import de.hdm.itprojekt.noteit.server.db.NotebookPermissionMapper;
import de.hdm.itprojekt.noteit.server.db.SourceMapper;
import de.hdm.itprojekt.noteit.server.db.UserMapper;
import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.NotePermission;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.NotebookPermission;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class NotesAdministrationImpl extends RemoteServiceServlet implements NotesAdministration {

	private static final long serialVersionUID = 1L;

	private User user = null;

	private UserMapper uMapper = null;
	private NoteMapper nMapper = null;
	private NotebookMapper nbMapper = null;
	private SourceMapper sMapper = null;
	private NotePermissionMapper npMapper = null;
	private NotebookPermissionMapper nbpMapper = null;

	Notebook nb;

	private static final Logger log = Logger.getLogger(NotesAdministrationImpl.class.getName());

	private Timestamp ts = new Timestamp(System.currentTimeMillis());

	public void init() {
		this.uMapper = UserMapper.userMapper();
		this.nMapper = NoteMapper.noteMapper();
		this.nbMapper = NotebookMapper.notebookMapper();
		this.sMapper = SourceMapper.sourceMapper();
		this.npMapper = NotePermissionMapper.notePermissionMapper();
		this.nbpMapper = NotebookPermissionMapper.notebookPermissionMapper();

	};

	public String greetServer(String input) throws IllegalArgumentException {

		return "Hello, ";
	}

	/**
	 * Standard Konstruktor
	 * 
	 * @throws IllegalArgumentException
	 */
	public NotesAdministrationImpl() throws IllegalArgumentException {
		// TODO Auto-generated constructor stub
	}

	// TODO müssen wir hier einen User zurück geben?
	@Override
	public User createUser(String mail, String firstName, String lastName) throws IllegalArgumentException {

		User user;
		User u = null;

		if (mail != null && firstName != null && lastName != null) {
			user = new User();

			user.setMail(mail);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			u = this.uMapper.insert(user);
			return u;
			// try {
			// user.setMail(mail);
			// user.setFirstName(firstName);
			// user.setLastName(lastName);
			// u = this.uMapper.insert(user);
			// } catch (Exception e) {
			// log.log(Level.SEVERE, "create user error", e);
			// }
		} else {
			return null;
		}

	}

	@Override
	public void updateUser(int userID, String mail, String firstName, String lastName) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if (userID != 0) {
			User user = findUserByID(userID);
			try {
				user.setMail(mail);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				this.uMapper.update(user);
			} catch (Exception e) {
				log.log(Level.SEVERE, "update user", e);
			}
		}

	}

	@Override
	public void deleteUser(User user) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Logger logger = Logger.getLogger("NameOfYourLogger");
		logger.log(Level.SEVERE, "DRIN");
		this.uMapper.delete(user);

	}

	// TODO methode noch in shared nodesAdministration hinzufügen?
	public User findUserByID(int userID) {
		return this.uMapper.findByID(userID);
	}

	@Override
	public User findUserByMail(String mail) throws IllegalArgumentException {
		return this.uMapper.findByEmail(mail);
	}

	/**
	 * Erstellt ein neus Notizbuch
	 * 
	 * @param title
	 *            Der Titel des Notizbuches
	 * @param creator
	 *            Der Nutzer der das Notizbuch erstellt
	 * 
	 * @return das erstellte <code>Notebook</code> Objekt
	 */
	@Override
	public Notebook createNotebook(String title, User creator) throws IllegalArgumentException {

		ts.getTime();
		nb = new Notebook();
		nb.setUserId(creator.getId());
		nb.setTitle(title);
		nb.setCreationDate(ts);
		return this.nbMapper.insert(nb);
	}

	/**
	 * Updated die Attribute eines bestehenden Notebooks Überprüft die
	 * Berechtigung des Nutzers ob er bearbeiten darf
	 */
	@Override
	public void updateNotebook(String title, int notebookID, int userId) throws IllegalArgumentException {
		
		//WICHTIG TODO übergabeparmater um "ArrayList<NotebookPermission> notebookPermissions" ergänzen WICHTIG!!!
		ArrayList<NotebookPermission> notebookPermissions = null;
		
		
		nb = new Notebook();
		nb.setId(notebookID);
		nb.setTitle(title);

		Notebook notebook = this.nbMapper.findById(notebookID);
		ArrayList<Note> allNotesFromThisNotebook = new ArrayList<Note>();
		allNotesFromThisNotebook = getAllNotesByNotebookID(notebookID, userId);
		ArrayList<NotePermission> allNotePermssionFromThisNote;
		ArrayList<NotePermission> usersWithNotePermissionInThisNotebook = new ArrayList<NotePermission>();
		Logger logger = Logger.getLogger("NameOfYourLogger");
		logger.log(Level.SEVERE, "in updateNotebook  " + notebook.getTitle());
		
		
		if (notebookPermissions != null) {
			/**
			 *  Überprüfung ob es schon Nutzer mit freigegebenen Notizen in diesem Notizbuch gibt
			 */
			for (NotebookPermission foundedNotebookPermission : notebookPermissions) {
				// für jede Notiz des Notizbuches
				for (Note foundedNote : allNotesFromThisNotebook) {
					allNotePermssionFromThisNote = npMapper.findNotePermissionByNoteId(foundedNote.getId());
					// für jede Permission der Notiz
					for (NotePermission foundedNotePermission : allNotePermssionFromThisNote) {
						// wenn die 
						if (foundedNotePermission.getUserId() == foundedNotebookPermission.getUserId()) {
							usersWithNotePermissionInThisNotebook.add(foundedNotePermission);
						}
					}
				}

			}
			
			/**
			 * NotePermission anlegen
			 */
			for (NotebookPermission foundedNotebookPermission : notebookPermissions) {
				// wenn Permission 1 ist
				if(foundedNotebookPermission.getPermission() == 1){
					// Überprüfung ob ein nutzer schon einer NotePermission in diesem Notbook hat, wenn nein eine notePermission anlegen
					for (NotePermission foundedUsers : usersWithNotePermissionInThisNotebook) {
						if(foundedUsers.getUserId() == foundedNotebookPermission.getUserId()){
							break;
						}else{
							for (Note foundedNote : allNotesFromThisNotebook) {
								NotePermission np = new NotePermission();
								np.setNoteId(foundedNote.getId());
								np.setUserId(foundedNotebookPermission.getUserId());
								np.setNotePermissionId(foundedNotebookPermission.getPermission());
								npMapper.insert(np);
							}
							
						}
					}
				}else if(foundedNotebookPermission.getPermission() == 2){
					// Überprüfung ob ein nutzer schon einer NotePermission in diesem Notbook hat, wenn nein eine notePermission anlegen
					for (NotePermission foundedUsers : usersWithNotePermissionInThisNotebook) {
						if(foundedUsers.getUserId() == foundedNotebookPermission.getUserId()){
							if(foundedUsers.getNotePermissionId() == 1){
								foundedUsers.setNotePermissionId(2);
								npMapper.update(foundedUsers);
							}
						}else{
							for (Note foundedNote : allNotesFromThisNotebook) {
								NotePermission np = new NotePermission();
								np.setNoteId(foundedNote.getId());
								np.setUserId(foundedNotebookPermission.getUserId());
								np.setNotePermissionId(foundedNotebookPermission.getPermission());
								npMapper.insert(np);
							}
							
						}
					}
				}else if(foundedNotebookPermission.getPermission() == 3){
					// Überprüfung ob ein nutzer schon einer NotePermission in diesem Notbook hat, wenn nein eine notePermission anlegen
					for (NotePermission foundedUsers : usersWithNotePermissionInThisNotebook) {
						if(foundedUsers.getUserId() == foundedNotebookPermission.getUserId()){
							if(foundedUsers.getNotePermissionId() == 1 || foundedUsers.getNotePermissionId() == 2){
								foundedUsers.setNotePermissionId(3);
								npMapper.update(foundedUsers);
							}
						}else{
							for (Note foundedNote : allNotesFromThisNotebook) {
								NotePermission np = new NotePermission();
								np.setNoteId(foundedNote.getId());
								np.setUserId(foundedNotebookPermission.getUserId());
								np.setNotePermissionId(foundedNotebookPermission.getPermission());
								npMapper.insert(np);
							}
							
						}
					}
				}
			}
		}
		
		//Überprüfen der Berechtigung zum bearbeiten
		if (notebook.getUserId() == userId) {
			this.nbMapper.edit(nb);
			logger.log(Level.SEVERE, "SUCCESS");
		} else {
			ArrayList<NotebookPermission> notebookPermissionList = this.nbpMapper.findNotebookPermissionByNotebookId(notebookID);
			for (NotebookPermission findResult : notebookPermissionList) {
				if (findResult.getUserId() == userId) {
					if (findResult.getPermission() > 1) {
						this.nbMapper.edit(nb);
						logger.log(Level.SEVERE, "SUCCESS");
					} else {
						logger.log(Level.SEVERE, "ERROR: keine Berechtigung");
					}
				}

			}

		}
	}
	

	/**
	 * Löscht ein bestehendes Notebook und alle Freigegebenen notebooks
	 * Überprüft die Berechtigung des Nutzers ob er bearbeiten darf
	 */
	// TODO Notizen des Notebooks müssen auch noch gelöscht werden
	@Override
	public void deleteNotebook(int notebookID, int userID) throws IllegalArgumentException {

		Logger logger = Logger.getLogger("NameOfYourLogger");
		logger.log(Level.SEVERE, "in deleteNotebook");
		
		System.out.println("notebookID" + notebookID);
		
		ArrayList<NotebookPermission> notebookPermissions = this.nbpMapper
				.findNotebookPermissionByNotebookId(notebookID);
		Notebook currentNotebook = new Notebook();
		currentNotebook = this.nbMapper.findById(notebookID);
		System.out.println("current Notebook.getUserId: " +currentNotebook.getUserId());
		System.out.println("übergebene UserID: " + userID);
		ArrayList<Note> allNotesByNotebookID = this.nMapper.findNotesByNotebookId(notebookID);

		try {
			// Wenn der selbe derdas Notebookerstellt hat, es löschen möchte
			if (currentNotebook.getUserId() == userID) { 
				System.out.println("userID = notebook.getUserId");
				// Wenn das Notizbuch nochNotizen enthält
				if (allNotesByNotebookID != null) { 
					
					deleteAllNotesByNotebookID(userID, notebookID);
					System.out.println("alle Notizen gelöscht");
				}
				// wenn es Permissions gibt
				if (notebookPermissions != null) { 
					System.out.println("es gibt permissions");
					for (NotebookPermission foundedNotebookPermission : notebookPermissions) {
						//lösche zuerst alle Permissions
						this.nbpMapper.delete(foundedNotebookPermission); 
					}
					// lösche das Notebook
					System.out.println("Notizbuch löschen");
					this.nbMapper.delete(currentNotebook); 
				} else {
					System.out.println("Notizbuch löschen ohne permissions");
					// wenn es keine permission gibt, lösche das notebook
					this.nbMapper.delete(currentNotebook); 
				}

			} else if(notebookPermissions != null && currentNotebook.getUserId() != userID) {
				for (NotebookPermission foundedNotebookPermission : notebookPermissions) {
					if (userID == foundedNotebookPermission.getUserId()) {
						// wenn Berechtigung für den übergebenen User 3 ist, der das Notebook löschen möchte
						if (foundedNotebookPermission.getPermission() == 3) { 
							// Wenn das Notizbuch noch Notizen enthält
							if (allNotesByNotebookID != null) { 
								deleteAllNotesByNotebookID(userID, notebookID);
							}
							for (NotebookPermission foundedNotebookPermissions : notebookPermissions) {
								// Wenn UserID nicht der des übergebenen ist,lösche seine Berechtigung
								if (userID != foundedNotebookPermissions.getUserId()) { 
														
									this.nbpMapper.delete(foundedNotebookPermissions);
								}
							}
							System.out.println("Lösche eigene Notebook Permission");
							this.nbpMapper.delete(foundedNotebookPermission);
							System.out.println("Lösche Notebook");
							this.nbMapper.delete(currentNotebook);
						}
						System.out
								.println("Notebook Permission kann aufgrund der Berechtigung nicht gelöscht werden ");
					}
				}
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "delete Notebook", e);
		}

	}

	@Override
	public Note createNote(String title, String subtitle, String text, Date maturity, User u, String source, int notebookID)
			throws IllegalArgumentException {

		System.out.println("User: "+ u);
		System.out.println("UserID: "+u.getId());
		ts.getTime();
		Note note = new Note();
		note.setTitle(title);
		note.setSubTitle(subtitle);
		note.setText(text);
		note.setMaturityDate(maturity);
		note.setSource(source);
		note.setUserId(u.getId());
		note.setCreationDate(ts);
		note.setNotebookId(notebookID);
		return this.nMapper.insert(note);

	}

	@Override
	public void updateNote(String title, String subtitle, String text, Timestamp maturity, int editorID, String source, int notebookID, int noteID)
			throws IllegalArgumentException {
		
		Note note = new Note();
		// note.setCreator(creatorID); //Int oder Objekt?
		note.setId(noteID);
		note.setTitle(title);
		note.setSubTitle(subtitle);
		note.setText(text);
		note.setMaturityDate(maturity);
		note.setSource(source);
		note.setModificationDate(ts);
		note.setNotebookId(notebookID);
		// note.setUserId(creator.getId());
		this.nMapper.update(note);

	}
/**
 * Note und dazugeh�rige Permissions l�schen
 */
	@Override
	public void deleteNote(int noteID, int userID) throws IllegalArgumentException {
		ArrayList<NotePermission> ArrayListNotePermission = this.npMapper.findNotePermissionByNoteId(noteID);
		Note note = this.nMapper.findById(noteID);
		System.out.println("input:"+noteID + "User"+userID+"db daten:"+note.getUserId());
		try {
			if (note.getUserId() == userID) {
				if (ArrayListNotePermission != null) {
					for (NotePermission foundNotePermission : ArrayListNotePermission) {
						this.npMapper.delete(foundNotePermission);
					}
				}
				this.nMapper.delete(note);
			}

			else if (ArrayListNotePermission != null) {
				for (NotePermission foundNotePermission : ArrayListNotePermission) {
					if (userID == foundNotePermission.getUserId() && foundNotePermission.getPermission() == 3) {
						NotePermission currentPermission = foundNotePermission;
						for (NotePermission foundNotePermission1 : ArrayListNotePermission) {
							if (foundNotePermission1.getId() != currentPermission.getId()) {
								this.npMapper.delete(foundNotePermission1);
							}

							this.npMapper.delete(currentPermission);
							this.nMapper.delete(note);
						}
						break;
					}

				}

			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "delete Note", e);
		}

	}

	@Override
	public void deleteAllNotesByNotebookID(int userID, int notebookID) throws IllegalArgumentException {
		ArrayList<Note> allNotesByNotebookID = this.nMapper.findNotesByNotebookId(notebookID);

		for (Note foundedNote : allNotesByNotebookID) {
			deleteNote(foundedNote.getId(), userID);
		}
	}

	/**
	 * Gibt alle Notizbücher eines Nutzers zurück
	 */
	@Override
	public ArrayList<Notebook> getAllNotebooksByUserID(int userID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Notebook finalNotebook = new Notebook();
		finalNotebook.setId(0);
		finalNotebook.setUserId(0);
		finalNotebook.setTitle("Für mich geteilte Notizen");
		finalNotebook.setCreationDate(ts);
		
		ArrayList<Notebook> createdNotebooks = new ArrayList<Notebook>();
		createdNotebooks.add(finalNotebook);
		
		ArrayList<Notebook> allNotebooks = this.nbMapper.findNotebooksByUserID(userID);
		ArrayList<NotebookPermission> sharedNotebooks = this.nbpMapper.findNotebookPermissionByUserId(userID);
		for (Notebook foundedNotebooks : allNotebooks) {
			createdNotebooks.add(foundedNotebooks);
		}
		for (NotebookPermission foundedNotebookPermission : sharedNotebooks) {
			createdNotebooks.add(nbMapper.findById(foundedNotebookPermission.getNotebookId()));
		}
		

		return createdNotebooks;
	}

	/**
	 * Gibt alles Notizen eines Notizbuches zurück
	 */
	@Override
	public ArrayList<Note> getAllNotesByNotebookID(int notebookID, int userID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Note> currentNotes = this.nMapper.findNotesByNotebookId(notebookID);
		ArrayList<NotePermission> notesPermission = this.npMapper.findNotePermissionByUserId(userID);
		ArrayList<Note> allFoundedNotes = new ArrayList<Note>();
		ArrayList<Note> sharedNotes= new ArrayList<Note>();
		
		if(notebookID == 0){
			if(notesPermission != null){ 
				for (NotePermission foundedNotePermission : notesPermission) {
					allFoundedNotes.add(this.nMapper.findById(foundedNotePermission.getNoteId()));
					System.out.println("Notiz titel: "+this.nMapper.findById(foundedNotePermission.getNoteId()).getText());
				}
			}
			return allFoundedNotes;
		}else if(this.nbMapper.findById(notebookID).getUserId() != userID){
			if(notesPermission != null){ 
				for (NotePermission foundedNotePermission : notesPermission) {
					allFoundedNotes.add(this.nMapper.findById(foundedNotePermission.getNoteId()));
				}
			}
			
			for (Note foundedNote : allFoundedNotes) {
				if(foundedNote.getNotebookId() == notebookID){
					sharedNotes.add(foundedNote);
				}
			}
			return sharedNotes;
		}else{
			
			return currentNotes;
		}
		
	}

	/**
	 * Gibt alle Notizbücher eines Nutzers zurück die das Suchwort (keyword)
	 * enthalten.
	 */
	@Override
	public ArrayList<Notebook> findNotebooksByKeyword(int userID, String keyword) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Notebook> allNotebooksFromThisUser = getAllNotebooksByUserID(userID);
		ArrayList<Notebook> notebooksWithKeyword = new ArrayList<Notebook>();

		if (allNotebooksFromThisUser != null) {

			for (Notebook foundedNotebook : allNotebooksFromThisUser) {
				String title = foundedNotebook.getTitle().toLowerCase();
				if (title.contains(keyword)) {
					notebooksWithKeyword.add(foundedNotebook);
				}

			}
		} else {
			// TODO funktioniert das?
			notebooksWithKeyword = null;
		}
		return notebooksWithKeyword;
	}

	/**
	 * Gibt alle Notizen eines Notizbuches zurück die das Suchwort (keyword)
	 * enthalten.
	 */
	@Override
	public ArrayList<Note> findNoteByKeyword(int userID, String keyword, int notebookID)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Note> allNotesFromThisNotebook = this.nMapper.findNotesByNotebookId(notebookID);
		ArrayList<Note> notesWithKeyword = new ArrayList<Note>();

		if (allNotesFromThisNotebook != null) {

			for (Note foundedNotes : allNotesFromThisNotebook) {
				String title = foundedNotes.getTitle().toLowerCase();
				String subTitle = foundedNotes.getSubTitle().toLowerCase();
				String text = foundedNotes.getText().toLowerCase();
				if (title.contains(keyword)) {
					notesWithKeyword.add(foundedNotes);
				} else if (subTitle.contains(keyword)) {
					notesWithKeyword.add(foundedNotes);
				} else if (text.contains(keyword)) {
					notesWithKeyword.add(foundedNotes);
				}

			}
		} else {
			// TODO funktioniert das?
			notesWithKeyword = null;
		}
		return notesWithKeyword;
	}

	@Override
	public ArrayList<Note> getAllNotes() throws IllegalArgumentException {
		return nMapper.findAllNotes();
	}
	
	public ArrayList<User> getAllPermittedUsersByNotebookID(int notebookID){
		ArrayList<NotebookPermission> allUsersWithPermission = nbpMapper.findNotebookPermissionByNotebookId(notebookID);
		ArrayList<User> permittedUsers = new ArrayList<User>();
		for (NotebookPermission foundedNotebookPermission : allUsersWithPermission) {
			User user = new User();
			user = uMapper.findByID(foundedNotebookPermission.getUserId());
			user.setPermissionID(foundedNotebookPermission.getPermission());
			permittedUsers.add(user);
		}
		return permittedUsers;
	}

	@Override
	public boolean setUserNotebookPermission(String mail, int permissionID, int notebookID) throws IllegalArgumentException {
		System.out.println("Email: " + mail);
		System.out.println("permissionID: " + permissionID);
		User user = uMapper.findByEmail(mail);
		if(user == null){
			System.out.println("user nicht vorhanden");
			return false;
		}else{
			System.out.println("user: "+user.getMail());
		}
		 
		ArrayList<NotebookPermission> NotebookPermissions = nbpMapper.findNotebookPermissionByNotebookId(notebookID);
		NotebookPermission nbp = new NotebookPermission();
		boolean updated = false;

		
		nbp.setPermission(permissionID);
		nbp.setUserId(user.getId());
		nbp.setNotebookId(notebookID);
		for (NotebookPermission foundedNotebookPermission : NotebookPermissions) {
			 if(user.getId() == foundedNotebookPermission.getUserId() && permissionID != foundedNotebookPermission.getPermission()){
				
				 foundedNotebookPermission.setPermission(permissionID);
				nbpMapper.update(foundedNotebookPermission);
				updated = true;
				return true;
			}
		}
		if (updated == false){
			nbpMapper.insert(nbp);
		}
		return true;
		
		
		
	}

	@Override
	public void deleteUserNotebookPermission(String mail, int permissionID, int notebookID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

}
