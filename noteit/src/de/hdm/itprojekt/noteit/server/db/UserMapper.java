package de.hdm.itprojekt.noteit.server.db;

	import java.sql.Connection;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.ArrayList;
	import java.util.logging.Level;
	import java.util.logging.Logger;

	
	import de.hdm.itprojekt.noteit.shared.bo.User;

	/**
	 * <p>
	 * Mapper-Klasse zur Abbildung von <code>User</code> Objekten auf die Datenbank.
	 * Über das Mapping können sowohl Objekte bzw deren Attribute in die Datenbank 
	 * geschrieben, als auch von der Datenbank ausgelesen werden.
	 * </p>
	 * <p>
	 * Es werden Methoden zum Erstellen, Ändern, Löschen und Ausgeben von Nutzern
	 * bereitgestellt.
	 * </p>
	 * @author Christian Roth
	 * 
	 */

	public class UserMapper {
		
		private static Logger rootLogger = Logger.getLogger("");
		
		private static UserMapper userMapper = null;
		
		
		/**
		 * Privater Konstruktor verhindert das Erzeugen neuer Instanzen
		 * mittels des <code>new</code> Keywords.
		 */
		private UserMapper() {
			
		}
		
		/**
		 * Singleton
		 * @return
		 */
		public static UserMapper userMapper() {
			if(userMapper == null) {
				userMapper = new UserMapper();
			}
			
			return userMapper;
		}
		
		
		/**
		 * Suche eines Nutzers anhand seiner einzigartigen ID.
		 * 
		 * @param id - Primärschlüssel von Nutzer
		 * @return Nutzer Objekt, das die gesuchte ID enthält
		 */
		public User findByID(int id) {
			// Datenbankverbindung öffnen
			Connection con = DBConnection.connection();
			
			try {
				// Neues SQL Statement anlegen
				Statement stmt = con.createStatement();
				// SQL Query ausführen
				ResultSet rs = stmt.executeQuery("SELECT userId, firstName, lastName, emailAddress FROM User " +
						"WHERE userId = " + id);
				// Bei Treffer 
				if(rs.next()) {
					// Neues User Objekt erzeugen
					User u = new User();
					// Id, Vorname, Nachname und Email mit den Daten aus der DB füllen
					u.setId(rs.getInt("userId"));
					u.setFirstName(rs.getString("firstName"));
					u.setLastName(rs.getString("lastName"));
					u.setMail(rs.getString("emailAddress"));
					// Objekt zurückgeben
					return u;
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
		 * Suche eines Nutzers anhand seines Vor- und Nachnamens.
		 * Da Vor- und Nachname nicht eindeutig sind, können mehrere 
		 * Ergebnisse ausgegeben werden. Alle gefundenen Nutzer werden in einem
		 * Vektor gespeichert.
		 * 
		 * @param firstName Vorname des gesuchten Nutzers
		 * @param lastName Nachname des gesuchten Nutzers
		 * @return Vektor mit allen zu den Suchparametern gefundenen Nutzern
		 */
		public ArrayList<User> findByName(String firstName, String lastName) {
			// Datenbankverbindung 
			Connection con = DBConnection.connection();
			// Ergebnisvektor anlegen
			ArrayList<User> result = new ArrayList<User>();
			
			try {
				// neues SQL Statement anlegen
				Statement stmt = con.createStatement();
				// SQL Query ausführen
				ResultSet rs = stmt.executeQuery("SELECT userId, firstName, lastName, emailAddress FROM User " +
						"WHERE firstName = '" + firstName + "' AND lastName = '" + lastName +"'");
				// Für jeden gefundenen Treffer...
				while (rs.next()) {
					// ... neues User Objekt anlegen
					User u = new User();
					// ... Id, Vorname, Nachname und Email mit den Daten aus der DB füllen
					u.setId(rs.getInt("userId"));
					u.setFirstName(rs.getString("firstName"));
					u.setLastName(rs.getString("lastName"));
					u.setMail(rs.getString("emailAddress"));
					// ... Objekt dem Ergebnisvektor hinzufügen
					result.add(u);
				}
			}
			// Error Handling
			catch (SQLException e) {
				e.printStackTrace();
			}
			// Ergebnis zurückgeben
			return result;
		}
		
		/**
		 * Suche eines Nutzers anhand seiner einzigartigen Email-Adresse.
		 * 
		 * @param email Die Email-Adresse des gesuchten Nutzers
		 * @return Nutzer Objekt, das die gesuchte Email-Adresse enthält
		 */
		public User findByEmail(String email) {
			// Datenbankverbindung öffnen
			Connection con = DBConnection.connection();
			
			try {
				// neues SQL Statement anlegen
				Statement stmt = con.createStatement();
				// SQL Query ausführen
				ResultSet rs = stmt.executeQuery("SELECT userId, firstName, lastName, emailAddress FROM User " +
						"WHERE emailAddress = '" + email + "'");
				// Wenn der Eintrag gefunden wurde
				if(rs.next()) {
					// Neues User Objekt anlegen
					User u = new User();
					// Das Objekt mit Daten aus der DB füllen
					u.setId(rs.getInt("userId"));
					u.setFirstName(rs.getString("firstName"));
					u.setLastName(rs.getString("lastName"));
					u.setMail(rs.getString("emailAddress"));
					
					return u;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return null;
			}
		
		/**
		 * Ausgabe aller Nutzer Datensätze
		 * 
		 * @return Vektor mit allen registrierten Nutzern
		 */
		public ArrayList<User> findAll() {
			// Datenbankverbindung öffnen
			Connection con = DBConnection.connection();
			// Ergebnisvektor anlegen
			ArrayList<User> result = new ArrayList<User>();
			
			try {
				// neues SQL Statement anlegen
				Statement stmt = con.createStatement();
				// SQL Query ausführen
				// TODO evtl. OrderBy ergänzen
				ResultSet rs = stmt.executeQuery("SELECT userId, firstName, lastName, emailAddress " +
						"FROM User");
				// Für jeden Eintrag neues User Objekt erzeugen
				while(rs.next()) {
					User u = new User();
					u.setId(rs.getInt("userId"));
					u.setFirstName(rs.getString("firstName"));
					u.setLastName(rs.getString("lastName"));
					u.setMail(rs.getString("emailAddress"));
					// User dem Ergebnisvektor hinzufügen
					result.add(u);
				}
			}
			// Error Handling
			catch (SQLException e) {
				e.printStackTrace();
			}
			// Ergebnisvektor zurückgeben
			return result;
		}
		
		/**
		 * Neuen Nutzer in der Datenbank anlegen.
		 * 
		 * @param u Nutzer Objekt, das in die Datenbank eingefügt werden soll
		 */
		public User insert(User u) {
			
			// Datenbankverbindung öffnen
			Connection con = DBConnection.connection();
			System.out.println("dbconnection: " + con);
			System.out.println("user: " + u.getFirstName() + u.getLastName());
			
			try {
				// neues SQL Statement anlegen
				Statement stmt = con.createStatement();
				// SQL Query ausführen um die höchste id zu erhalten
				ResultSet rs = stmt.executeQuery("SELECT MAX(userId) AS maxId FROM User");
				if(rs.next()) {
					// id um 1 erhöhen, damit ein neuer Eintrag erzeugt wird
					u.setId(rs.getInt("maxId") + 1);
					// neues SQL Statement
					stmt = con.createStatement();
					// SQL Query ausführen um Datensatz in DB zu schreiben
					stmt.executeUpdate("INSERT INTO User (userId, firstName, lastName, emailAddress) " +
							"VALUES "
							+ "('" 
							+ u.getId() 
							+ "', '" 
							+ u.getFirstName() 
							+ "', '" 
							+ u.getLastName() 
							+ "', '" 
							+ u.getMail() 
							+ "')");
				}
			}
			// Error Handling
			catch (SQLException e) {
				e.printStackTrace();
			}
			return u;
		}
		
		/**
		 * Nutzerdaten eines bestehenden Nutzers in der Datenbank ändern
		 * 
		 * @param u das bereits geänderte Nutzerobjekt
		 */
		public User update(User u) {
			// Datenbankverbindung öffnen
			Connection con = DBConnection.connection();
			
			try {
				// neues SQL Statement anlegen
				Statement stmt = con.createStatement();
				// SQL Query ausführen
				stmt.executeUpdate("UPDATE User "
						+ "SET firstName = '" 
						+ u.getFirstName() 
						+ "', lastName = '" +
						u.getLastName() 
						+ "', emailAddress = '" 
						+ u.getMail() 
						+ "', WHERE userId = " 
						+ u.getId());
			}
			// Error Handling
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			return u;
		}
		
		/**
		 * Diese Methode löscht einen Nutzer in der Datenbank die dazugehörigen Nutzer-Referenzen in allen Tabellen
		 * 
		 * @param u der zu löschende Nutzer
		 */
		public void delete(User u) {
			// Datenbankverbindung öffnen
			Connection con = DBConnection.connection();
		
			try {
			// neues SQL Statement anlegen
			Statement stmt = con.createStatement();
			// SQL Query ausführen
			stmt.executeUpdate("DELETE FROM User WHERE userId = " + u.getId());
		}
			// Error Handling
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
		
