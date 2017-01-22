package de.hdm.itprojekt.noteit.shared.bo;

/**
 * Umsetzung der Notizbuchberechtigungsklasse. Um Berechtiungen mit einer
 * Notizbuch zu verknüpfen
 * 
 * @author maikzimmermann
 *
 */
public class NotebookPermission extends BusinessObjects {

	private static final long serialVersionUID = 1L;
	private int permission;
	private int notebookId = 0;
	private int userId;
	public Notebook notebook;
	public User user;

	public NotebookPermission() {

	}

	/**
	 * NotizbuchID der Permission holen
	 * 
	 * @return the notebookID
	 */
	public int getNotebookId() {
		return notebookId;
	}

	/**
	 * NotizbuchID der Permission setzen
	 * 
	 * @param notebookID
	 */
	public void setNotebookId(int notebookId) {
		this.notebookId = notebookId;
	}

	/**
	 * PermissionID holen
	 * 
	 * @return permission
	 */
	public int getPermission() {
		return permission;
	}

	/**
	 * PermissionID für die Berechtigung setzen
	 * 
	 * @param permission
	 */
	public void setPermission(int permission) {
		this.permission = permission;
	}

	/**
	 * UserID für die Berechtigung holen
	 * 
	 * @return userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * UserID für die Berechtigung setzen
	 * 
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	};

	/**
	 * Ausgabe der NotebookPermission
	 * 
	 * @return
	 */
	public Notebook getNotebook() {
		return notebook;
	}

	/**
	 * Setzen der NotebookPermission
	 * 
	 * @param creator
	 */
	public void setNotebook(Notebook notebook) {
		this.notebook = notebook;
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
