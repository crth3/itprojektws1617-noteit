package de.hdm.itprojekt.noteit.server;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.ReportService;
import de.hdm.itprojekt.noteit.shared.bo.Note;
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
	public NotesSharingInformation createReportNotesSharingInformation()
			throws IllegalArgumentException {
		
		return null;
	}

}