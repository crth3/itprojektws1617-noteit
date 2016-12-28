package de.hdm.itprojekt.noteit.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.ReportService;
import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.NotePermission;
import de.hdm.itprojekt.noteit.shared.bo.User;
import de.hdm.itprojekt.noteit.shared.report.Column;
import de.hdm.itprojekt.noteit.shared.report.NotesGeneralInformation;
import de.hdm.itprojekt.noteit.shared.report.NotesSharingInformation;
import de.hdm.itprojekt.noteit.shared.report.ParagraphComposite;
import de.hdm.itprojekt.noteit.shared.report.ParagraphSimple;
import de.hdm.itprojekt.noteit.shared.report.Report;
import de.hdm.itprojekt.noteit.shared.report.Row;

/**
 * 
 * @author maikzimmermann
 *
 */
public class ReportServiceImpl extends RemoteServiceServlet 
implements ReportService {
	
	private NotesAdministration notesAdministration = null;
	private static final long serialVersionUID = 1L;
	
	private String sPermission = null;
	private String sPermissionRead = "Lesen";
	private String sPermissionReadWrite = "Lesen & Schreiben";
	private String sPermissionReadWriteDelete = "Lesen, Schreiben & Löschen";
	private int noteId;
	
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
	 * Method to get a <code>Report</code> Object of all Notes by a user
	 */
	@Override
		public NotesGeneralInformation createReportNotesGeneralInformation(User u,  Timestamp maturity, Timestamp creationDate, Timestamp modificationDate)
			throws IllegalArgumentException {
		if (this.getNotesAdministration() == null)
			return null;

		NotesGeneralInformation result = new NotesGeneralInformation();

		result
				.setTitle("Informationen über Titeln von Notizbüchern und Notizen, sowie "
						+ "Erstell-, Modifikations-, und Fälligkeitsdaten,");

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

		headline.addColumn(new Column("Notiz.-ID."));
		headline.addColumn(new Column("Notiz"));
		headline.addColumn(new Column("Titel"));
		headline.addColumn(new Column("Erstelldatum"));
		headline.addColumn(new Column("Modifikationsdatum"));
		headline.addColumn(new Column("Fälligkeitsdatum"));

		result.addRow(headline);

		ArrayList<Note> notes = this.notesAdministration.findNoteByUserId(u.getId());
		ArrayList<Note> maturityDates = this.notesAdministration.findNoteByMaturity(maturity);
		ArrayList<Note> creationDates = this.notesAdministration.findNoteByCreationDate(creationDate);
		ArrayList<Note> modificationDates = this.notesAdministration.findNoteByModificationDate(modificationDate);
		
		
		if (maturityDates != null) {
			
			for (Note foundedMaturity : maturityDates) {

				Row noteRow = new Row();

				noteRow.addColumn(new Column("" + foundedMaturity.getId()));
				noteRow.addColumn(new Column("" + foundedMaturity.getTitle()));
				noteRow.addColumn(new Column("" + foundedMaturity.getTitle())); 
				noteRow.addColumn(new Column("" + foundedMaturity.getCreationDate()));
				noteRow.addColumn(new Column("" + foundedMaturity.getModificationDate()));
				noteRow.addColumn(new Column("" + foundedMaturity.getMaturityDate()));

				result.addRow(noteRow);
			}
			
		}
		
if (creationDates != null) {
			
			for (Note foundedCreationDate : creationDates) {

				Row noteRow = new Row();

				noteRow.addColumn(new Column("" + foundedCreationDate.getId()));
				noteRow.addColumn(new Column("" + foundedCreationDate.getTitle()));
				noteRow.addColumn(new Column("" + foundedCreationDate.getTitle())); 
				noteRow.addColumn(new Column("" + foundedCreationDate.getCreationDate()));
				noteRow.addColumn(new Column("" + foundedCreationDate.getModificationDate()));
				noteRow.addColumn(new Column("" + foundedCreationDate.getMaturityDate()));

				result.addRow(noteRow);
			}
		}

if (modificationDates != null) {
	
	for (Note foundedModificationDates : modificationDates) {

		Row noteRow = new Row();

		noteRow.addColumn(new Column("" + foundedModificationDates.getId()));
		noteRow.addColumn(new Column("" + foundedModificationDates.getTitle()));
		noteRow.addColumn(new Column("" + foundedModificationDates.getTitle())); 
		noteRow.addColumn(new Column("" + foundedModificationDates.getCreationDate()));
		noteRow.addColumn(new Column("" + foundedModificationDates.getModificationDate()));
		noteRow.addColumn(new Column("" + foundedModificationDates.getMaturityDate()));

		result.addRow(noteRow);
	}
}

	
		for (Note foundedNote : notes) {

			Row noteRow = new Row();

			noteRow.addColumn(new Column("" + foundedNote.getId()));
			noteRow.addColumn(new Column("" + foundedNote.getTitle()));
			noteRow.addColumn(new Column("" + foundedNote.getTitle())); 
			noteRow.addColumn(new Column("" + foundedNote.getCreationDate()));
			noteRow.addColumn(new Column("" + foundedNote.getModificationDate()));
			noteRow.addColumn(new Column("" + foundedNote.getMaturityDate()));

			result.addRow(noteRow);
		}

		return result;
	}

	@Override
	public NotesSharingInformation createReportNotesSharingInformation(User u) 
			throws IllegalArgumentException {
			if (this.getNotesAdministration() == null)
			return null;
			
			int userId = u.getId();
			
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
				result.addRow(NotePremissionRow);
			    }
		    
		    
			/*
		     * Zum Schluss m�ssen wir noch den fertigen Report zur�ckgeben.
		     */    
		return result;
		
	}
	
}
	
