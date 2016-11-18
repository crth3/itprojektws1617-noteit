package de.hdm.itprojekt.noteit.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.Source;

public class SourceMapper {
	
	
private static SourceMapper sourceMapper = null;
	
	/**
	 * Privater Konstruktor verhindert das Erzeugen neuer Instanzen
	 * mittels des <code>new</code> Keywords.
	 */
	private SourceMapper() {
		
	}
	
	/**
	 * Singleton
	 * @return
	 */
	public static SourceMapper sourceMapper() {
		if(sourceMapper == null) {
			sourceMapper = new SourceMapper();
		}
		
		return sourceMapper;
	}
	
	/**
	 * Suche eine Source anhand seiner einzigartigen ID.
	 * 
	 * @param id - Primärschlüssel von Source
	 * @return Source Objekt, das die gesuchte ID enthält
	 */
	public Source findById(int id) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// Neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
			ResultSet rs = stmt.executeQuery("SELECT sourceId, sourceId, noteId FROM Source " 
					+ "WHERE sourceId = " 
					+ id);
			// Bei Treffer 
			if(rs.next()) {
				// Neues Source Objekt erzeugen
				Source s = new Source();
				// Id und Source mit den Daten aus der DB füllen
				s.setId(rs.getInt("sourceId"));
				s.setSource(rs.getString("source"));
				s.setNoteId(rs.getInt("Note_noteId"));
			
				// Objekt zurückgeben
				return s;
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
	 * Neue Source in der Datenbank anlegen.
	 * 
	 * @param s Die Source, die in die Datenbank eingefügt werden soll
	 * @return 
	 */
	public Source insert(Source s) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen um die höchste id zu erhalten
			ResultSet rs = stmt.executeQuery("SELECT MAX(notebookId) AS maxId FROM Source");
			if(rs.next()) {
				// id um 1 erhöhen, damit ein neuer Eintrag erzeugt wird
				s.setId(rs.getInt("maxId") + 1);
				// neues SQL Statement
				stmt = con.createStatement();
				// SQL Query ausführen um Datensatz in DB zu schreiben
				stmt.executeUpdate("INSERT INTO Source (sourceId, source, noteId) " +
						"VALUES (" 
						+ s.getId() 
						+ ", '" 
						+ s.getSource()
						+ "', '" 
						+ s.getNoteId()
						+"')");
			}
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	
	/**
	 * Source aus der Datenbank bearbeiten
	 * 
	 * @param s
	 * @return s
	 */
	public Source edit(Source s) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
			stmt.executeUpdate("UPDATE Source SET "
					+ "source=" 
					+ s.getSource() +" WHERE sourceId="+s.getId());
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
		}
		//Rückgabe des Notebooks
		return s;
	}
	
	/**
	 * Source aus der Datenbank löschen
	 * 
	 * @param s die zu löschende Source
	 */
	public boolean delete(int id) {
		// Datenbankverbindung öffnen
		Connection con = DBConnection.connection();
		
		try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
			stmt.executeUpdate("DELETE FROM Source "
					+ "WHERE sourceId = " 
					+ id);
		}
		// Error Handling
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

}
