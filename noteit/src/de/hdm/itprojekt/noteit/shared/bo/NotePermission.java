package de.hdm.itprojekt.noteit.shared.bo;

public class NotePermission extends BusinessObjects {

	private static final long serialVersionUID = 1L;
	private int permissionID = 0;
	private int noteID = 0;

	public NotePermission() {

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
	};

}
