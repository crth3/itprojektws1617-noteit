package de.hdm.itprojekt.noteit.server;

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
		public NotesGeneralInformation createReportNotesGeneralInformation()
			throws IllegalArgumentException {
		if (this.getNotesAdministration() == null)
			return null;

		NotesGeneralInformation notesGeneralInformation = new NotesGeneralInformation();

		notesGeneralInformation
				.setTitle("Informationen über Titeln von Notizbüchern und Notizen, sowie "
						+ "Erstell-, Modifikations-, und Fälligkeitsdaten,");

		this.addImprint(notesGeneralInformation);

		notesGeneralInformation.setCreated(new Date());
		

		/*
		 * Ab hier erfolgt ein zeilenweises hinzufügen von Notiz-Informationen.
		 */
		Row headline = new Row();

		headline.addColumn(new Column("Notiz.-ID."));
		headline.addColumn(new Column("Notiz"));
		headline.addColumn(new Column("Notizbuch"));
		headline.addColumn(new Column("Erstelldatum"));
		headline.addColumn(new Column("Modifikationsdatum"));
		headline.addColumn(new Column("Fälligkeitsdatum"));

		notesGeneralInformation.addRow(headline);

		ArrayList<Note> notes = this.notesAdministration.getAllNotes();

		for (Note foundedNote : notes) {

			Row noteRow = new Row();

			noteRow.addColumn(new Column("" + foundedNote.getId()));
			noteRow.addColumn(new Column("" + foundedNote.getTitle()));
			noteRow.addColumn(new Column("" + foundedNote.getNotebookId())); // TODO in Notizbuch title ändern
			noteRow.addColumn(new Column("" + foundedNote.getCreationDate()));
			noteRow.addColumn(new Column("" + foundedNote.getModificationDate()));
			noteRow.addColumn(new Column("" + foundedNote.getMaturityDate()));

			notesGeneralInformation.addRow(noteRow);
		}

		return notesGeneralInformation;
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
			NotesSharingInformation notesSharingInformation = new NotesSharingInformation();
			
			notesSharingInformation
			.setTitle("SharingInformationen zu einem Nutzer");
			
			 // Imressum hinzuf�gen
		    this.addImprint(notesSharingInformation);
		    
		    /*
		     * Datum der Erstellung hinzuf�gen. new Date() erzeugt autom. einen
		     * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		     */
		    notesSharingInformation.setCreated(new Date());
		    
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
		    headline.addColumn(new Column("NotePermission-Nr."));
		    headline.addColumn(new Column("NotePermission"));
		    headline.addColumn(new Column("Note-Nr."));

		    // Hinzuf�gen der Kopfzeile
		    notesSharingInformation.addRow(headline);
		    
		    
			ArrayList<NotePermission> notePermissionlist = this.notesAdministration.findNotePermissionByUserId(userId);
			
			for (NotePermission np : notePermissionlist) {
			    
				// Eine leere Zeile anlegen.
				Row NotePremissionRow = new Row(); 
				
				// Erste Spalte: NotePermissionId hinzuf�gen
				NotePremissionRow.addColumn(new Column(""+np.getId()));

			    // Zweite Spalte: 
				NotePremissionRow.addColumn(new Column(""+np.getNoteId()));
				
				// dritte Spalte
				NotePremissionRow.addColumn(new Column(""+np.getNotePermissionId()));

			    // und schlie�lich die Zeile dem Report hinzuf�gen.
				notesSharingInformation.addRow(NotePremissionRow);
			    }
		    
		    
			/*
		     * Zum Schluss m�ssen wir noch den fertigen Report zur�ckgeben.
		     */    
		return notesSharingInformation;
		
		
	}
	
}
