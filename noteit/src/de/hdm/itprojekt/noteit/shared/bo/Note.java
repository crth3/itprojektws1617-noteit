package de.hdm.itprojekt.noteit.shared.bo;

import java.sql.Date;

/**
 * 
 * @author maikzimmermann
 *
 */
public class Note extends BusinessObjects {

	private static final long serialVersionUID = 1L;
	private int noteID = 0;
	private String title = "";
	private String subTitle = "";
	private String text = "";
	private Date creationDate;
	private Date modificationDate;
	private Date maturityDate;
	private boolean visible = false;
	public User creator;
	private String source;
	
	
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
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the maturityDate
	 */
	public Date getMaturityDate() {
		return maturityDate;
	}

	/**
	 * @param maturity
	 *            the maturityDate to set
	 */
	public void setMaturityDate(Date maturity) {
		this.maturityDate = maturity;
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
	 * @return modificationDate
	 */
	public Date getModificationDate() {
		return modificationDate;
	}
	
	/**
	 * 
	 * @param modificationDate
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/**
	 * Ausgabe des Creator
	 * @return
	 */
	public User getCreator() {
		return creator;
	}
	/**
	 * Setzen des Creator
	 * @param creator
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
