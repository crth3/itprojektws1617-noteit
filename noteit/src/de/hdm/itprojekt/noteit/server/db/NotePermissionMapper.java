package de.hdm.itprojekt.noteit.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojekt.noteit.shared.bo.NotePermission;

public class NotePermissionMapper {
	
	
private static NotePermissionMapper notePermissionMapper = null;
	
	/**
	 * Privater Konstruktor verhindert das Erzeugen neuer Instanzen
	 * mittels des <code>new</code> Keywords.
	 */
	private NotePermissionMapper() {
		
	}
	
	/**
	 * Singleton
	 * @return
	 */
	public static NotePermissionMapper notePermissionMapper() {
		if(notePermissionMapper == null) {
			notePermissionMapper = new NotePermissionMapper();
		}
		
		return notePermissionMapper;
	}
	
	/**
	 * Suche eine NotePermisison anhand seiner einzigartigen ID.
	 * 
	 * @param id - Primärschlüssel von NotePermission
	 * @return NotePermission Objekt, das die gesuchte ID enthält
	 */
	public NotePermission findById(int id) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// Neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
			ResultSet rs = stmt.executeQuery("SELECT notePermissionId, permission, Note_noteId, User_userId FROM NotePermission " 
					+ "WHERE notePermissionId = " 
					+ id);
			// Bei Treffer 
			if(rs.next()) {
				// Neues Source Objekt erzeugen
				NotePermission np = new NotePermission();
				// Id und Source mit den Daten aus der DB füllen
				np.setId(rs.getInt("notePermissionId"));
				np.setPermission(rs.getInt("permission"));
				np.setNoteId(rs.getInt("Note_noteId"));
				np.setNoteId(rs.getInt("Note_noteId"));
				np.setUserId(rs.getInt("User_userId"));

			
				// Objekt zurückgeben
				return np;
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
	 * Neue NotePermission in der Datenbank anlegen.
	 * 
	 * @param np Die NotePermission, die in die Datenbank eingefügt werden soll
	 * @return 
	 */
	public NotePermission insert(NotePermission np) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen um die höchste id zu erhalten
			ResultSet rs = stmt.executeQuery("SELECT MAX(notePermissionId) AS maxId FROM NotePermission");
			if(rs.next()) {
				// id um 1 erhöhen, damit ein neuer Eintrag erzeugt wird
				np.setId(rs.getInt("maxId") + 1);
				// neues SQL Statement
				stmt = con.createStatement();
				// SQL Query ausführen um Datensatz in DB zu schreiben
				stmt.executeUpdate("INSERT INTO NotePermission (notePermissionId, permission, Note_noteId, User_userId) " +
						"VALUES (" 
						+ np.getId() 
						+ ", '" 
						+ np.getPermission()
						+ "', '" 
						+ np.getNoteId()
						+ "', '" 
						+ np.getUserId()
						+"')");
			}
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return np;
	}
	
	/**
	 * NotePermission aus der Datenbank bearbeiten
	 * 
	 * @param np
	 * @return np
	 */
	public NotePermission update(NotePermission np) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
			System.out.println("UPDATE NotePermission SET "
					+ "permission="
					+ np.getPermission()
					+ "','"
					+ "Note_noteId="
					+ np.getNoteId()
					+ "','"
					+ "User_userId="
					+ np.getUserId()
					+"' WHERE notePermissionId="+np.getId());
			
			stmt.executeUpdate("UPDATE NotePermission SET "
					+ "permission="
					+ np.getPermission()
					+ "','"
					+ "Note_noteId="
					+ np.getNoteId()
					+ "','"
					+ "User_userId="
					+ np.getUserId()
					+"' WHERE notePermissionId="+np.getId());
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
		//Rückgabe der NotePermission
		return np;
	}
	
	/**
	 * NotePermission aus der Datenbank löschen
	 * 
	 * @param nbp die zu löschende Source
	 */
	public void delete(NotePermission np) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
			stmt.executeUpdate("DELETE FROM NotePermisison "
					+ "WHERE notePermissionId = " 
					+ np.getNotePermissionId());
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
