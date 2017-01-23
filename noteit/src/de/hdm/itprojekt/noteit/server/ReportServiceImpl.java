package de.hdm.itprojekt.noteit.server;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.ReportService;
import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.NotePermission;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.User;
import de.hdm.itprojekt.noteit.shared.report.Column;
import de.hdm.itprojekt.noteit.shared.report.NotesGeneralInformation;
import de.hdm.itprojekt.noteit.shared.report.NotesSharingInformation;
import de.hdm.itprojekt.noteit.shared.report.ParagraphComposite;
import de.hdm.itprojekt.noteit.shared.report.ParagraphSimple;
import de.hdm.itprojekt.noteit.shared.report.Report;
import de.hdm.itprojekt.noteit.shared.report.Row;

public class ReportServiceImpl extends RemoteServiceServlet 
implements ReportService {
	
	private NotesAdministration notesAdministration = null;
	private static final long serialVersionUID = 1L;
	private String sPermission = null;
	private String sPermissionRead = "Lesen";
	private String sPermissionReadWrite = "Lesen & Schreiben";
	private String sPermissionReadWriteDelete = "Lesen, Schreiben & Löschen";
	private int noteId;
	boolean bPermission;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static Logger rootLogger = Logger.getLogger("");

	
	/**
	 * No Argument Kontstruktor
	 * @throws IllegalArgumentException
	 */
	public ReportServiceImpl() throws IllegalArgumentException {
		
	}
	
	/**
	 * Initialization Method
	 */
	public void init() {
		NotesAdministrationImpl notesAdministrationImpl = new NotesAdministrationImpl();
		notesAdministrationImpl.init();
		this.notesAdministration = notesAdministrationImpl;
	}


	
	/**
	 * Method to get the <code>AdministrationService</code> Object
	 * 
	 * @return AdministrationService
	 */
	protected NotesAdministration getNotesAdministration(){
		return this.notesAdministration;
	}

	/**
	 * Method to add Imprint
	 * @param r
	 */
	public void addImprint(Report r) {
		/*
		 * Das Impressum soll wesentliche Informationen über die Noteit
		 * enthalten.
		 */

		/*
		 * Das Imressum soll mehrzeilig sein.
		 */
		ParagraphComposite imprint = new ParagraphComposite();

		imprint.addSubParagraph(new ParagraphSimple("Report"));
		imprint.addSubParagraph(new ParagraphSimple("Hochschule der Medien"));
		imprint.addSubParagraph(new ParagraphSimple("Nobelstraße 10"));
		imprint.addSubParagraph(new ParagraphSimple("73733 Stuttgart"));

		r.setImprint(imprint);
	}
		
	/**
	 * Method to convert Date to Timestamp
	 * @param date
	 * @return
	 */
	public static Timestamp getTimestamp(Date date) { 
		return date == null ? null : new java.sql.Timestamp(date.getTime()); 
		}
	
	
	/**
	 * Method to get a <code>Report</code> Object of all Notes based on 
	 * NoteKeyword, NotebookKeyword, User, Maturity, CreationDate or ModificationDate 
	 */
	@Override
		public NotesGeneralInformation createReportNotesGeneralInformation(User u, String sKeywordNote, String sKeywordNotebook, Date fromMaturity, Date toMaturity, 
				Date fromCreationDate, Date toCreationDate, Date fromModificationDate, Date toModificationDate)
			throws IllegalArgumentException {
		if (this.getNotesAdministration() == null)
			return null;
		
		int userId = u.getId();
		rootLogger.log(Level.SEVERE, "userId: " + userId);

		
		//-------------------- convert date to Timestamp ---------------------------
		Timestamp tFromMaturity = getTimestamp(fromMaturity);
		Timestamp tFromCreationDate = getTimestamp(fromCreationDate);
		Timestamp tFromModificationDate = getTimestamp(fromModificationDate);
		Timestamp tToMaturity = getTimestamp(toMaturity);
		Timestamp tToCreationDate = getTimestamp(toCreationDate);
		Timestamp tToModificationDate = getTimestamp(toModificationDate);
		

		NotesGeneralInformation result = new NotesGeneralInformation();

		result
				.setTitle("Informationen über Titeln von Notizbüchern und Notizen, sowie "
						+ "Erstell-, Modifikations-, und Fälligkeitsdaten");
		this.addImprint(result);

		result.setCreated(new Date());
		
		 /*
	     * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die oben
	     * auf dem Report stehen) des Reports. Die Kopfdaten sind mehrzeilig, daher
	     * die Verwendung von ParagraphComposite.
	     */
	    ParagraphComposite header = new ParagraphComposite();

	    // Name und Vorname des Kunden aufnehmen
	    if (u.getId() != 0) {
	    header.addSubParagraph(new ParagraphSimple(u.getLastName() + ", "
	        + u.getFirstName()));

	    // User aufnehmen
	    	header.addSubParagraph(new ParagraphSimple("User.-Nr.: " + u.getId()));
	    	
	    }
	    
	    // Hinzuf�gen der zusammengestellten Kopfdaten zu dem Report
	    result.setHeaderData(header);
		

		/*
		 * Ab hier erfolgt ein zeilenweises hinzufügen von Notiz-Informationen.
		 */
		Row headline = new Row();
		
		if (sKeywordNotebook != null) {
		
		headline.addColumn(new Column("Notizbuch-ID"));
		headline.addColumn(new Column("Notizbuch-Titel"));
		
		}

		
		headline.addColumn(new Column("Notiz-ID"));
		headline.addColumn(new Column("Titel"));
		headline.addColumn(new Column("Inhalt"));
		headline.addColumn(new Column("Fälligkeitsdatum"));
		headline.addColumn(new Column("Erstelldatum"));
		headline.addColumn(new Column("Änderungsdatum"));

		result.addRow(headline);
		
		
		// Ergebnis - ArrayListen anlegen
		ArrayList<Note> allNotes = this.notesAdministration.getAllNotes();		
		ArrayList<Notebook> allNotebooks = this.notesAdministration.getAllNotebooks();	
		
		rootLogger.log(Level.SEVERE, "Size of list: " + allNotes.size());

		 
			if (userId != 0) {
			// Schleife die Objekte aus der allNotes-ArrayList löscht, wenn diese nicht mit den Kriterien übereinstimmen
			 for (java.util.Iterator<Note> iterator = allNotes.iterator(); iterator.hasNext();  ) {
					Note user = iterator.next();
					// Wenn das Objekt nicht der gesuchten UserId entspricht, löschen
					if (userId != user.getUserId()) {
						 iterator.remove();
							rootLogger.log(Level.SEVERE, "Size of list after removed: " + allNotes.size());

						}
			 		}
			}
			
			if (sKeywordNote != null) {
				// Schleife die Objekte aus der allNotes-ArrayList löscht, wenn diese nicht mit den Kriterien übereinstimmen
				 for (java.util.Iterator<Note> iterator = allNotes.iterator(); iterator.hasNext();  ) {
						Note skn = iterator.next();
						// Wenn das Objekt nicht der gesuchten UserId entspricht, löschen
						
						String content = skn.getTitle();
						
						if (content.toLowerCase().indexOf(sKeywordNote.toLowerCase()) == -1) {
							rootLogger.log(Level.SEVERE, "found content:" + content);
							 iterator.remove();
								rootLogger.log(Level.SEVERE, "Size of list after removed: " + allNotes.size());
							}
				 		}
				}
			
			// checken welches Notebook, den gewünschten Titel enthält, wenn nicht aus ArrayList löschen.
			if (sKeywordNotebook != null) {
				// Schleife die Objekte aus der allNotes-ArrayList löscht, wenn diese nicht mit den Kriterien übereinstimmen
				 for (java.util.Iterator<Notebook> iterator = allNotebooks.iterator(); iterator.hasNext();  ) {
						Notebook sknb = iterator.next();
						// Wenn das Objekt nicht der gesuchten UserId entspricht, löschen
						
						String content = sknb.getTitle();
						
						if (content.toLowerCase().indexOf(sKeywordNotebook.toLowerCase()) == -1) {
							rootLogger.log(Level.SEVERE, "found content:" + content);
							 iterator.remove();
							rootLogger.log(Level.SEVERE, "Size of Notebooklist after removed: " + allNotebooks.size());

							}
				 		}
				 
				}
			
			// Wenn ein vonDatum oder bisDatum eingegeben wurde, dann in Schleife
			if (tFromMaturity != null) {
				System.out.println("tFromMaturity: " +tFromMaturity);
			 for (java.util.Iterator<Note> iterator = allNotes.iterator(); iterator.hasNext();  ) {
						Note m = iterator.next();
						// Wenn ein Objekt Wert null in der DB hat, Objekt löschen
						if(m.getMaturityDate() == null) {
							 iterator.remove();
								rootLogger.log(Level.SEVERE, "Size of list after removed: " + allNotes.size());
						} 
						 // Wenn ein Objekt nicht der gesuchten getMaturityDate entspricht, löschen
							else if (m.getMaturityDate().before(tFromMaturity)) {
							 iterator.remove();
								rootLogger.log(Level.SEVERE, "Size of list after removed: " + allNotes.size());
						} 
				}
			}
			
			if (tToMaturity != null) {
				System.out.println("tToMaturity: " +tToMaturity);
			 for (java.util.Iterator<Note> iterator = allNotes.iterator(); iterator.hasNext();  ) {
						Note m = iterator.next();
						// Wenn ein Objekt Wert null in der DB hat, Objekt löschen
						if(m.getMaturityDate() == null) {
							 iterator.remove();
								rootLogger.log(Level.SEVERE, "Size of list after removed: " + allNotes.size());
						} 
						 // Wenn ein Objekt nicht der gesuchten getMaturityDate entspricht, löschen
							else if (m.getMaturityDate().after(tToMaturity) && !m.getMaturityDate().equals(tToMaturity)) {
							 iterator.remove();
								rootLogger.log(Level.SEVERE, "Size of list after removed: " + allNotes.size());
						} 
				}
			}
			
			if (tFromCreationDate != null ) {
				System.out.println("fromCreationDate: " +tFromCreationDate);

			 for (java.util.Iterator<Note> iterator = allNotes.iterator(); iterator.hasNext();  ) {
						Note c = iterator.next();
						
						// Wenn das Objekt nicht dem gesuchten creationDate entspricht, löschen
						if (c.getCreationDate().before(tFromCreationDate)) {
							 iterator.remove();
								rootLogger.log(Level.SEVERE, "Size of list after removed: " + allNotes.size());
						}	
				}
			}
			
			if (tToCreationDate != null ) {
				System.out.println("tToCreationDate: " +tToCreationDate);

			 for (java.util.Iterator<Note> iterator = allNotes.iterator(); iterator.hasNext();  ) {
						Note c = iterator.next();
						
						// Wenn das Objekt nicht dem gesuchten creationDate entspricht, löschen
						if (c.getCreationDate().after(tToCreationDate) && !c.getCreationDate().equals(tToCreationDate)) {
							 iterator.remove();
								rootLogger.log(Level.SEVERE, "Size of list after removed: " + allNotes.size());
						}	
				}
			}
			
			
			if (tFromModificationDate != null ) {

				System.out.println("modificationDate: " +tFromModificationDate);
			 for (java.util.Iterator<Note> iterator = allNotes.iterator(); iterator.hasNext();  ) {
						Note mD = iterator.next();
						//Null-Objekte löschen
						if (mD.getModificationDate() == null) {
							 iterator.remove();
								rootLogger.log(Level.SEVERE, "Size of list after removed: " + allNotes.size());
								// Wenn das Objekt nicht dem gesuchten modificationDate entspricht, löschen
						} else if (mD.getModificationDate().before(tFromModificationDate)) {
							 iterator.remove();
								rootLogger.log(Level.SEVERE, "Size of list after removed: " + allNotes.size());
						}
				}
			}
			
			if (tToModificationDate != null ) {

				System.out.println("tToModificationDate: " +tToModificationDate);
			 for (java.util.Iterator<Note> iterator = allNotes.iterator(); iterator.hasNext();  ) {
						Note mD = iterator.next();
						//Null-Objekte löschen
						if (mD.getModificationDate() == null) {
							 iterator.remove();
								rootLogger.log(Level.SEVERE, "Size of list after removed: " + allNotes.size());
								// Wenn das Objekt nicht dem gesuchten modificationDate entspricht, löschen
						} else if (mD.getModificationDate().after(tToModificationDate) && !mD.getModificationDate().equals(tToModificationDate)) {
							 iterator.remove();
								rootLogger.log(Level.SEVERE, "Size of list after removed: " + allNotes.size());
						}
				}
			}
		 	
			
			// Notes die nicht das gewünschte Suchwort nach Notizbuchtiteln erhalten, löschen
			 for (Notebook foundedNotebook : allNotebooks) {
				 
				 System.out.println("founded Notebook-ID: " +foundedNotebook.getId());
				 
				 if (sKeywordNotebook != null) {
					
				 for (java.util.Iterator<Note> iterator = allNotes.iterator(); iterator.hasNext();  ) {
						Note n = iterator.next();
						// Wenn das Objekt nicht der gesuchten getMaturityDate entspricht, löschen
						 System.out.println("nb getNotebookId: " +n.getNotebookId());
						if (n.getNotebookId() != foundedNotebook.getId() || allNotebooks.size() == 0) {
							 iterator.remove();
								rootLogger.log(Level.SEVERE, "Size of list after removed (Keyword-Search): " + allNotes.size());
						}	
				} 
				 
			} 
				 
	}
		// Wenn Notebook Titel nicht gefunden wurde, keine Notes ausgeben
		if (allNotebooks.size() == 0) {
			
			allNotes.removeAll(allNotes);
			
		}
					
		
				
		// Schleife für das hinzufügen der selektierten Notes zum Report
		for (Note selectedNote : allNotes) {
			
			//aktuelles notebook objekt laden, um titel zu bekommen
			Notebook n = this.notesAdministration.findNotebookById(selectedNote.getNotebookId());
			
			Row noteRow = new Row();
			
			if (sKeywordNotebook != null) {
			
			noteRow.addColumn(new Column("" + selectedNote.getNotebookId()));
			noteRow.addColumn(new Column("" + n.getTitle()));

			}
			
			// Timestamp in Date umwandeln
			Timestamp tsMaturity = selectedNote.getMaturityDate();			
			Timestamp tsCreationDate = selectedNote.getCreationDate();		
			Timestamp tsModificationDate = selectedNote.getModificationDate();			
			
			Date dateMaturity = tsMaturity;
			Date dateCreationDate = tsCreationDate;
			Date dateModificationDate = tsModificationDate;
			
			String stringMaturityDate = null;
			String stringCreationDate= null;
			String stringModificationDate = null;
			
			noteRow.addColumn(new Column("" + selectedNote.getId()));
			noteRow.addColumn(new Column("" + selectedNote.getTitle()));
			noteRow.addColumn(new Column("" + selectedNote.getText())); 
			
			if (dateMaturity != null) {
				
				// Date Format anpassen
				stringMaturityDate = dateFormat.format(dateMaturity);
				
				//neues Date Format dem Report hinzufügen
				noteRow.addColumn(new Column("" + stringMaturityDate));
				
			}else {
				noteRow.addColumn(new Column("-" ));
			}
			
			
			if (dateCreationDate != null) {
				
				// Date Format anpassen
				stringCreationDate = dateFormat.format(dateCreationDate);
				
				//neues Date Format dem Report hinzufügen
				noteRow.addColumn(new Column("" + stringCreationDate));	
				
			}else {
				noteRow.addColumn(new Column("-" ));
			}
			
			if (dateModificationDate != null) {
				
				// Date Format anpassen
				stringModificationDate = dateFormat.format(dateModificationDate);
				
				//neues Date Format dem Report hinzufügen
				noteRow.addColumn(new Column("" + stringModificationDate));
				
			}else {
				noteRow.addColumn(new Column("-" ));
			}
			
			
			result.addRow(noteRow);	
			
		}
	
			return result;
		}

	
	/**
	 * Method to get a <code>Report</code> Object of all Notes based on 
	 * User and Permission
	 */
	@Override
	public NotesSharingInformation createReportNotesSharingInformation(User u, int permission) 
			throws IllegalArgumentException {
			if (this.getNotesAdministration() == null)
			return null;
			
			int userId = u.getId();
			
			System.out.println("Selected Permission: "+permission);
			
			/*
		     * Zun�chst legen wir uns einen leeren Report an.
		     */
			NotesSharingInformation result = new NotesSharingInformation();
			
			result
			.setTitle("Freigaben von Notizen eines Nutzer");
			
			 // Imressum hinzuf�gen
		    this.addImprint(result);
		    
		    /*
		     * Datum der Erstellung hinzuf�gen. new Date() erzeugt autom. einen
		     * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		     */
		    result.setCreated(new Date());
		    
		    /*
		     * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die oben
		     * auf dem Report stehen) des Reports. Die Kopfdaten sind mehrzeilig, daher
		     * die Verwendung von ParagraphComposite.
		     */
		    ParagraphComposite header = new ParagraphComposite();

		    // Name und Vorname des Kunden aufnehmen
		    header.addSubParagraph(new ParagraphSimple(u.getLastName() + ", "
		        + u.getFirstName()));

		    // User aufnehmen
		    header.addSubParagraph(new ParagraphSimple("User.-Nr.: " + u.getId()));
		    
		    // Hinzuf�gen der zusammengestellten Kopfdaten zu dem Report
		    result.setHeaderData(header);
		    
		    
		    /*
		     * Zun�chst legen wir eine Kopfzeile f�r die Konto-Tabelle an.
		     */
		    Row headline = new Row();

		    /*
		     * Wir wollen Zeilen mit 2 Spalten in der Tabelle erzeugen. In die erste
		     * Spalte schreiben wir die jeweilige Kontonummer und in die zweite den
		     * aktuellen Kontostand. In der Kopfzeile legen wir also entsprechende
		     * �berschriften ab.
		     */
		  
		    headline.addColumn(new Column("Notiz-Nr."));
		    headline.addColumn(new Column("Notiz-Titel"));
		    headline.addColumn(new Column("Notiz"));
		    headline.addColumn(new Column("Berechtigung"));

		    // Hinzuf�gen der Kopfzeile
		    result.addRow(headline);
		    
		    
			ArrayList<NotePermission> notePermissionlist = this.notesAdministration.findNotePermissionByUserId(userId);
					
			for (NotePermission np : notePermissionlist) {

				if (np.getPermission() == permission) {
					bPermission = true;
				}else {
					bPermission = false;
				}
				
				// Berechtigung zuweisen 
				switch(np.getPermission()){ 
			        case 1: 
			        	sPermission = sPermissionRead;
						break; 
			        case 2: 
			        	sPermission = sPermissionReadWrite;
			            break; 
			        case 3: 
			        	sPermission = sPermissionReadWriteDelete;
			            break;
			        default: 
			            System.out.println("keine Berechtigung"); 
			            
			        } 
			    
				// Eine leere Zeile anlegen.
				Row NotePremissionRow = new Row(); 
								
				// erste Spalte
				NotePremissionRow.addColumn(new Column(""+np.getNoteId()));
				
				// Note anhand der id finden
				Note notes = this.notesAdministration.findNoteById(np.getNoteId());
				
				//zweite Spalte
				NotePremissionRow.addColumn(new Column(""+notes.getTitle()));
				
				//dritte Spalte
				NotePremissionRow.addColumn(new Column(""+notes.getText()));
				
			    // Vierte Spalte: 
				NotePremissionRow.addColumn(new Column(""+ sPermission));
	

			    // und schlie�lich die Zeile dem Report hinzuf�gen.
				
				if (bPermission == true || permission == 0) {
					result.addRow(NotePremissionRow);
				}
				
			    }
		    
		    
			/*
		     * Zum Schluss m�ssen wir noch den fertigen Report zur�ckgeben.
		     */    
		return result;
		
	}
	
}
	
