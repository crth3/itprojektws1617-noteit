package de.hdm.itprojekt.noteit.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojekt.noteit.shared.report.*;

/**
 * 
 * @author maikzimmermann
 *
 */
@RemoteServiceRelativePath("report")
public interface ReportAdmin extends RemoteService{
	
	public void init();
	
	public ReportSimpleAllNotesWithGeneralInformations createReportSimpleAllNotesWithGeneralInformations() throws IllegalArgumentException;
	public ReportSimpleAllNotesWithGeneralSharingInformations createReportSimpleAllNotesWithGeneralSharingInformations() throws IllegalArgumentException;
}
