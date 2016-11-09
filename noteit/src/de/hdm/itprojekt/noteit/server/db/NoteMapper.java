package de.hdm.itprojekt.noteit.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.User;

/**
 * <p>
 * Mapper-Klasse zur Abbildung von <code>Notebook</code> Objekten auf die
 * Datenbank. Über das Mapping können sowohl Objekte bzw deren Attribute in die
 * Datenbank geschrieben, als auch von der Datenbank ausgelesen werden.
 * </p>
 * <p>
 * Es werden Methoden zum Erstellen, Löschen und Ausgeben von Messages
 * bereitgestellt.
 * </p>
 * 
 */

public class NoteMapper {
	
	private static NoteMapper noteMapper = null;

	/**
	 * Privater Konstruktor verhindert das Erzeugen neuer Instanzen mittels des
	 * <code>new</code> Keywords.
	 */
	private NoteMapper() {

	}

	/**
	 * Singleton
	 * 
	 * @return
	 */
	public static NoteMapper noteMapper() {
		if (noteMapper == null) {
			noteMapper = new NoteMapper();
		}

		return noteMapper;
	}

	/**
	 * Suche eine Note anhand ihrer einzigartigen ID.
	 * 
	 * @param id
	 *            - Primärschlüssel von Note
	 * @return Note Objekt, das die gesuchte ID enthält
	 */
	public Note findById(int id) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();

		try {
			// Neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM NOTE INNER JOIN User ON Note.User_idUser = User.idUser WHERE Note.noteId= " + id);
			// Bei Treffer
			if (rs.next()) {
				// Neues Message-Objekt erzeugen
				Note n = new Note();
				User creator = new User();
				creator.setId(rs.getInt("userId"));
				creator.setFirstName(rs.getString("firstName"));
				creator.setLastName(rs.getString("lastName"));
				// sender.setCreationDate(rs.getTimestamp("creationDate"));
				creator.setMail(rs.getString("emailAddress"));
				n.setId(rs.getInt("noteId"));
				n.setText(rs.getString("content"));
				n.setMaturityDate(rs.getTimestamp("maturity"));
				n.setCreationDate(rs.getTimestamp("creationDate"));
				//last update fehlt
				//n.set(rs.getTimestamp("creationDate"));
				n.setId(rs.getInt("User_userId"));
				n.setCreator(creator);
				// Objekt zurückgeben
				return n;
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
	 * Neue Note in der Datenbank anlegen.
	 * 
	 * @param n
	 *            Note Objekt, das in die Datenbank eingefügt werden soll
	 * @return
	 */
	public Note insert(Note n) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();

		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen um die höchste id zu erhalten
			ResultSet rs = stmt
					.executeQuery("SELECT MAX(idMessage) AS maxId FROM Note");
			if (rs.next()) {
				// id um 1 erhöhen, damit ein neuer Eintrag erzeugt wird
				n.setId(rs.getInt("maxId") + 1);
				// neues SQL Statement
				stmt = con.createStatement();
				// SQL Query ausführen um Datensatz in DB zu schreiben
				// Console - Ausgabe SQL Befehl
				System.out
						.println("INSERT INTO Note (noteId, title, subtitle, content, maturity, creationDate, User_userId, Notebook_notebookId) "
								+ "VALUES ('"
								+ n.getId()
								+ "', '"
								+ n.getTitle()
								+ "', '"
								+ n.getSubTitle()
								+ "', '"
								+ n.getText()
								+ "', '"
								+ n.getCreationDate()
								+ "', '"
								//TODO vielleicht nur user id
								+ n.getUserId()
								+ "', '"
								+ n.getNotebookId()
								+ "');");

		
				stmt.executeUpdate("INSERT INTO Note (noteId, title, subtitle, content, maturity, creationDate, User_userId, Notebook_notebookId) "
						+ "VALUES ('"
						+ n.getId()
						+ "', '"
						+ n.getTitle()
						+ "', '"
						+ n.getSubTitle()
						+ "', '"
						+ n.getText()
						+ "', '"
						+ n.getCreationDate()
						+ "', '"
						//TODO vielleicht nur user id
						+ n.getUserId()
						+ "', '"
						+ n.getNotebookId()
						+ "');");

			}
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}

		return n;
	}

	/**
	 * Note aus der Datenbank bearbeiten
	 * 
	 * @param m
	 *            die zu bearbeitende Nachricht
	 * @return m
	 */
	public Note update(Note n) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();

		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
	
			System.out.println("UPDATE Note SET "
					+ "title = '"
					+ n.getTitle() 
					+ "','"
					+ "subtitle = '"
					+ n.getSubTitle() 
					+ "','"
					+ "content = '"
					+ n.getText() 
					+ "','"
					+ "maturity = '"
					+ n.getMaturityDate() 
					+ "','"
					+ "modificationDate = '"
					+ n.getModificationDate() 
					+ "' "
					+" WHERE noteId=" 
					+ n.getId());
			

			stmt.executeUpdate("UPDATE Note SET "
					+ "title = '"
					+ n.getTitle() 
					+ "','"
					+ "subtitle = '"
					+ n.getSubTitle() 
					+ "','"
					+ "content = '"
					+ n.getText() 
					+ "','"
					+ "maturity = '"
					+ n.getMaturityDate() 
					+ "','"
					+ "modificationDate = '"
					+ n.getModificationDate() 
					+ "' "
					+" WHERE noteId=" 
					+ n.getId());
			
		}
		
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
		// Rückgabe der Note
		return n;
	}

	/**
	 * Note aus der Datenbank löschen
	 * 
	 * @param n
	 *            die zu löschenende Note
	 */
	public void delete(Note n) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();

		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			
			// Source Verküpfung löschen 
			// Prüfen ob Verbindung zu Source besteht, löschen wenn bestehend
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM Source WHERE Note_noteId = '" + n.getId() +"';");
			if(rs1.next()) {
				
				System.out.println("DELETE FROM Note WHERE "
						+ "Note_noteId = '" 
						+ n.getId() 
						+"';");
				
				stmt.executeUpdate("DELETE FROM Note WHERE "
						+ "Note_noteId = '" 
						+ n.getId() 
						+"';");
			}
			
			// Prüfen ob Verbindung zu NotePermission besteht, löschen wenn bestehend
						ResultSet rs2 = stmt.executeQuery("SELECT * FROM NotePermission WHERE Note_noteId = '" + n.getId() +"';");
						if(rs2.next()) {
							
							System.out.println("DELETE FROM NotePermission WHERE "
									+ "Note_noteId = '" 
									+ n.getId()
									+ "'"
									+ "AND "
									+ "User_userId = '"
									+ n.getUserId()
									+"';");
							
							stmt.executeUpdate("DELETE FROM NotePermission WHERE "
									+ "Note_noteId = '" 
									+ n.getId()
									+ "'"
									+ "AND "
									+ "User_userId = '"
									+ n.getUserId()
									+"';");
						}
					
			// Message löschen - SQL Query ausführen
			
						System.out.println("DELETE FROM Note WHERE "
								+ "noteId = "
								+ n.getId());
			
					stmt.executeUpdate("DELETE FROM Note WHERE "
					+ "noteId = "
					+ n.getId());
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
