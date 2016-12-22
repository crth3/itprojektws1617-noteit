package de.hdm.itprojekt.noteit.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojekt.noteit.client.NotesGeneralInformationReport;
import de.hdm.itprojekt.noteit.client.NotesSharingInformationReport;
import de.hdm.itprojekt.noteit.shared.report.NotesGeneralInformation;
import de.hdm.itprojekt.noteit.shared.report.NotesSharingInformation;

public interface ReportServiceAsync {

	void init(AsyncCallback<Void> callback);

	void createReportNotesGeneralInformation(AsyncCallback<NotesGeneralInformation> callback);

	void createReportNotesSharingInformation(AsyncCallback<NotesSharingInformation> callback);
	
}
