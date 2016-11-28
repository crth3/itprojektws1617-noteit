package de.hdm.itprojekt.noteit.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojekt.noteit.shared.report.ReportSimpleAllNotesWithGeneralInformations;
import de.hdm.itprojekt.noteit.shared.report.ReportSimpleAllNotesWithGeneralSharingInformations;

public interface ReportAdminAsync {

	void init(AsyncCallback<Void> callback);

	void createReportSimpleAllNotesWithGeneralInformations(
			AsyncCallback<ReportSimpleAllNotesWithGeneralInformations> callback);

	void createReportSimpleAllNotesWithGeneralSharingInformations(
			AsyncCallback<ReportSimpleAllNotesWithGeneralSharingInformations> callback);

}
