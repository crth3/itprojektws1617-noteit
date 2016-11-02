package de.hdm.itprojekt.noteit.shared.bo;

/**
 * 
 * @author maikzimmermann
 *
 */
public class NotebookPermission extends BusinessObjects {

	private static final long serialVersionUID = 1L;
	private int permissionID = 0;
	private int notebookID = 0;

	public NotebookPermission() {

	}

	/**
	 * @return the permissionID
	 */
	public int getPermissionID() {
		return permissionID;
	}

	/**
	 * @param permissionID
	 *            the permissionID to set
	 */
	public void setPermissionID(int permissionID) {
		this.permissionID = permissionID;
	}

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
	};

}
