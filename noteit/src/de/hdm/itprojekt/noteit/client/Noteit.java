package de.hdm.itprojekt.noteit.client;

import de.hdm.itprojekt.noteit.client.Impressum;
import de.hdm.itprojekt.noteit.client.LoginInfo;
import de.hdm.itprojekt.noteit.shared.FieldVerifier;
import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
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
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final NotesAdministrationAsync notesAdministrationService = GWT.create(NotesAdministration.class);

	/**
	 * Login-Widgets
	 */
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the StockWatcher application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");

	Logger logger = Logger.getLogger("NameOfYourLogger");

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		/**
		 * create new Panels
		 */
		final VerticalPanel vpBasisPanel = new VerticalPanel();
		HorizontalPanel headerPanel = new HorizontalPanel();
		HorizontalPanel welcomePanel = new HorizontalPanel();
		HorizontalPanel headlinePanel = new HorizontalPanel();
		HorizontalPanel logoutPanel = new HorizontalPanel();
		// HorizontalPanel navPanel = new HorizontalPanel();
		// HorizontalPanel navNotebookPanel = new HorizontalPanel();
		// HorizontalPanel navNotesPanel = new HorizontalPanel();
		// HorizontalPanel contentPanel = new HorizontalPanel();
		// HorizontalPanel contentNotebookPanel = new HorizontalPanel();
		// HorizontalPanel contentNotesPanel = new HorizontalPanel();
		VerticalPanel homepage = new Homepage();
		// VerticalPanel editNotes = new EditNotes();

		/**
		 * Create new Labels
		 */
		Label welcomeLabel = new Label("Wilkommen Chris");
		Label headlineLabel = new Label("NoteIt");
		// Label headlineNotebookLabel = new Label ("Notizbücher");
		// Label headlineNotesLabel = new Label ("Notizen");

		/**
		 * Create new Buttons
		 */
		Button btnLogOut = new Button("Logout");
		Button impressumButton = new Button("Impressum");
		Button zurueckButton = new Button("Zurück");

		/**
		 * Set the Style
		 */
		btnLogOut.setStylePrimaryName("logOutButton");
		welcomeLabel.setStylePrimaryName("welcomeLabel");
		headlineLabel.setStylePrimaryName("headlineLabel");
		// headlineNotebookLabel.setStylePrimaryName("headlineNotebookLabel");
		// headlineNotesLabel.setStylePrimaryName("headlineNotesLabel");
		headerPanel.setStylePrimaryName("headerPanel");
		welcomePanel.setStylePrimaryName("welcomePanel");
		logoutPanel.setStylePrimaryName("logoutPanel");
		headlinePanel.setStylePrimaryName("headlinePanel");
		// navNotebookPanel.setStylePrimaryName("navNotebookPanel");
		// navNotesPanel.setStylePrimaryName("navNotesPanel");
		// contentNotebookPanel.setStylePrimaryName("contentNotebookPanel");
		// contentNotesPanel.setStylePrimaryName("contentNotesPanel");
		welcomePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		headlinePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		logoutPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		// navNotebookPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		// navNotesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		// contentNotebookPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		// contentNotesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		/**
		 * set the width
		 */
		headerPanel.setWidth("1000px");
		// navPanel.setWidth("1000px");
		// contentPanel.setWidth("1000px");
		// navNotebookPanel.setWidth("500px");
		// navNotesPanel.setWidth("500px");
		// contentNotebookPanel.setWidth("500px");
		// contentNotesPanel.setWidth("500px");
		// contentNotebookPanel.setHeight("300px");
		// contentNotesPanel.setHeight("300px");
		// navNotebookPanel.add(headlineNotebookLabel);
		// navNotesPanel.add(headlineNotesLabel);

		/**
		 * add the widgets
		 */
		welcomePanel.add(welcomeLabel);
		headlinePanel.add(headlineLabel);
		logoutPanel.add(btnLogOut);
		logoutPanel.add(impressumButton);
		logoutPanel.add(zurueckButton);
		headerPanel.add(welcomePanel);
		headerPanel.add(headlinePanel);
		headerPanel.add(logoutPanel);
		vpBasisPanel.add(loginPanel);

		// navPanel.add(navNotebookPanel);
		// navPanel.add(navNotesPanel);
		// contentPanel.add(contentNotebookPanel);
		// contentPanel.add(contentNotesPanel);
		// RootPanel.get("nav").add(navPanel);
		// RootPanel.get("content").add(contentPanel);
		// RootPanel.get("content").add(editNotes);

		/**
		 * Login Status mit Login service �berpr�fen. Client-side proxy
		 * erstellen.
		 */
		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
				logger.log(Level.SEVERE, "ERROR Login" + error);
			}

			public void onSuccess(LoginInfo result) {

				loginInfo = result;
				if (loginInfo.isLoggedIn()) {
					RootPanel.get("content").add(vpBasisPanel);
					RootPanel.get("content").add(homepage);
					RootPanel.get("header").add(headerPanel);
					// Hier muss auf die Hompage-Steie verwiesen werden

				} else {
					logger.log(Level.SEVERE, "DONE Login" + result);
					loadLogin();
				}
			}
		});

		// ClickHandler für Impressum Button
		impressumButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				VerticalPanel impressum = new Impressum();

				RootPanel.get("content").clear();
				RootPanel.get("content").add(impressum);
			}
		});

		// ClickHandler für Zurück Button
		zurueckButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				VerticalPanel homepage = new Homepage();

				RootPanel.get("content").clear();
				RootPanel.get("content").add(homepage);
			}
		});

		// ClickHandler für LogOut Button
		btnLogOut.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loginInfo.getLogoutUrl();
				Window.open(loginInfo.getLogoutUrl(), "_self", "");
				loadLogin();

			}
		});

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

		/**
		 * 
		 */
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

	private void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("content").add(loginPanel);
	}
}
