package de.hdm.itprojekt.noteit.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojekt.noteit.client.NotesGeneralInformationReport;
import de.hdm.itprojekt.noteit.client.NotesSharingInformationReport;
import de.hdm.itprojekt.noteit.shared.bo.User;
import de.hdm.itprojekt.noteit.shared.report.NotesGeneralInformation;
import de.hdm.itprojekt.noteit.shared.report.NotesSharingInformation;
import de.hdm.itprojekt.noteit.shared.report.ReportSimple;

public interface ReportServiceAsync {

	void init(AsyncCallback<Void> callback);

	void createReportNotesGeneralInformation(User u, AsyncCallback<NotesGeneralInformation> callback);

	void createReportNotesSharingInformation(User u, AsyncCallback<NotesSharingInformation> callback);
	
}
