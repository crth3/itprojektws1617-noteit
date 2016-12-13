package de.hdm.itprojekt.noteit.client.report;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.client.ClientsideSettings;
import de.hdm.itprojekt.noteit.shared.ReportAdminAsync;
import de.hdm.itprojekt.noteit.shared.report.ReportSimple;
import de.hdm.itprojekt.noteit.shared.report.HTMLReportWriter;

public class NotesByKeyword extends VerticalPanel{
	
private ReportAdminAsync rpAdmin = null;
	
	private  Label lblNotesByKeyword;

	public NotesByKeyword() {
		
//		rpAdmin = ClientsideSettings.getReportService();
//		
//		lblNotesByKeyword = new Label("All notes by keyword");
//		add(lblNotesByKeyword);
//		rpAdmin.createReportNotesByKeyword(new AsyncCallback<NotesByKeyword>() {
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
	
			
	}

}
