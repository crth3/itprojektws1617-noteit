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
	 * 
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setzen des Vornamens des Nutzers
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Ausgabe des Nachnamens des Nutzers
	 * 
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setzen des Nachnamens des Nutzers
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Ausgabe der Email Adresse des Nutzers
	 * 
	 * @return
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Setzen der Email Adresse des Nutzers
	 * 
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * BerechtigungsID des Nutzers holen
	 * 
	 * @return
	 */
	public int getPermissionID() {
		return permissionID;
	}

	/**
	 * BerechtigungsID des Nutzers setzen
	 * 
	 * @param permissionID
	 */
	public void setPermissionID(int permissionID) {
		this.permissionID = permissionID;
	}
}
