package de.hdm.itprojekt.noteit.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.NotePermission;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.NotebookPermission;
import de.hdm.itprojekt.noteit.shared.bo.User;

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
	 * Diese Methode gibt alle NotePermission einer Note
	 * anhand der NoteId in einer Liste aus
	 * 
	 * @param id
	 *            Eindeutiger Identifikator der NotePermission in der Datenbank
	 * @return Liste der NotePermission einer Note
	 * 	 
	 * */
	public ArrayList<NotePermission> findNotePermissionByNoteId(int id) {

		Connection con = DBConnection.connection();
		ArrayList<NotePermission> notePermissionList = new ArrayList<NotePermission>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM NotePermission "
							+ "INNER JOIN Note "
							+ "ON NotePermission.Note_noteId = Note.noteId "
							+ "WHERE Note_noteId = "
							+ id);

			while (rs.next()) {
				NotePermission np = new NotePermission();
				Note n = new Note();
				
				np.setId(rs.getInt("notePermissionId"));
				np.setPermission(rs.getInt("permission"));
				np.setNoteId(rs.getInt("Note_noteId"));
				np.setUserId(rs.getInt("User_userId"));
				
				n.setId(rs.getInt("noteId"));
				n.setTitle(rs.getString("title"));
				n.setSubTitle(rs.getString("subtitle"));
				n.setText(rs.getString("content"));
				n.setMaturityDate(rs.getTimestamp("maturity"));
				n.setCreationDate(rs.getTimestamp("creationDate"));
				n.setModificationDate(rs.getTimestamp("modificationDate"));
				n.setNotebookId(rs.getInt("Notebook_notebookId"));
				n.setUserId(rs.getInt("User_UserId"));
				
				np.setNote(n);
							
				System.out.println(rs);
				// Conversation Objekt der Liste hinzufügen
				notePermissionList.add(np);
			}
			// Objekt zurückgeben
			return notePermissionList;
		}
		// Error-Handlung
		catch (SQLException e) {
			e.printStackTrace();

		}
		return notePermissionList;
	}
	
	/**
	 * Diese Methode gibt alle NotePermission einer Note
	 * anhand der userId in einer Liste aus
	 * 
	 * @param id
	 *            Eindeutiger Identifikator der NotePermission in der Datenbank
	 * @return Liste der NotePermission einer Note aus
	 * 	 
	 * */
	public ArrayList<NotePermission> findNotePermissionByUserId(int id) {
		
		System.out.println("userId: " +id);

		Connection con = DBConnection.connection();
		ArrayList<NotePermission> notePermissionList = new ArrayList<NotePermission>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM NotePermission "
							+ "INNER JOIN User "
							+ "ON NotePermission.User_userId = User.userId "
							+ "WHERE User_userId = "
							+ id);

			while (rs.next()) {
				NotePermission np = new NotePermission();
				//User u = new User();
				
				np.setId(rs.getInt("notePermissionId"));
				np.setPermission(rs.getInt("permission"));
				np.setNoteId(rs.getInt("Note_noteId"));
				np.setUserId(rs.getInt("User_userId"));
				
				System.out.println("ID: " + np.getId());
				System.out.println("permission: " + np.getPermission());
				System.out.println("noteid" + np.getNoteId());
				System.out.println("userId: " + np.getUserId());
				
				//u.setId(rs.getInt("userId"));
				//u.setFirstName(rs.getString("firstName"));
				//u.setLastName(rs.getString("lastName"));
				
				//np.setUser(u);
				
				System.out.println(rs);
				// Conversation Objekt der Liste hinzufügen
				notePermissionList.add(np);
			}
			// Objekt zurückgeben
			return notePermissionList;
		}
		// Error-Handlung
		catch (SQLException e) {
			e.printStackTrace();

		}
		return notePermissionList;
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
					+ "permission='" 
					+ np.getPermission()
					+ "',"
					+ "Note_noteId='"
					+ np.getNoteId()
					+ "',"
					+ "User_userId='"
					+ np.getUserId()
					+"' WHERE notePermissionId='"+np.getId()
					+ "'");
			
			stmt.executeUpdate("UPDATE NotePermission SET "
					+ "permission='" 
					+ np.getPermission()
					+ "',"
					+ "Note_noteId='"
					+ np.getNoteId()
					+ "',"
					+ "User_userId='"
					+ np.getUserId()
					+"' WHERE notePermissionId='"+np.getId()
					+ "'");
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
			System.out.println("DELETE FROM NotePermission "
					+ "WHERE notePermissionId = " 
					+ np.getId());
			
			
			stmt.executeUpdate("DELETE FROM NotePermission "
					+ "WHERE notePermissionId = " 
					+ np.getId());
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
