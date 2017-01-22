package de.hdm.itprojekt.noteit.shared.bo;

/**
 * Umsetzung der Notizberechtigungsklasse. Um Berechtiungen 
 * mit einer Notiz zu verknüpfen
 * 
 * @author maikzimmermann
 *
 */
public class NotePermission extends BusinessObjects {

	private static final long serialVersionUID = 1L;
	private int notePermissionId = 0;
	private int permissionID;
	private int noteId = 0;
	private int userId;
	public Note note;
	public User user;

	public NotePermission() {

	}

	/**
	 * permissionID Berechtigung für die Notiz holen
	 * 
	 * @return the permissionID
	 */
	public int getNotePermissionId() {
		return notePermissionId;
	}

	/**
	 * permissionID Berechtigung für die Notiz setzen
	 * 
	 * @param permissionID
	 */
	public void setNotePermissionId(int notePermissionId) {
		this.notePermissionId = notePermissionId;
	}

	/**
	 * Notiz ID für die Berechtigung holen
	 * 
	 * @return the noteID
	 */
	public int getNoteId() {
		return noteId;
	}

	/**
	 * Notiz ID für die Berechtigung setzen
	 * 
	 * @param noteID
	 */
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	/**
	 * Nutzer ID für die Berechtigung holen
	 * 
	 * @return userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Nutzer ID für die Berechtigung setzen
	 * 
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * ID für die Berechtigung holen
	 * 
	 * @return
	 */
	public int getPermission() {
		return permissionID;
	}

	/**
	 * ID für die Berechtigung setzen
	 * 
	 * @param permissionID
	 */
	public void setPermission(int permissionID) {
		this.permissionID = permissionID;
	};

	/**
	 * Ausgabe der NotePermission
	 * 
	 * @return
	 */
	public Note getNote() {
		return note;
	}

	/**
	 * Setzen der NotePermission
	 * 
	 * @param creator
	 */
	public void setNote(Note note) {
		this.note = note;
	}

	/**
	 * Ausgabe des Users
	 * 
	 * @return User
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setzen des Users
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
