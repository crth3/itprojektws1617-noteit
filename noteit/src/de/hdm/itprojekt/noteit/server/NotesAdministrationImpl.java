package de.hdm.itprojekt.noteit.server;

import java.sql.Timestamp;
import java.util.ArrayList;
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

public class NotesAdministrationImpl extends RemoteServiceServlet implements NotesAdministration{
	
	private static final long serialVersionUID = 1L; 
	
	private User user = null;
	
	private UserMapper uMapper = null;
	private NoteMapper nMapper = null;
	private NotebookMapper nbMapper = null;
	private SourceMapper sMapper = null;
	private NotePermissionMapper npMapper = null;
	private NotebookPermissionMapper nbpMapper = null;
	
	Notebook nb;
	
	private static final Logger log = Logger.getLogger( NotesAdministrationImpl.class.getName() );
	
	private Timestamp ts = new Timestamp(System.currentTimeMillis());
	
	public void init(){
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
	 *  Standard Konstruktor
	 * @throws IllegalArgumentException
	 */
	public NotesAdministrationImpl() throws IllegalArgumentException {
		// TODO Auto-generated constructor stub
	}
	
	//TODO müssen wir hier einen User zurück geben?
	@Override
	public User createUser(String mail, String firstName, String lastName) throws IllegalArgumentException {

		User user;
		User u = null;

		if (mail != null && firstName != null && lastName != null) {
			user = new User();
			
			user.setMail(mail);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			u=this.uMapper.insert(user);
			return u; 
//			try {
//				user.setMail(mail);
//				user.setFirstName(firstName);
//				user.setLastName(lastName);
//			u = this.uMapper.insert(user);				
//			} catch (Exception e) {
//				log.log(Level.SEVERE, "create user error", e);
//			}
		} else {
			return null;
		}
	
	}
	
	@Override
	public void updateUser(int userID, String mail, String firstName, String lastName) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(userID != 0){
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
	
	//TODO methode noch in shared nodesAdministration hinzufügen?
	public User findUserByID(int userID){
		return this.uMapper.findByID(userID);
	}
	
	@Override
	public User findUserByMail(String mail) throws IllegalArgumentException {
		return this.uMapper.findByEmail(mail);
	}
	
	@Override
	public Notebook createNotebook(String title, User creator) throws IllegalArgumentException {
		
		ts.getTime();
		nb = new Notebook();
		nb.setUserId(creator.getId());
		nb.setTitle(title);
		nb.setCreationDate(ts);
		return this.nbMapper.insert(nb);
	}
	
	@Override
	public void updateNotebook(String title, int notebookID, int userId) throws IllegalArgumentException {
		nb = new Notebook();
		nb.setId(notebookID);
		nb.setTitle(title);
		
		Notebook notebook = this.nbMapper.findById(notebookID);
		
		Logger logger = Logger.getLogger("NameOfYourLogger");
		logger.log(Level.SEVERE, "in updateNotebook  " + notebook.getTitle());
		
		
		if(notebook.getUserId() == userId) {
			this.nbMapper.edit(nb);
			logger.log(Level.SEVERE, "SUCCESS");
		}
		else {
		ArrayList<NotebookPermission> notebookPermissionList = this.nbpMapper.findNotebookPermissionByNotebookId(notebookID);
		for (NotebookPermission findResult : notebookPermissionList) {
			if (findResult.getUserId() == userId) {
				if (findResult.getPermission() > 1) {
					this.nbMapper.edit(nb);
					logger.log(Level.SEVERE, "SUCCESS");
				}
				else {
					logger.log(Level.SEVERE, "ERROR: keine Berechtigung");
				}
			}
			
		}
		
	}
}
	
	@Override
	public void deleteNotebook(int notebookID, int userID) throws IllegalArgumentException {
		
		Logger logger = Logger.getLogger("NameOfYourLogger");
		logger.log(Level.SEVERE, "in deleteNotebook");
		
		ArrayList<NotebookPermission> notebookPermissions = this.nbpMapper.findNotebookPermissionByNotebookId(notebookID);
		Notebook currentNotebook = this.nbMapper.findById(notebookID);
		
		try {
			if(currentNotebook.getUserId() == userID){ // Wenn der selbe der das Notebook erstellt hat, es löschen möchte
				if(notebookPermissions != null){ // wenn es Permissions gibt
					for (NotebookPermission foundedNotebookPermission : notebookPermissions) {
							this.nbpMapper.delete(foundedNotebookPermission); // lösche zuerst alle Permissions
					}
					this.nbMapper.delete(currentNotebook); // lösche das Notebook
				}else{
					this.nbMapper.delete(currentNotebook); // wenn es keine permission gibt, lösche das notebook
				}
			}else if (notebookPermissions != null) {
				for (NotebookPermission foundedNotebookPermission : notebookPermissions) {
					if (userID == foundedNotebookPermission.getUserId()) { // Wenn UserID der der übegeben ist...
						if (foundedNotebookPermission.getPermission() == 3) { //wenn Berechtigung für den übergebenen User 3 ist, der das Notebook löschen möchte
							for (NotebookPermission foundedNotebookPermissions : notebookPermissions) {
								if(userID != foundedNotebookPermissions.getUserId()){ // Wenn UserID nicht der des übergeben ist lösche seine Breichtigung
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
	public Note createNote(String title, String subtitle, String text, Timestamp maturity, User u, String source)
			throws IllegalArgumentException {
		
		ts.getTime();
		Note note = new Note();
		note.setTitle(title);
		note.setSubTitle(subtitle);
		note.setText(text);
		note.setMaturityDate(maturity);
		//note.setsource //Methode fehlt noch
		//note.setUserId(creator.getId());
		note.setCreationDate(ts);
	
		return this.nMapper.insert(note);
		
	}
	@Override
	public void updateNote(String title, String subtitle, String text, Timestamp maturity, int editorID, String source)
			throws IllegalArgumentException {
		ts.getTime();
		Note note = new Note();
		//note.setCreator(creatorID); //Int oder Objekt?
		note.setTitle(title);
		note.setSubTitle(subtitle);
		note.setText(text);
		note.setMaturityDate(maturity);
		//note.setsource //Methode fehlt noch
		//note.setUserId(creator.getId());
		note.setCreationDate(ts);
	
		this.nMapper.update(note);
		
	}
	@Override
	public void deleteNote(int noteID, int userID) throws IllegalArgumentException {
		ArrayList<NotePermission> ArrayListNotePermission = this.npMapper.findNotePermissionByNoteId(noteID);
		Note note = this.nMapper.findById(noteID);
		
		try {
			if(note.getUserId() == userID){
				this.nMapper.delete(note);
			}else if (ArrayListNotePermission != null) {
				for (NotePermission foundNotePermission : ArrayListNotePermission) {
					if (userID == foundNotePermission.getUserId()) {
						if (foundNotePermission.getPermission() == 3) {
							this.npMapper.delete(foundNotePermission);
							this.nMapper.delete(note);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "delete Note", e);
		}
		
	}
	@Override
	public ArrayList<Notebook> getAllNotebooksByUserID(int UserID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		return this.nbMapper.findNotebooksByUserID(UserID);
	}
	@Override
	public ArrayList<Note> getAllNotesByNotebookID(int notebookID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.nMapper.findNotesByNotebookId(notebookID);
	}
	
	@Override
	public ArrayList<Notebook> findNotebooksByKeyword(int userID, String keyword) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Notebook> allNotebooksFromThisUser = this.nbMapper.findNotebooksByUserID(userID);
		ArrayList<Notebook> notebooksWithKeyword = new ArrayList<Notebook>();
		
		if (allNotebooksFromThisUser != null) {

			for (Notebook foundedNotebook : allNotebooksFromThisUser) {
				String title = foundedNotebook.getTitle().toLowerCase();
				if (title.contains(keyword)) {
					notebooksWithKeyword.add(foundedNotebook);
				}

			}
		}else{
			//TODO funktioniert das?
			notebooksWithKeyword = null;
		}
		return notebooksWithKeyword;
	}
	@Override
	public ArrayList<Note> findNoteByKeyword(int userID, String keyword, int notebookID)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
