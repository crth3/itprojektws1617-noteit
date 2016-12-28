package de.hdm.itprojekt.noteit.shared;

import java.sql.Timestamp;

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
	
	public void init();
	
	public NotesGeneralInformation createReportNotesGeneralInformation(User u, Timestamp maturity, Timestamp creationDate, Timestamp modificationDate);
	public NotesSharingInformation createReportNotesSharingInformation(User u);

}
