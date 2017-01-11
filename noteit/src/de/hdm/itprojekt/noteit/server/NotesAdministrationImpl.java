package de.hdm.itprojekt.noteit.server;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.DateUtils;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ibm.icu.text.SimpleDateFormat;

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

	private UserMapper uMapper = null;
	private NoteMapper nMapper = null;
	private NotebookMapper nbMapper = null;
	private SourceMapper sMapper = null;
	private NotePermissionMapper npMapper = null;
	private NotebookPermissionMapper nbpMapper = null;

	Notebook nb;

	private static final Logger log = Logger.getLogger(NotesAdministrationImpl.class.getName());

	Timestamp ts = new Timestamp(System.currentTimeMillis());

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

		if (mail != null) {
			System.out.println("In der createUser");
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
	public void updateUser(int userId, String mail, String firstName, String lastName) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if (userId != 0) {
			User user = findUserById(userId);
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

	@Override
	public User findUserByMail(String mail) throws IllegalArgumentException {
		return this.uMapper.findByEmail(mail);
	}

	public ArrayList<User> findAllUser() throws IllegalArgumentException {

		Logger logger = Logger.getLogger("nutzer");
		// logger.log(Level.SEVERE, "finde alle Nutzer: " +
		// this.uMapper.findAllUser());

		// rrayList<User> result = null;

		return this.uMapper.findAllUser();
		// TODO Auto-generated method stub

		// User testuser = new User();
		// testuser.setFirstName("Max");
		// testuser.setLastName("Mustermann");
		//
		//
		// result.add(testuser);
		//
		// System.out.println("testuser: " + testuser);
		// System.out.println("result: " + result);
		//
		// return result;
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

		ts.setHours(0);
		ts.setMinutes(0);
		ts.setSeconds(0);
		ts.setNanos(0);

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

		nb = new Notebook();
		nb.setId(notebookID);
		nb.setTitle(title);
		nb.setModificationDate(ts);

		Notebook notebook = this.nbMapper.findById(notebookID);
		Logger logger = Logger.getLogger("NameOfYourLogger");
		logger.log(Level.SEVERE, "in updateNotebook  " + notebook.getTitle());

		// Überprüfen der Berechtigung zum bearbeiten
		if (notebook.getUserId() == userId) {
			this.nbMapper.edit(nb);
			logger.log(Level.SEVERE, "SUCCESS");
		} else {
			ArrayList<NotebookPermission> notebookPermissionList = this.nbpMapper
					.findNotebookPermissionByNotebookId(notebookID);
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

		System.out.println("notebookID: " + notebookID);

		ArrayList<NotebookPermission> notebookPermissions = this.nbpMapper
				.findNotebookPermissionByNotebookId(notebookID);
		Notebook currentNotebook = this.nbMapper.findById(notebookID);
		System.out.println("current notebook objekt: " + currentNotebook.getTitle());
		ArrayList<Note> allNotesByNotebookID = this.nMapper.findNotesByNotebookId(notebookID);
		System.out.println("current Notebook title: " + currentNotebook.getTitle());
		System.out.println("current Notebook.getUserId: " + currentNotebook.getUserId());
		System.out.println("übergebene UserID: " + userID);

		System.out.println("notes list länge " + allNotesByNotebookID.size());
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
						// lösche zuerst alle Permissions
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

			} else if (notebookPermissions != null && currentNotebook.getUserId() != userID) {
				for (NotebookPermission foundedNotebookPermission : notebookPermissions) {
					if (userID == foundedNotebookPermission.getUserId()) {
						// wenn Berechtigung für den übergebenen User 3 ist, der
						// das Notebook löschen möchte
						if (foundedNotebookPermission.getPermission() == 3) {
							// Wenn das Notizbuch noch Notizen enthält
							if (allNotesByNotebookID != null) {
								deleteAllNotesByNotebookID(userID, notebookID);
							}
							for (NotebookPermission foundedNotebookPermissions : notebookPermissions) {
								// Wenn UserID nicht der des übergebenen
								// ist,lösche seine Berechtigung
								if (userID != foundedNotebookPermissions.getUserId()) {

									this.nbpMapper.delete(foundedNotebookPermissions);
								}
							}
							System.out.println("Lösche eigene Notebook Permission");
							this.nbpMapper.delete(foundedNotebookPermission);
							System.out.println("Lösche Notebook");
							this.nbMapper.delete(currentNotebook);
						}
						System.out.println("Notebook Permission kann aufgrund der Berechtigung nicht gelöscht werden ");
					}
				}
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "delete Notebook", e);
		}

	}

	@Override
	public Note createNote(String title, String subtitle, String text, Timestamp maturity, User u, String source,
			int notebookID) throws IllegalArgumentException {

		ts.setHours(0);
		ts.setMinutes(0);
		ts.setSeconds(0);
		ts.setNanos(0);

		System.out.println("User: " + u);
		System.out.println("UserID: " + u.getId());
		System.out.println("Fälligkeitsdatum: " + maturity);
		// System.out.println("DATE Parameter: "+today);
		System.out.println("TIMESTAMP: " + ts);
		// ts.getTime();
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
	public void updateNote(String title, String subtitle, String text, Timestamp maturity, int editorID, String source,
			int notebookID, int noteID) throws IllegalArgumentException {
		System.out.println("ablaufdatum: " + maturity);

		ts.setHours(0);
		ts.setMinutes(0);
		ts.setSeconds(0);
		ts.setNanos(0);

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

		// ArrayList mit allen Permissions-Objekten einer Note laden
		ArrayList<NotePermission> ArrayListNotePermission = this.npMapper.findNotePermissionByNoteId(noteID);

		// Objekt mit der gesuchten Note anhand der Id laden
		Note note = this.nMapper.findById(noteID);

		System.out.println("input:" + noteID + "User" + userID + "db daten:" + note.getUserId());

		try {
			// Prüfe ob die UserId der UserId der Note entspricht
			if (note.getUserId() == userID) {
				// Prüfe ob ArrayList Objekte enthält
				if (ArrayListNotePermission != null) {
					// Lösche jede Permission die in der ArrayList vorhanden ist
					for (NotePermission foundNotePermission : ArrayListNotePermission) {
						this.npMapper.delete(foundNotePermission);
					}
				}
				// lösche die Note
				this.nMapper.delete(note);
			}

			else if (ArrayListNotePermission != null) {
				for (NotePermission foundNotePermission : ArrayListNotePermission) {
					if (userID == foundNotePermission.getUserId() && foundNotePermission.getPermission() == 3) {
						NotePermission currentPermission = foundNotePermission;
						for (NotePermission foundNotePermission1 : ArrayListNotePermission) {
							if (foundNotePermission1.getId() != currentPermission.getId()) {

								System.out.println("foundedNotePermission: " + foundNotePermission);
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
		Notebook tempForMeSharedNotebook = new Notebook();
		tempForMeSharedNotebook.setId(-1);
		tempForMeSharedNotebook.setUserId(0);
		tempForMeSharedNotebook.setTitle("Für mich geteilte Notizen");
		tempForMeSharedNotebook.setCreationDate(ts);

		ArrayList<Notebook> createdNotebooks = new ArrayList<Notebook>();
		createdNotebooks.add(tempForMeSharedNotebook);

		ArrayList<Notebook> allNotebooks = this.nbMapper.findNotebooksByUserID(userID);
		ArrayList<NotebookPermission> sharedNotebooks = this.nbpMapper.findNotebookPermissionByUserId(userID);
		for (Notebook foundedNotebooks : allNotebooks) {
			createdNotebooks.add(foundedNotebooks);
		}
		//freigegebene Notizen dem Notizbuch hinzufügen
		for (NotebookPermission foundedNotebookPermission : sharedNotebooks) {
			Notebook permittedNotebook = nbMapper.findById(foundedNotebookPermission.getNotebookId());
			permittedNotebook.setPermissionID(foundedNotebookPermission.getPermission());
			createdNotebooks.add(permittedNotebook);
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
		ArrayList<Note> sharedNotes = new ArrayList<Note>();
		System.out.println("getAllNotesByNotebookID");
		System.out.println("nb id: " + notebookID);
		System.out.println("usr id: " + userID);

		if (notebookID == -1) {
			if (notesPermission != null) {
				for (NotePermission foundedNotePermission : notesPermission) {
					allFoundedNotes.add(this.nMapper.findById(foundedNotePermission.getNoteId()));
					System.out.println(
							"Notiz titel: " + this.nMapper.findById(foundedNotePermission.getNoteId()).getTitle());
				}
			}
			return allFoundedNotes;
		} else if (this.nbMapper.findById(notebookID).getUserId() != userID) {
			if (notesPermission != null) {
				for (NotePermission foundedNotePermission : notesPermission) {
					allFoundedNotes.add(this.nMapper.findById(foundedNotePermission.getNoteId()));
				}
			}

			for (Note foundedNote : allFoundedNotes) {
				if (foundedNote.getNotebookId() == notebookID) {
					sharedNotes.add(foundedNote);
				}
			}
			return sharedNotes;
		} else {

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

	public ArrayList<Note> findNoteByUserId(int userId) throws IllegalArgumentException {
		return this.nMapper.findNotesByUser(userId);
	}

	public Note findNoteById(int noteId) throws IllegalArgumentException {
		return this.nMapper.findById(noteId);

	}

	public ArrayList<Note> findNoteByMaturity(Timestamp maturity) throws IllegalArgumentException {
		return this.nMapper.findNotesByMaturity(maturity);
	}

	@Override
	public ArrayList<Note> getAllNotes() throws IllegalArgumentException {
		return nMapper.findAllNotes();
	}

	public ArrayList<User> getAllPermittedUsersByNotebookID(int notebookID) {
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
	public boolean setUserNotebookPermission(String mail, int permissionID, int notebookID)
			throws IllegalArgumentException {
		System.out.println("Email: " + mail);
		System.out.println("permissionID: " + permissionID);
		User user = uMapper.findByEmail(mail);
		if (user == null) {
			System.out.println("user nicht vorhanden");
			return false;
		} else {
			System.out.println("user: " + user.getMail());
		}

		ArrayList<NotebookPermission> NotebookPermissions = nbpMapper.findNotebookPermissionByNotebookId(notebookID);
		NotebookPermission nbp = new NotebookPermission();
		boolean updated = false;

		nbp.setPermission(permissionID);
		nbp.setUserId(user.getId());
		nbp.setNotebookId(notebookID);
		for (NotebookPermission foundedNotebookPermission : NotebookPermissions) {
			if (user.getId() == foundedNotebookPermission.getUserId()
					&& permissionID != foundedNotebookPermission.getPermission()) {

				foundedNotebookPermission.setPermission(permissionID);
				nbpMapper.update(foundedNotebookPermission);
				updated = true;
				return true;
			}
		}
		if (updated == false) {
			nbpMapper.insert(nbp);
		}
		return true;

	}

	@Override
	public void deleteUserNotebookPermission(String mail, int permissionID, int notebookID)
			throws IllegalArgumentException {
		User u = uMapper.findByEmail(mail);
		for (NotebookPermission foundedNotebookPermission : nbpMapper.findNotebookPermissionByUserId(u.getId())) {
			if (foundedNotebookPermission.getNotebookId() == notebookID) {
				nbpMapper.delete(foundedNotebookPermission);
			}
		}
	}

	@Override
	public boolean setUserNotePermission(String mail, int permissionID, int noteID) throws IllegalArgumentException {
		System.out.println("Email: " + mail);
		System.out.println("permissionID: " + permissionID);
		User user = uMapper.findByEmail(mail);
		if (user == null) {
			System.out.println("user nicht vorhanden");
			return false;
		} else {
			System.out.println("user: " + user.getMail());
			System.out.println("userid: " + user.getId());
		}

		ArrayList<NotePermission> notePermissions = npMapper.findNotePermissionByNoteId(noteID);
		NotePermission np = new NotePermission();
		boolean updated = false;

		np.setPermission(permissionID);
		np.setUserId(user.getId());
		np.setNoteId(noteID);
		for (NotePermission foundedNotePermission : notePermissions) {
			if (user.getId() == foundedNotePermission.getUserId()
					&& permissionID != foundedNotePermission.getPermission()) {

				foundedNotePermission.setPermission(permissionID);
				System.out.println("Userpermission wurde geupdatet ");
				npMapper.update(foundedNotePermission);
				updated = true;
				return true;
			}
		}
		if (updated == false) {
			npMapper.insert(np);
			System.out.println("Userpermission wurde erstellt ");
		}
		return true;
	}

	@Override
	public ArrayList<User> getAllPermittedUsersByNoteID(int noteID) throws IllegalArgumentException {
		ArrayList<NotePermission> allUsersWithPermission = npMapper.findNotePermissionByNoteId(noteID);
		System.out.println("Note ID: " + noteID);
		System.out.println("Anzahl freigebener Nutzer pro Note: " + allUsersWithPermission.size());
		ArrayList<User> permittedUsers = new ArrayList<User>();
		for (NotePermission foundedNotePermission : allUsersWithPermission) {
			User user = new User();
			user = uMapper.findByID(foundedNotePermission.getUserId());
			user.setPermissionID(foundedNotePermission.getPermission());
			permittedUsers.add(user);
		}
		return permittedUsers;
	}

	@Override
	public void deleteUserNotePermission(String mail, int permissionID, int noteID) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<NotePermission> findNotePermissionByUserId(int userId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.npMapper.findNotePermissionByUserId(userId);
	}

	@Override
	public User findUserById(int userId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.uMapper.findByID(userId);
	}

	@Override
	public ArrayList<Note> findNoteByCreationDate(Timestamp creationDate) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.nMapper.findNotesByCreationDate(creationDate);
	}

	@Override
	public ArrayList<Note> findNoteByModificationDate(Timestamp modificationDate) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.nMapper.findNotesByModificationDate(modificationDate);
	}

	@Override
	public ArrayList<Notebook> getAllNotebooks() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.nbMapper.getAllNotebooks();
	}

	@Override
	public Notebook findNotebookById(int notebookId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.nbMapper.findById(notebookId);

	}

	// Absteigend sortieren nach Fälligkeitsdatum
	@Override
	public ArrayList<Note> sortNotesMaturityDesc(int notebookID) {
		System.out.println("Absteigend sortieren nach Fälligkeitsdatum : " +notebookID);
		ArrayList<Note> notesList = nMapper.findNotesByNotebookId(notebookID);
		java.util.Collections.sort(notesList, new Comparator<Note>() {

			@Override
			public int compare(Note o1, Note o2) {
				// TODO Auto-generated method stub
				if (o1.getMaturityDate() == null || o2.getMaturityDate() == null)
					return 0;
				return o2.getMaturityDate().compareTo(o1.getMaturityDate());

			}
		});
		System.out.println("Anzahl: " +notesList.size());
		return notesList;

	}

	// Aufsteigend sortieren nach Fälligkeitsdatum
	@Override
	public ArrayList<Note> sortNotesByMaturityAsc(int notebookID) {
		ArrayList<Note> notesList = nMapper.findNotesByNotebookId(notebookID);
		java.util.Collections.sort(notesList, new Comparator<Note>() {

			@Override
			public int compare(Note o1, Note o2) {
				// TODO Auto-generated method stub
				if (o1.getMaturityDate() == null || o2.getMaturityDate() == null)
					return 0;
				return o1.getMaturityDate().compareTo(o2.getMaturityDate());

			}
		});

		return notesList;

	}

	// Absteigend sortieren nach Erstelldatum
	@Override
	public ArrayList<Note> sortNotesCreationDateDesc(int notebookID) {
		ArrayList<Note> notesList = nMapper.findNotesByNotebookId(notebookID);
		java.util.Collections.sort(notesList, new Comparator<Note>() {

			@Override
			public int compare(Note o1, Note o2) {
				// TODO Auto-generated method stub
				if (o1.getCreationDate() == null || o2.getCreationDate() == null)
					return 0;
				return o2.getCreationDate().compareTo(o1.getCreationDate());

			}
		});

		return notesList;

	}

	// Aufsteigend sortieren nach Erstelldatum
	@Override
	public ArrayList<Note> sortNotesCreationDateAsc(int notebookID) {
		ArrayList<Note> notesList = nMapper.findNotesByNotebookId(notebookID);
		java.util.Collections.sort(notesList, new Comparator<Note>() {

			@Override
			public int compare(Note o1, Note o2) {
				// TODO Auto-generated method stub
				if (o1.getCreationDate() == null || o2.getCreationDate() == null)
					return 0;
				return o1.getCreationDate().compareTo(o2.getCreationDate());

			}
		});

		return notesList;

	}

	// Absteigend sortieren nach Änderungsdatum
	@Override
	public ArrayList<Note> sortNotesModificationDateDesc(int notebookID) {
		ArrayList<Note> notesList = nMapper.findNotesByNotebookId(notebookID);
		java.util.Collections.sort(notesList, new Comparator<Note>() {

			@Override
			public int compare(Note o1, Note o2) {
				// TODO Auto-generated method stub
				if (o1.getModificationDate() == null || o2.getModificationDate() == null)
					return 0;
				return o2.getModificationDate().compareTo(o1.getModificationDate());

			}
		});

		return notesList;

	}

	// Aufsteigend sortieren nach Änderungsdatum
	@Override
	public ArrayList<Note> sortNotesModificationDateAsc(int notebookID) {
		ArrayList<Note> notesList = nMapper.findNotesByNotebookId(notebookID);
		java.util.Collections.sort(notesList, new Comparator<Note>() {

			@Override
			public int compare(Note o1, Note o2) {
				// TODO Auto-generated method stub
				if (o1.getModificationDate() == null || o2.getModificationDate() == null)
					return 0;
				return o1.getModificationDate().compareTo(o2.getModificationDate());

			}
		});

		return notesList;

	}


}
