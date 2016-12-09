package de.hdm.itprojekt.noteit.shared.bo;

/**
 * Umsetzung der Nutzerklasse. Als Attribute dienen Vorname, Nachname sowie
 * Email des Nutzers.
 * 
 * @author maikzimmermann
 *
 */
public class User extends BusinessObjects {

	private static final long serialVersionUID = 1L;

	/**
	 * Vorname des Nutzers
	 */
	private String firstName = "";

	/**
	 * Nachname des Nutzers
	 */
	private String lastName = "";

	/**
	 * Email Adresse des Nutzers
	 */
	private String mail = "";
	
	/**
	 * Freigabe des nutzers
	 */
	private int permissionID;

	/**
	 * Ausgabe des Vornamens des Nutzers
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setzen des Vornamens des Nutzers
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Ausgabe des Nachnamens des Nutzers
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setzen des Nachnamens des Nutzers
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Ausgabe der Email Adresse des Nutzers
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Setzen der Email Adresse des Nutzers
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getPermissionID() {
		return permissionID;
	}

	public void setPermissionID(int permissionID) {
		this.permissionID = permissionID;
	}
}
