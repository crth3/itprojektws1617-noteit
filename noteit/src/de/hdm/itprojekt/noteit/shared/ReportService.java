package de.hdm.itprojekt.noteit.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojekt.noteit.client.NotesGeneralInformationReport;
import de.hdm.itprojekt.noteit.client.NotesSharingInformationReport;
import de.hdm.itprojekt.noteit.shared.bo.User;
import de.hdm.itprojekt.noteit.shared.report.NotesGeneralInformation;
import de.hdm.itprojekt.noteit.shared.report.NotesSharingInformation;
import de.hdm.itprojekt.noteit.shared.report.ReportSimple;

/**
 * 
 * @author maikzimmermann
 *
 */
@RemoteServiceRelativePath("report")
public interface ReportService extends RemoteService{
	
	/**
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;
	
	/**
	 * GeneralInformationReport erstellen
	 * 
	 * @param u
	 * @param sKeywordNote
	 * @param sKeywordNotebook
	 * @param fromMaturity
	 * @param toMaturity
	 * @param fromCreationDate
	 * @param toCreationDate
	 * @param fromModificationDate
	 * @param toModificationdate
	 * @return
	 */
	public NotesGeneralInformation createReportNotesGeneralInformation(User u, String sKeywordNote, String sKeywordNotebook,
			Date fromMaturity, Date toMaturity, Date fromCreationDate, Date toCreationDate, Date fromModificationDate,
			Date toModificationdate);
	
	/**
	 * SharingInformationReport erstellen
	 * 
	 * @param u
	 * @param permission
	 * @return
	 */
	public NotesSharingInformation createReportNotesSharingInformation(User u, int permission);


}
