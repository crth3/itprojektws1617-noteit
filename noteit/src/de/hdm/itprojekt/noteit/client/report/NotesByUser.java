package de.hdm.itprojekt.noteit.client.report;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.client.ClientsideSettings;
import de.hdm.itprojekt.noteit.shared.ReportAdminAsync;
import de.hdm.itprojekt.noteit.shared.report.ReportSimple;
import de.hdm.itprojekt.noteit.shared.report.HTMLReportWriter;

public class NotesByUser extends VerticalPanel{
	
private ReportAdminAsync rpAdmin = null;
	
	private  Label lblNotesGeneralInformation;

	public NotesByUser() {
//		
//		rpAdmin = ClientsideSettings.getReportService();
//		
//		lblNotesGeneralInformation = new Label("All Notes with General Information");
//		add(lblNotesGeneralInformation);
//		rpAdmin.createReportNotesGeneralInformation(new AsyncCallback<ReportSimple>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onSuccess(ReportSimple result) {
//			
//				HTMLReportWriter hTMLReportWriter = new HTMLReportWriter();
//				final ReportSimple report = result;
//					hTMLReportWriter.process(report);
//				add(new HTML(hTMLReportWriter.getReportText()));
//			}
//		});;
//	
//			
	}

}
