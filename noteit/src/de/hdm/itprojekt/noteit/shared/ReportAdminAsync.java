package de.hdm.itprojekt.noteit.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojekt.noteit.shared.report.ReportSimple;
import de.hdm.itprojekt.noteit.shared.report.NotesSharingInformation;

public interface ReportAdminAsync {

	void init(AsyncCallback<Void> callback);

	void createReportNotesGeneralInformation(AsyncCallback<ReportSimple> callback);


	void createReportNotesSharingInformation(
			AsyncCallback<NotesSharingInformation> callback);


}
