package de.hdm.itprojekt.noteit.shared.bo;

import java.sql.Timestamp;

/**
 * Umsetzung der Notizklasse. Als Attribute dienen ErstellerID, BerechtigungsID,
 * Titel, Untertitel, Notiztext, Erstelldatum und Bearbeitungsdatum,
 * Fälligkeitsdatum
 * 
 * @author maikzimmermann
 *
 */
public class Note extends BusinessObjects {

	private static final long serialVersionUID = 1L;
	private int noteID = 0;
	private int permissionID;
	private String title = "";
	private String subTitle = "";
	private String text = "";
	private Timestamp creationDate;
	private Timestamp modificationDate;
	private Timestamp maturityDate;
	public User user;

	/**
	 * Fremschlüsselbeziehung zu User
	 */
	private int userId;

	/**
	 * Fremdchlüsselbeziehung zu Notebook
	 */
	private int notebookId;

	/**
	 * @return the noteID
	 */
	public int getNoteID() {
		return noteID;
	}

	/**
	 * @param noteID
	 *            the noteID to set
	 */
	public void setNoteID(int noteID) {
		this.noteID = noteID;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the subTitle
	 */
	public String getSubTitle() {
		return subTitle;
	}

	/**
	 * @param subTitle
	 *            the subTitle to set
	 */
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the creationTimestamp
	 */
	public Timestamp getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationTimestamp
	 *            the creationTimestamp to set
	 */
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the maturityTimestamp
	 */
	public Timestamp getMaturityDate() {
		return maturityDate;
	}

	/**
	 * @param maturity
	 *            the maturityTimestamp to set
	 */
	public void setMaturityDate(Timestamp maturity) {
		this.maturityDate = maturity;
	}

	/**
	 * 
	 * @return notebookId
	 */
	public int getNotebookId() {
		return notebookId;
	}

	/**
	 * 
	 * @param notebookId
	 */
	public void setNotebookId(int notebookId) {
		this.notebookId = notebookId;
	}

	/**
	 * 
	 * @return modificationTimestamp
	 */
	public Timestamp getModificationDate() {
		return modificationDate;
	}

	/**
	 * 
	 * @param modificationTimestamp
	 */
	public void setModificationDate(Timestamp modificationDate) {
		this.modificationDate = modificationDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Ausgabe des Nutzers
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setzen des Nutzers
	 * 
	 * @param nutzer
	 */
	public void setCreator(User user) {
		this.user = user;
	}

	/**
	 * Berechtigung des Nutzers für diese Notiz holen
	 * 
	 * @return ID der Berechtigung
	 */
	public int getPermissionID() {
		return permissionID;
	}

	/**
	 * Setzen der Berechtigung des Nutzers für diese Notiz
	 * 
	 * @param permissionID
	 */
	public void setPermissionID(int permissionID) {
		this.permissionID = permissionID;
	}

}
