package de.hdm.itprojekt.noteit.shared.bo;

import java.sql.Timestamp;

/**
 * 
 * @author maikzimmermann
 *
 */
public class Notebook extends BusinessObjects {

	private static final long serialVersionUID = 1L;
	private int notebookID = 0;
	private String title = "";
	private String subTitle = "";
	private String text = "";
	private Timestamp creationDate;
	private boolean visible = false;
	
	/**
	 * Fremdschlüsselbeziehung zu userId
	 */
	private int userId;

	/**
	 * @return the notebookID
	 */
	public int getNotebookID() {
		return notebookID;
	}

	/**
	 * @param notebookID
	 *            the notebookID to set
	 */
	public void setNotebookID(int notebookID) {
		this.notebookID = notebookID;
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
	public Timestamp getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
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

}
