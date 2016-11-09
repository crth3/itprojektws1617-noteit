package de.hdm.itprojekt.noteit.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojekt.noteit.shared.bo.NotebookPermission;

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
				
				System.out.println("INSERT INTO NotebookPermission (notebookPermissionId, permission, Note_noteId, User_userId) " +
						"VALUES (" 
						+ nbp.getId() 
						+ ", '" 
						+ nbp.getPermission()
						+ "', '" 
						+ nbp.getNotebookId()
						+ "', '" 
						+ nbp.getUserId()
						+"')");
				
				stmt.executeUpdate("INSERT INTO NotebookPermission (notebookPermissionId, permission, Note_noteId, User_userId) " +
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
			stmt.executeUpdate("DELETE FROM NotebookPermisison "
					+ "WHERE notebookPermissionId = " 
					+ nbp.getnotebookPermisisonId());
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
