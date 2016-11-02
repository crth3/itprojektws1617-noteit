package de.hdm.itprojekt.noteit.shared.bo;

/**
 * 
 * @author maikzimmermann
 *
 */
public class Permission extends BusinessObjects {

	private static final long serialVersionUID = 1L;
	private int permissionID = 0;
	private int read = 1;
	private int write = 2;
	private int delete = 3;

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
	 * @return the read
	 */
	public int getRead() {
		return read;
	}

	/**
	 * @param read
	 *            the read to set
	 */
	public void setRead(int read) {
		this.read = read;
	}

	/**
	 * @return the write
	 */
	public int getWrite() {
		return write;
	}

	/**
	 * @param write
	 *            the write to set
	 */
	public void setWrite(int write) {
		this.write = write;
	}

	/**
	 * @return the delete
	 */
	public int getDelete() {
		return delete;
	}

	/**
	 * @param delete
	 *            the delete to set
	 */
	public void setDelete(int delete) {
		this.delete = delete;
	}

}
