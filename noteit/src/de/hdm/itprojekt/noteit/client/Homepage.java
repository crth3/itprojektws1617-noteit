package de.hdm.itprojekt.noteit.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
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
		
		Button addNoteButton = new Button ("Add Note");
		Button addNotebookButton = new Button ("Add Notebook");
		
		
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
		navNotesPanel.add(addNoteButton);
		navNotesPanel.add(addNotebookButton);

		
		navPanel.add(navNotebookPanel);
		navPanel.add(navNotesPanel);
		contentPanel.add(contentNotebookPanel);
		contentPanel.add(contentNotesPanel);
		
		
		addNoteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				VerticalPanel editNotes = new EditNotes();
		        
		        RootPanel.get("content").clear();
		        RootPanel.get("content").add(editNotes);
		      }
		    });

		addNotebookButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				VerticalPanel editNotebook = new EditNotebook();
		        
		        RootPanel.get("content").clear();
		        RootPanel.get("content").add(editNotebook);
		      }
		    });
		
		this.add(navPanel);
		this.add(contentPanel);
		
	}
}
