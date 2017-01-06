package de.hdm.itprojekt.noteit.shared.bo;

import java.sql.Timestamp;

/**
 * 
 * @author maikzimmermann
 *
 */
public class Notebook extends BusinessObjects {

	private static final long serialVersionUID = 1L;
	public int creatorID = 0;
	private String title = "";
	private Timestamp creationDate;
	private Timestamp modificationDate;
	private boolean visible = false;
	public User creator;
	public Note note;
	
	/**
	 * Fremdschlüsselbeziehung zu userId
	 */
	private int userId;

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

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
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
	
//	/**
//	 * Ausgabe des Creator
//	 * @return
//	 */
//	public int getCreatorID() {
//		return creatorID;
//	}
//	
//	/**
//	 * 
//	 * @param creatorID
//	 */
//	public void setCreatorID(int creatorID) {
//		this.creatorID = creatorID;
//	}
	
	/**
	 * Ausgabe der Note
	 * @return
	 */
	public Note getNote() {
		return note;
	}
	/**
	 * Setzen des Note
	 * @param creator
	 */
	public void setNote(Note note) {
		this.note = note;
	}

}
