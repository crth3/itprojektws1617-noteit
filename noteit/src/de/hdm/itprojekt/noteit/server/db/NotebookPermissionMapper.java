package de.hdm.itprojekt.noteit.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.NotebookPermission;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class NotebookPermissionMapper {
	
private static NotebookPermissionMapper notebookPermissionMapper = null;
	
	/**
	 * Privater Konstruktor verhindert das Erzeugen neuer Instanzen
	 * mittels des <code>new</code> Keywords.
	 */
	private NotebookPermissionMapper() {
		
	}
	
	/**
	 * Singleton
	 * @return
	 */
	public static NotebookPermissionMapper notebookPermissionMapper() {
		if(notebookPermissionMapper == null) {
			notebookPermissionMapper = new NotebookPermissionMapper();
		}
		
		return notebookPermissionMapper;
	}
	
	/**
	 * Suche eine NotebookPermission anhand seiner einzigartigen ID.
	 * 
	 * @param id - Primärschlüssel von NotebookPermission
	 * @return NotebookPermission Objekt, das die gesuchte ID enthält
	 */
	public NotebookPermission findById(int id) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// Neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
			ResultSet rs = stmt.executeQuery("SELECT notebookPermissionId, permission, Note_noteId, User_userId FROM NotebookPermission " 
					+ "WHERE notebookPermissionId = " 
					+ id);
			// Bei Treffer 
			if(rs.next()) {
				// Neues Source Objekt erzeugen
				NotebookPermission nbp = new NotebookPermission();
				// Id und Source mit den Daten aus der DB füllen
				nbp.setId(rs.getInt("notebookPermissionId"));
				nbp.setPermission(rs.getInt("permission"));
				nbp.setNotebookId(rs.getInt("Note_noteId"));
				nbp.setUserId(rs.getInt("User_userId"));

			
				// Objekt zurückgeben
				return nbp;
			}
		} 
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		// Falls nichts gefunden wurde null zurückgeben
		return null;
	}
	
	/**
	 * Diese Methode gibt alle NotebookPermission eines Notebooks 
	 * anhand der NotebookId in einer Liste aus
	 * 
	 * @param id
	 *            Eindeutiger Identifikator der NotebookPermission in der Datenbank
	 * @return Liste der NotebookPermission eines Notebooks
	 * 	 
	 * */
	public Vector<NotebookPermission> findNotebookPermissionByNotebookId(int id) {

		Connection con = DBConnection.connection();
		Vector<NotebookPermission> notebookPermissionList = new Vector<NotebookPermission>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM NotebookPermission "
							+ "INNER JOIN Notebook "
							+ "ON NotebookPermission.Notebook_notebookId = Notebook.notebookId "
							+ "WHERE Notebook_notebookId = "
							+ id);

			while (rs.next()) {
				NotebookPermission nbp = new NotebookPermission();
				Notebook nb = new Notebook();
				
				nbp.setId(rs.getInt("notebookPermissionId"));
				nbp.setPermission(rs.getInt("permission"));
				nbp.setNotebookId(rs.getInt("Notebook_notebookId"));
				nbp.setUserId(rs.getInt("User_userId"));
				
				nb.setId(rs.getInt("notebookId"));
				nb.setTitle(rs.getString("title"));
				
				nbp.setNotebook(nb);
							
				System.out.println(rs);
				// Conversation Objekt der Liste hinzufügen
				notebookPermissionList.add(nbp);
			}
			// Objekt zurückgeben
			return notebookPermissionList;
		}
		// Error-Handlung
		catch (SQLException e) {
			e.printStackTrace();

		}
		return notebookPermissionList;
	}
	
	/**
	 * Diese Methode gibt alle NotebookPermission eines Notebooks 
	 * anhand der userId in einer Liste aus
	 * 
	 * @param id
	 *            Eindeutiger Identifikator der NotebookPermission in der Datenbank
	 * @return Liste der NotebookPermission eines Notebooks
	 * 	 
	 * */
	public Vector<NotebookPermission> findNotebookPermissionByUserId(int id) {

		Connection con = DBConnection.connection();
		Vector<NotebookPermission> notebookPermissionList = new Vector<NotebookPermission>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM NotebookPermission "
							+ "INNER JOIN User "
							+ "ON NotebookPermission.User_userId = User.userId "
							+ "WHERE User_userId = "
							+ id
							+ "ORDER BY User_userId ASC");

			while (rs.next()) {
				NotebookPermission nbp = new NotebookPermission();
				User u = new User();
				
				nbp.setId(rs.getInt("notebookPermissionId"));
				nbp.setPermission(rs.getInt("permission"));
				nbp.setNotebookId(rs.getInt("Notebook_notebookId"));
				nbp.setUserId(rs.getInt("User_userId"));
				
				u.setId(rs.getInt("userId"));
				u.setFirstName(rs.getString("firstName"));
				u.setLastName(rs.getString("lastName"));
				
				nbp.setUser(u);
							
				System.out.println(rs);
				// Conversation Objekt der Liste hinzufügen
				notebookPermissionList.add(nbp);
			}
			// Objekt zurückgeben
			return notebookPermissionList;
		}
		// Error-Handlung
		catch (SQLException e) {
			e.printStackTrace();

		}
		return notebookPermissionList;
	}
	
	
	
	/**
	 * Neue NotebookPermission in der Datenbank anlegen.
	 * 
	 * @param nbp Die NotebookPermission, die in die Datenbank eingefügt werden soll
	 * @return 
	 */
	public NotebookPermission insert(NotebookPermission nbp) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen um die höchste id zu erhalten
			ResultSet rs = stmt.executeQuery("SELECT MAX(notebookPermissionId) AS maxId FROM NotebookPermission");
			if(rs.next()) {
				// id um 1 erhöhen, damit ein neuer Eintrag erzeugt wird
				nbp.setId(rs.getInt("maxId") + 1);
				// neues SQL Statement
				stmt = con.createStatement();
				// SQL Query ausführen um Datensatz in DB zu schreiben
				
				System.out.println("INSERT INTO NotebookPermission (notebookPermissionId, permission, Notebook_notebookId, User_userId) " +
						"VALUES (" 
						+ nbp.getId() 
						+ ", '" 
						+ nbp.getPermission()
						+ "', '" 
						+ nbp.getNotebookId()
						+ "', '" 
						+ nbp.getUserId()
						+"')");
				
				stmt.executeUpdate("INSERT INTO NotebookPermission (notebookPermissionId, permission, Notebook_notebookId, User_userId) " +
						"VALUES (" 
						+ nbp.getId() 
						+ ", '" 
						+ nbp.getPermission()
						+ "', '" 
						+ nbp.getNotebookId()
						+ "', '" 
						+ nbp.getUserId()
						+"')");
			}
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return nbp;
	}
	
	/**
	 * NotebookPermission aus der Datenbank bearbeiten
	 * 
	 * @param nbp
	 * @return nbp
	 */
	public NotebookPermission update(NotebookPermission nbp) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
			
			System.out.println("UPDATE NotebookPermission SET "
					+ "permission="
					+ nbp.getPermission()
					+ "','"
					+ "Notebook_notebookId="
					+ nbp.getNotebookId()
					+ "','"
					+ "User_userId="
					+ nbp.getUserId()
					+"' WHERE notebookPermissionId="+nbp.getId());
			
			stmt.executeUpdate("UPDATE NotebookPermission SET "
					+ "permission="
					+ nbp.getPermission()
					+ "','"
					+ "Notebook_notebookId="
					+ nbp.getNotebookId()
					+ "','"
					+ "User_userId="
					+ nbp.getUserId()
					+"' WHERE notebookPermissionId="+nbp.getId());
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
		//Rückgabe der NotebookPermission
		return nbp;
	}
	
	/**
	 * NotebookPermission aus der Datenbank löschen
	 * 
	 * @param nbp die zu löschende NotebookPermission
	 */
	public void delete(NotebookPermission nbp) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
			stmt.executeUpdate("DELETE FROM NotebookPermission "
					+ "WHERE notebookPermissionId = " 
					+ nbp.getId());
			System.out.println("Notebook mit der ID " + nbp.getNotebookId() + " Permission mit id " + nbp.getId() +  " wurde gelöscht");
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
