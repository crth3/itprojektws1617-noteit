package de.hdm.itprojekt.noteit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import de.hdm.itprojekt.noteit.shared.ReportAdmin;
import de.hdm.itprojekt.noteit.shared.ReportAdminAsync;

public class ReportGen implements EntryPoint {
	
	private final ReportAdminAsync report = GWT.create(ReportAdmin.class);

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		
	}

}
