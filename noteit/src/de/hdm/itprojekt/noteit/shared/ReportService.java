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
public interface ReportService extends RemoteService{
	
	public void init();
	
	public NotesByUser createReportNotesByUser() throws IllegalArgumentException;
	public NotesByKeyword createReportNotesByKeyword() throws IllegalArgumentException;
}
