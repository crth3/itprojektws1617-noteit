package de.hdm.itprojekt.noteit.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author maikzimmermann
 *
 */
@RemoteServiceRelativePath("report")
public interface ReportAdmin extends RemoteService{
	
	public void init();

}
