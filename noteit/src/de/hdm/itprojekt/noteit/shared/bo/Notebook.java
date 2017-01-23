package de.hdm.itprojekt.noteit.shared.bo;

import java.sql.Timestamp;

/**
 * Umsetzung der Notizbuchklasse. Als Attribute dienen ErstellerID,
 * BerechtigungsID, Titel, Erstelldatum und Bearbeitungsdatum
 * 
 * @author maikzimmermann
 *
 */
public class Notebook extends BusinessObjects {

	private static final long serialVersionUID = 1L;
	public int creatorID = 0;
	private int permissionID;
	private String title = "";
	private Timestamp creationDate;
	private Timestamp modificationDate;
	public User creator;
	public Note note;

	/**
	 * Fremdschlüsselbeziehung zu userId
	 */
	private int userId;

	/**
	 * Titel des Notizbuches holen
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Titel des Notizbuches setzen
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Erstelldatum holen
	 * 
	 * @return the creationTimestamp
	 */
	public Timestamp getCreationDate() {
		return creationDate;
	}

	/**
	 * Erstelldatum setzen
	 * 
	 * @param creationTimestamp
	 * 
	 */
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Modifikationdatum für das Notizbuch holen
	 * 
	 * @return modificationTimestamp
	 */
	public Timestamp getModificationDate() {
		return modificationDate;
	}

	/**
	 * Modifikationdatum für das Notizbuch setzen
	 * 
	 * @param modificationTimestamp
	 */
	public void setModificationDate(Timestamp modificationDate) {
		this.modificationDate = modificationDate;
	}

	/**
	 * 
	 * @return
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * 
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Ausgabe der Note
	 * 
	 * @return
	 */
	public Note getNote() {
		return note;
	}

	/**
	 * Setzen des Note
	 * 
	 * @param creator
	 */
	public void setNote(Note note) {
		this.note = note;
	}

	/**
	 * Ausgabe der Berechtigung für dieses Notizbuch
	 * 
	 * @return
	 */
	public int getPermissionID() {
		return permissionID;
	}

	/**
	 * Setzen der Berechtigung für dieses Notizbuch
	 * 
	 * @param permissionID
	 */
	public void setPermissionID(int permissionID) {
		this.permissionID = permissionID;
	}

}
