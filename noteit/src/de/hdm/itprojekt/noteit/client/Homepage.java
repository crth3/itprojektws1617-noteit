package de.hdm.itprojekt.noteit.client;

import java.rmi.NotBoundException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Homepage extends VerticalPanel{
	public void onLoad() {
		
		HorizontalPanel navPanel = new HorizontalPanel();
		HorizontalPanel navNotebookPanel = new HorizontalPanel();
		HorizontalPanel navNotesPanel = new HorizontalPanel();
		HorizontalPanel contentPanel = new HorizontalPanel();
		final HorizontalPanel contentNotebookPanel = new HorizontalPanel();
		HorizontalPanel contentNotesPanel = new HorizontalPanel();
		
		final Notebooks Notebooks = new Notebooks();
		Notebooks.getAllNotebooks(1);
		contentNotebookPanel.add(Notebooks.getAllNotebooks(1));
		
		
		Label headlineNotebookLabel = new Label ("Notizbücher");
		Label headlineNotesLabel = new Label ("Notizen");
		
		
		Button addNoteButton = new Button ("Add Note");
		Button addNotebookButton = new Button ("Add Notebook");
		Button searchNoteButton = new Button("Search Note");
		
		
		
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
		navNotebookPanel.add(addNotebookButton);
		navNotesPanel.add(searchNoteButton);
		
		
		
		/**
		 * create the TextBox, and included to the  Panel
		 */
		final TextBox tbSearchNotebook = new TextBox();
		navNotebookPanel.add(tbSearchNotebook);

		/**
		 * add to the Panels
		 */
		navPanel.add(navNotebookPanel);
		navPanel.add(navNotesPanel);
		contentPanel.add(contentNotebookPanel);
		contentPanel.add(contentNotesPanel);
		contentNotebookPanel.add(Notebooks);
		
		/**
		 * Create the DialoBox and Panel, this is the Popup for the addNotesButton 
		 */
		DialogBox notizBuchDialogBox = new DialogBox();
		notizBuchDialogBox.setGlassEnabled(true);
		notizBuchDialogBox.setAnimationEnabled(true);
		notizBuchDialogBox.setText("Notizbuch hinzufügen");
		
		VerticalPanel editNotebook = new EditNotebook();
		editNotebook.setSpacing(40);
		notizBuchDialogBox.setWidget(editNotebook);

		/**
		 * Create the Button and the ClickHandler
		 */
		addNotebookButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {		
//				VerticalPanel editNotebook = new EditNotebook();
		        
				notizBuchDialogBox.center();
				notizBuchDialogBox.show();
				
//		        RootPanel.get("content").clear();
//		        RootPanel.get("content").add(editNotebook);
		      }
		    });

		addNoteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				VerticalPanel editNotes = new EditNotes();
		        
		        RootPanel.get("content").clear();
		        RootPanel.get("content").add(editNotes);
		      }
		    });
		
		/**
		 * Create the TextBox with ChangeHandler for Search Notebook Function.
		 */
		tbSearchNotebook.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				
				contentNotebookPanel.add(Notebooks.getAllNotebooksByKeyword(1, event.getValue()));
			}
		});

		
		searchNoteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				VerticalPanel searchNotes = new SearchNotes();
		        
		        RootPanel.get("content").clear();
		        RootPanel.get("content").add(searchNotes);
		      }
		    });
				
		
		this.add(navPanel);
		this.add(contentPanel);
		
}
}

