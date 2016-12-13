package de.hdm.itprojekt.noteit.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojekt.noteit.shared.report.NotesByKeyword;
import de.hdm.itprojekt.noteit.shared.report.NotesByUser;

public interface ReportAdminAsync {

	void init(AsyncCallback<Void> callback);
	
	void createReportNotesByUser(AsyncCallback<NotesByUser> asyncCallback);

	void createReportNotesByKeyword(AsyncCallback<NotesByKeyword> asyncCallback);


}
