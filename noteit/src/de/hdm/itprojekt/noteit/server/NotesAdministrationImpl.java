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
	public void updateNotebook(String title, int notebookID, int editorID, ArrayList<String> userMail,
			ArrayList<Integer> permission) throws IllegalArgumentException {
		nb = new Notebook();
		nb.setId(notebookID);
		nb.setTitle(title);
		
		if(permission != null){
			for (int i = 0; i != permission.size(); i++) {
				NotebookPermission nbp = new NotebookPermission();
				nbp.setNotebookId(notebookID);
				nbp.setUserId(findUserByMail(userMail.get(i)).getId());
				nbp.setPermission(permission.get(i)); //TODO hier wird permssion immer 0 gesetzt! überprüfen
				
				this.nbpMapper.insert(nbp);
			}
		}
		this.nbMapper.edit(nb);
		
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

}
