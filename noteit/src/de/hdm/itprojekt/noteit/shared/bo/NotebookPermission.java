package de.hdm.itprojekt.noteit.shared.bo;

/**
 * 
 * @author maikzimmermann
 *
 */
public class NotebookPermission extends BusinessObjects {

	private static final long serialVersionUID = 1L;
	private int notebookPermissionId = 0;
	private int permission;
	private int notebookId = 0;
	private int userId;
	
	public NotebookPermission() {

	}

	/**
	 * @return the permissionID
	 */
	public int getnotebookPermisisonId() {
		return notebookPermissionId;
	}

	/**
	 * @param permissionID
	 *            the permissionID to set
	 */
	public void setNotebookPermissionId (int notebookPermissionId) {
		this.notebookPermissionId = notebookPermissionId;
	}

	/**
	 * @return the notebookID
	 */
	public int getNotebookId() {
		return notebookId;
	}

	/**
	 * @param notebookID
	 *            the notebookID to set
	 */
	public void setNotebookId(int notebookId) {
		this.notebookId = notebookId;
	}
	
	/**
	 * 
	 * @return permission
	 */
	public int getPermission() {
		return permission;
	}
	
	/**
	 * 
	 * @param permission
	 */
	public void setPermission(int permission) {
		this.permission = permission;
	}
	
	/**
	 * 
	 * @return userId
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
	};

}
