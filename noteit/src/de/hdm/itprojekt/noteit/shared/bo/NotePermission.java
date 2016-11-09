package de.hdm.itprojekt.noteit.shared.bo;

public class NotePermission extends BusinessObjects {

	private static final long serialVersionUID = 1L;
	private int notePermissionId = 0;
	private int permission;
	private int noteId = 0;
	private int userId;

	public NotePermission() {

	}

	/**
	 * @return the permissionID
	 */
	public int getNotePermissionId() {
		return notePermissionId;
	}

	/**
	 * @param permissionID
	 *            the permissionID to set
	 */
	public void setNotePermissionId(int notePermissionId) {
		this.notePermissionId = notePermissionId;
	}

	/**
	 * @return the noteID
	 */
	public int getNoteId() {
		return noteId;
	}

	/**
	 * @param noteID
	 *            the noteID to set
	 */
	public void setNoteId(int noteId) {
		this.noteId = noteId;
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
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	};

}
