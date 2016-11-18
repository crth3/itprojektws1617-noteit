package de.hdm.itprojekt.noteit.client;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Homepage extends VerticalPanel{
	public void onLoad() {
		
		HorizontalPanel navPanel = new HorizontalPanel();
		HorizontalPanel navNotebookPanel = new HorizontalPanel();
		HorizontalPanel navNotesPanel = new HorizontalPanel();
		HorizontalPanel contentPanel = new HorizontalPanel();
		HorizontalPanel contentNotebookPanel = new HorizontalPanel();
		HorizontalPanel contentNotesPanel = new HorizontalPanel();
		
		
		Label headlineNotebookLabel = new Label ("Notizbücher");
		Label headlineNotesLabel = new Label ("Notizen");
		
		
		headlineNotebookLabel.setStylePrimaryName("headlineNotebookLabel");
		headlineNotesLabel.setStylePrimaryName("headlineNotesLabel");
		
		
		navNotebookPanel.setStylePrimaryName("navNotebookPanel");
		navNotesPanel.setStylePrimaryName("navNotesPanel");
		contentNotebookPanel.setStylePrimaryName("contentNotebookPanel");
		contentNotesPanel.setStylePrimaryName("contentNotesPanel");
		
		
		navNotebookPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		navNotesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		contentNotebookPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		contentNotesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		navPanel.setWidth("1000px");
		contentPanel.setWidth("1000px");
		navNotebookPanel.setWidth("500px");
		navNotesPanel.setWidth("500px");
		contentNotebookPanel.setWidth("500px");
		contentNotesPanel.setWidth("500px");
		
		contentNotebookPanel.setHeight("300px");
		contentNotesPanel.setHeight("300px");

		navNotebookPanel.add(headlineNotebookLabel);
		navNotesPanel.add(headlineNotesLabel);
		
		
		navPanel.add(navNotebookPanel);
		navPanel.add(navNotesPanel);
		contentPanel.add(contentNotebookPanel);
		contentPanel.add(contentNotesPanel);
		
		this.add(navPanel);
		this.add(contentPanel);
		
	}
}
