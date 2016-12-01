package de.hdm.itprojekt.noteit.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.User;

/**
 * <p>
 * Mapper-Klasse zur Abbildung von <code>Notebook</code> Objekten auf die Datenbank.
 * Über das Mapping können sowohl Objekte bzw deren Attribute in die Datenbank 
 * geschrieben, als auch von der Datenbank ausgelesen werden.
 * </p>
 * <p>
 * Es werden Methoden zum Erstellen, Ändern, Löschen und Ausgeben von Notebooks
 * bereitgestellt.
 * @author Christian Roth
 */
public class NotebookMapper {
	
	private static NotebookMapper notebookMapper = null;
	
	/**
	 * Privater Konstruktor verhindert das Erzeugen neuer Instanzen
	 * mittels des <code>new</code> Keywords.
	 */
	private NotebookMapper() {
		
	}
	
	/**
	 * Singleton
	 * @return
	 */
	public static NotebookMapper notebookMapper() {
		if(notebookMapper == null) {
			notebookMapper = new NotebookMapper();
		}
		
		return notebookMapper;
	}
	
	/**
	 * Suche eine Notebook anhand seiner einzigartigen ID.
	 * 
	 * @param id - Primärschlüssel von Notebook
	 * @return Notebook Objekt, das die gesuchte ID enthält
	 */
	public Notebook findById(int id) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// Neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
			ResultSet rs = stmt.executeQuery("SELECT notebookId, title, creationDate, User_userId FROM Notebook " +
					"WHERE notebookId = " + id);
			// Bei Treffer 
			if(rs.next()) {
				// Neues Notebook Objekt erzeugen
				Notebook nb = new Notebook();
				// Id und Konversation mit den Daten aus der DB füllen
				nb.setId(rs.getInt("notebookId"));
				nb.setTitle(rs.getString("title"));
				nb.setCreationDate(rs.getTimestamp("creationDate"));
				nb.setUserId(rs.getInt("User_userId"));
			
				// Objekt zurückgeben
				return nb;
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
	 * Diese Methode gibt alle Notebooks, die zu einem User gehören
	 * anhand der userId in einer Liste aus
	 * 
	 * @param id
	 *            Eindeutiger Identifikator des Notebook in der Datenbank
	 * @return Liste der Notebooks
	 */
	public ArrayList<Notebook> findNotebooksByUserID(int id) {

		Connection con = DBConnection.connection();
		ArrayList<Notebook> notebookList = new ArrayList<Notebook>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT notebookId, title, creationDate "
							+ "FROM Notebook INNER JOIN NotebookPermission ON "
							+ "Notebook.notebookId = NotebookPermission.Notebook_notebookId "
							+ "WHERE Notebook.User_userId = "
							+ id 
							+ " GROUP BY notebookId");
			
			System.out.println("SELECT notebookId, title, creationDate "
					+ "FROM Notebook INNER JOIN NotebookPermission ON "
					+ "Notebook.notebookId = NotebookPermission.Notebook_notebookId "
					+ "WHERE Notebook.User_userId = "
					+ id 
					+ " GROUP BY notebookId");

			while (rs.next()) {
				Notebook nb = new Notebook();
				
				nb.setId(rs.getInt("notebookId"));
				nb.setTitle(rs.getString("title"));
				nb.setCreationDate(rs.getTimestamp("creationDate"));
				nb.setUserId(rs.getInt("User_userId"));
				
		
				System.out.println(rs);
				// Notebook Objekt der Liste hinzufügen
				notebookList.add(nb);
			}
			// Objekt zurückgeben
			return notebookList;
		}
		// Error-Handlung
		catch (SQLException e) {
			e.printStackTrace();

		}
		return notebookList;
	}
	
	/**
	 * Neues Notebook in der Datenbank anlegen.
	 * 
	 * @param nb Notebook Objekt, das in die Datenbank eingefügt werden soll
	 * @return 
	 */
	public Notebook insert(Notebook nb) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen um die höchste id zu erhalten
			ResultSet rs = stmt.executeQuery("SELECT MAX(notebookId) AS maxId FROM Notebook");
			if(rs.next()) {
				// id um 1 erhöhen, damit ein neuer Eintrag erzeugt wird
				nb.setId(rs.getInt("maxId") + 1);
				// neues SQL Statement
				stmt = con.createStatement();
				// SQL Query ausführen um Datensatz in DB zu schreiben
				stmt.executeUpdate("INSERT INTO Notebook (notebookId, title, creationDate, User_userId) " +
						"VALUES (" 
						+ nb.getId() 
						+ ", '" 
						+ nb.getTitle() 
						+ "', '" 
						+ nb.getCreationDate() 
						+ "', '" 
						+ nb.getUserId() 
						+"')");
			}
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return nb;
	}
	
	/**
	 * Notebook aus der Datenbank bearbeiten
	 * 
	 * @param nb das zu bearbeitende Notebook
	 * @return nb
	 */
	public Notebook edit(Notebook nb) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
			System.out.println("UPDATE Notebook SET title='" + nb.getTitle() +"' WHERE notebookId="+nb.getId());
	
			stmt.executeUpdate("UPDATE Notebook SET title='" + nb.getTitle() +"' WHERE notebookId="+nb.getId());
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
		//Rückgabe des Notebooks
		return nb;
	}
	
	/**
	 * Notebook aus der Datenbank löschen
	 * 
	 * @param nb das zu löschende Notebook
	 */
	public void delete(Notebook nb) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
			stmt.executeUpdate("DELETE FROM Notebook WHERE notebookId = " + nb.getId());
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

}
