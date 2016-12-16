package de.hdm.itprojekt.noteit.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.ReportServiceAsync;

/**
 * Class for relevant client-side settings and services.
 * 
 * @author Thies
 * 
 */

public class ClientsideSettings {
	
	private static NotesAdministrationAsync notesAdministration = null;
	private static LoginServiceAsync loginService = null;
	private static ReportServiceAsync reportService = null;
	private static final Logger log = Logger
			.getLogger("Logger");



	/**
	 * Private constructor to prevent initialization.
	 */
	private ClientsideSettings() {
	}


	/**
	 * Creates and returns the application wide LoginService. Ensures that only
	 * a single instance exists.
	 * 
	 * @return The LoginService
	 */
	public static LoginServiceAsync getLoginService() {
		if (loginService == null) {
			loginService = GWT.create(LoginService.class);
		}

		return loginService;
	}

	/**
	 * Creates and returns the application wide AdministrationService. Ensures
	 * that only a single instance exists.
	 * 
	 * @return AdministrationService
	 */
	public static NotesAdministrationAsync getAdministrationService() {
		if (notesAdministration == null) {
			notesAdministration = GWT.create(NotesAdministration.class);
		}

		return notesAdministration;
	}

	/**
	 * Creates and returns ReprotService. Ensures that only a single instance
	 * exists
	 * 
	 * @return ReportService
	 */
	public static ReportServiceAsync getReportService() {
		if (reportService == null) {
			reportService = GWT.create(ReportServiceAsync.class);
		}

		reportService.init(new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub

			}

		});

		return reportService;
	}

	/**
	 * Get the application wide logger. The log is created on the client-side.
	 * 
	 * @return The logger instance for the server-side
	 */
	public static Logger getLogger() {
		return log;
	}
}