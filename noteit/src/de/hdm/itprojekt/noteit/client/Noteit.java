package de.hdm.itprojekt.noteit.client;


import de.hdm.itprojekt.noteit.client.Impressum;
import de.hdm.itprojekt.noteit.client.LoginInfo;
import de.hdm.itprojekt.noteit.shared.bo.User;
import de.hdm.itprojekt.noteit.shared.FieldVerifier;
import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Anchor;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Noteit implements EntryPoint {
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final NotesAdministrationAsync notesAdministrationService = GWT
			.create(NotesAdministration.class);



			/**
			 *  Login-Widgets
			 */
	 private LoginInfo loginInfo = null;
	  private VerticalPanel loginPanel = new VerticalPanel();
	  private Label loginLabel = new Label(
	      "Please sign in to your Google Account to access the StockWatcher application.");
	  private Anchor signInLink = new Anchor("Sign In");	
		
	  
	  Logger logger = Logger.getLogger("NameOfYourLogger");
		
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
				
		/**
		 *  Login Status mit Login service �berpr�fen.
		 *  Client-side proxy erstellen.
		 */
		 // Check login status using login service.
	    LoginServiceAsync loginService = GWT.create(LoginService.class);
	    loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
	      public void onFailure(Throwable error) {
	    	  logger.log(Level.SEVERE, "ERROR Login"+error);
	      }

	      public void onSuccess(LoginInfo result) {
	    	  
	        loginInfo = result;
	        if(loginInfo.isLoggedIn()) {
	          //loadStockWatcher();
	        } else {
	        	logger.log(Level.SEVERE, "DONE Login"+result);
	          loadLogin();
	        }
	      }
	    });
	  


		
		
		HorizontalPanel headerPanel = new HorizontalPanel();
		HorizontalPanel welcomePanel = new HorizontalPanel();
		HorizontalPanel headlinePanel = new HorizontalPanel();
		HorizontalPanel logoutPanel = new HorizontalPanel();
//	HorizontalPanel navPanel = new HorizontalPanel();
//		HorizontalPanel navNotebookPanel = new HorizontalPanel();
//		HorizontalPanel navNotesPanel = new HorizontalPanel();
//	HorizontalPanel contentPanel = new HorizontalPanel();
//		HorizontalPanel contentNotebookPanel = new HorizontalPanel();
//		HorizontalPanel contentNotesPanel = new HorizontalPanel();
		VerticalPanel homepage = new Homepage();
//		VerticalPanel editNotes = new EditNotes();
	
	Label welcomeLabel = new Label("Wilkommen Chris");
	Label headlineLabel = new Label("NoteIt");
//	Label headlineNotebookLabel = new Label ("Notizbücher");
//	Label headlineNotesLabel = new Label ("Notizen");

	
	Button btnLogOut = new Button("Logout");
	Button impressumButton = new Button ("Impressum");
	Button zurueckButton = new Button ("Zurück");
	
	welcomeLabel.setStylePrimaryName("welcomeLabel");
	headlineLabel.setStylePrimaryName("headlineLabel");
//	headlineNotebookLabel.setStylePrimaryName("headlineNotebookLabel");
//	headlineNotesLabel.setStylePrimaryName("headlineNotesLabel");
	
	
	btnLogOut.setStylePrimaryName("logOutButton");
	
	headerPanel.setStylePrimaryName("headerPanel");
	welcomePanel.setStylePrimaryName("welcomePanel");
	logoutPanel.setStylePrimaryName("logoutPanel");
	headlinePanel.setStylePrimaryName("headlinePanel");
//	navNotebookPanel.setStylePrimaryName("navNotebookPanel");
//	navNotesPanel.setStylePrimaryName("navNotesPanel");
//	contentNotebookPanel.setStylePrimaryName("contentNotebookPanel");
//	contentNotesPanel.setStylePrimaryName("contentNotesPanel");
	
	welcomePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
	headlinePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	logoutPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
//	navNotebookPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
//	navNotesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
//	contentNotebookPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
//	contentNotesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

	
	headerPanel.setWidth("1000px");
//	navPanel.setWidth("1000px");
//	contentPanel.setWidth("1000px");
//	navNotebookPanel.setWidth("500px");
//	navNotesPanel.setWidth("500px");
//	contentNotebookPanel.setWidth("500px");
//	contentNotesPanel.setWidth("500px");
//	
//	contentNotebookPanel.setHeight("300px");
//	contentNotesPanel.setHeight("300px");
//
//	navNotebookPanel.add(headlineNotebookLabel);
//	navNotesPanel.add(headlineNotesLabel);

	
	welcomePanel.add(welcomeLabel);
	headlinePanel.add(headlineLabel);
	logoutPanel.add(btnLogOut);
	logoutPanel.add(impressumButton);
	logoutPanel.add(zurueckButton);
	
	
	headerPanel.add(welcomePanel);
	headerPanel.add(headlinePanel);
	headerPanel.add(logoutPanel);
//	navPanel.add(navNotebookPanel);
//	navPanel.add(navNotesPanel);
//	contentPanel.add(contentNotebookPanel);
//	contentPanel.add(contentNotesPanel);


	RootPanel.get("header").add(headerPanel);
//	RootPanel.get("nav").add(navPanel);
//	RootPanel.get("content").add(contentPanel);
	
	RootPanel.get("content").add(homepage);
//	RootPanel.get("content").add(editNotes);
	
	//ClickHandler für Impressum Button
	impressumButton.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
			VerticalPanel impressum = new Impressum();
			
			RootPanel.get("content").clear();
			RootPanel.get("content").add(impressum);
		}
	});
	
	zurueckButton.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event){
			VerticalPanel homepage = new Homepage();
			
			RootPanel.get("content").clear();
			RootPanel.get("content").add(homepage);
		}
	});
	
//  neu
//-------------------------------------------------------		
//  alt		

		
		
		
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();
		
		final ArrayList<String> mailArray = new ArrayList<String>(Arrays.asList("1@array.de", "2@array.de", "3@array.de"));
		final ArrayList<Integer> intArray = new ArrayList<Integer>(Arrays.asList(1,2,3));
		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});
		

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			
			
			public void onClick(ClickEvent event) {
				//sendNameToServer();
//				notesAdministrationService.createUser("mail@mail.de", "hans", "nachname", new AsyncCallback<User>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//						Logger logger = Logger.getLogger("NameOfYourLogger");
//						logger.log(Level.SEVERE, "ERROR CreateUser"+caught);
//						
//					}
//
//					@Override
//					public void onSuccess(User result) {
//						// TODO Auto-generated method stub
//						Logger logger = Logger.getLogger("NameOfYourLogger");
//						logger.log(Level.SEVERE, "on success" + result.getFirstName());
//					}
//				});
//				Logger logger = Logger.getLogger("NameOfYourLogger");
//				logger.log(Level.SEVERE, "Start Update notebook methode");
//				notesAdministrationService.updateNotebook("update Notebook", 1, 13, mailArray, intArray, new AsyncCallback<Void>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//						Logger logger = Logger.getLogger("NameOfYourLogger");
//						logger.log(Level.SEVERE, "ERROR CreateUser"+caught);
//						
//					}
//
//					@Override
//					public void onSuccess(Void result) {
//						// TODO Auto-generated method stub
//						Logger logger = Logger.getLogger("NameOfYourLogger");
//						logger.log(Level.SEVERE, "on success Update Notebook");
//					}
//				});
				
//				notesAdministrationService.deleteNotebook(2,6, new AsyncCallback<Void>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//						
//					}
//
//					@Override
//					public void onSuccess(Void result) {
//						// TODO Auto-generated method stub
//						
//					}
//				});
				
//				notesAdministrationService.getAllNotebooksByUserID(1, new AsyncCallback<ArrayList<Notebook>>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//						
//					}
//
//					public void onSuccess(ArrayList<Notebook> result) {
//						// TODO Auto-generated method stub
//						Logger logger = Logger.getLogger("NameOfYourLogger");
//						logger.log(Level.SEVERE, "on success Update Notebook" + result.get(0).getTitle());
//					
//					}
//				});
				
//				notesAdministrationService.getAllNotesByNotebookID(1, new AsyncCallback<ArrayList<Note>>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//						
//					}
//
//					@Override
//					public void onSuccess(ArrayList<Note> result) {
//						// TODO Auto-generated method stub
//						Logger logger = Logger.getLogger("NameOfYourLogger");
//						logger.log(Level.SEVERE, "getAllNotesByNotebookID " + result.get(0).getText());
//					}
//				});
				
//				notesAdministrationService.findNotebooksByKeyword(1, "notebook", new AsyncCallback<ArrayList<Notebook>>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//						
//					}
//
//					@Override
//					public void onSuccess(ArrayList<Notebook> result) {
//						// TODO Auto-generated method stub
//						
//						Logger logger = Logger.getLogger("NameOfYourLogger");
//						logger.log(Level.SEVERE, "on success Update Notebook " + result.get(0).getTitle() + " 2 " + result.get(1).getTitle());
//						
//					}
//				});
				
				notesAdministrationService.findNoteByKeyword(1, "title", 1, new AsyncCallback<ArrayList<Note>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(ArrayList<Note> result) {
						Logger logger = Logger.getLogger("NameOfYourLogger");
						logger.log(Level.SEVERE, "on success Note by Keyword " + result.size());
						
					}
				});
				
				
			
				
			}
			

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
//				greetingService.greetServer(textToServer,
//						new AsyncCallback<String>() {
//							public void onFailure(Throwable caught) {
//								// Show the RPC error message to the user
//								dialogBox
//										.setText("Remote Procedure Call - Failure");
//								serverResponseLabel
//										.addStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(SERVER_ERROR);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//
//							public void onSuccess(String result) {
//								dialogBox.setText("Remote Procedure Call");
//								serverResponseLabel
//										.removeStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(result);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}

private void loadLogin() {
    // Assemble login panel.
    signInLink.setHref(loginInfo.getLoginUrl());
    loginPanel.add(loginLabel);
    loginPanel.add(signInLink);
    RootPanel.get("stockList").add(loginPanel);
  }}
