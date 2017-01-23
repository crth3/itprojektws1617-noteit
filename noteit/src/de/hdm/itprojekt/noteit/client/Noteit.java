package de.hdm.itprojekt.noteit.client;

import de.hdm.itprojekt.noteit.client.LoginInfo;
import de.hdm.itprojekt.noteit.shared.LoginService;
import de.hdm.itprojekt.noteit.shared.LoginServiceAsync;
import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
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
	private final NotesAdministrationAsync notesAdministrationService = ClientsideSettings.getAdministrationService();

	/**
	 * Login-Widgets
	 */
	public static LoginInfo loginInfo = null;
	public final static String value_URL = Window.Location.getParameter("url");
	private static VerticalPanel loginPanel = new VerticalPanel();
	private static Label loginLabel = new Label("Melde dich mit deinem Google-Konto an um Noteit nutzen zu können.");
	private static Anchor signInLink = new Anchor("Sign In");
	// private Anchor signOutLink = new Anchor("Sign Out");

	public static User currentUser = new User();

	static Logger logger = Logger.getLogger("NameOfYourLogger");

	private Homepage HomepagePanel;

	/**
	 * create new Panels
	 */

	VerticalPanel vpBasisPanel = new VerticalPanel();
	final static HorizontalPanel welcomePanel = new HorizontalPanel();
	final HorizontalPanel headlinePanel = new HorizontalPanel();
	final HorizontalPanel content = new HorizontalPanel();
	final HorizontalPanel logoutPanel = new HorizontalPanel();
	final VerticalPanel showNote = new ShowNote();

	/**
	 * Create new Labels
	 */
	Label headlineLabel = new Label("NoteIt");

	/**
	 * Create new Buttons
	 */
	Button btnLogOut = new Button("Logout");
	Button zurueckButton = new Button("Zurück");

	static boolean isNew = false;
	private static Storage stockStore = null;

	/**
	 * This is the entry point method.
	 */

	public void onModuleLoad() {

		stockStore = Storage.getSessionStorageIfSupported();

		if (stockStore != null) {
			if (value_URL != null) {
				stockStore.setItem("url", value_URL);
				logger.log(Level.SEVERE, "URL in Storage geschrieben " + value_URL);
			}

		}

		/**
		 * Set the Style
		 */
		btnLogOut.setStylePrimaryName("logOutButton");
		headlineLabel.setStylePrimaryName("headlineLabel");
		logoutPanel.setStylePrimaryName("logoutPanel");
		headlinePanel.setStylePrimaryName("headlinePanel");

		welcomePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		logoutPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		/**
		 * add the widgets
		 */
		headlinePanel.add(headlineLabel);
		logoutPanel.add(btnLogOut);
		logoutPanel.add(zurueckButton);
		vpBasisPanel.add(loginPanel);

		logger.log(Level.SEVERE, "URL Inhalt" + value_URL);
		/**
		 * Login Status mit Login service überprüfen. Client-side proxy
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

				final String mail = loginInfo.getEmailAddress();

				if (loginInfo.isLoggedIn() == true) {

					logger.log(Level.SEVERE, "bei Google eingeloggt: " + loginInfo.getEmailAddress());

					notesAdministrationService.findUserByMail(mail, new AsyncCallback<User>() {

						@Override
						public void onSuccess(User result) {
							if (result != null) {
								logger.log(Level.SEVERE, "Nutzer gefunden: " + result);
								currentUser = result;
								HomepagePanel = new Homepage(result);
								RootPanel.get().add(HomepagePanel);

							} else if (mail != null) {

								logger.log(Level.SEVERE,
										"Neuen Nutzer anlegen-mail, vorname, nachname " + loginInfo.getEmailAddress()
												+ loginInfo.getFirstName() + loginInfo.getLastName());

								notesAdministrationService.createUser(mail, loginInfo.getFirstName(),
										loginInfo.getLastName(), new AsyncCallback<User>() {

											@Override
											public void onFailure(Throwable caught) {
												logger.log(Level.SEVERE, "RPC Fehler " + caught);

											}

											@Override
											public void onSuccess(User result) {
												currentUser = result;
												logger.log(Level.SEVERE, "Neuen Nutzer angelegt "
														+ currentUser.getMail() + " " + currentUser.getFirstName());
												currentUser = result;
												HomepagePanel = new Homepage(result);

												isNew = true;

												RootPanel.get().add(HomepagePanel);

											}
										});

							}

						}

						@Override
						public void onFailure(Throwable caught) {
							logger.log(Level.SEVERE, "Error Login: " + caught);

						}
					});

				} else {
					logger.log(Level.SEVERE, "LOAD Login");
					loadLogin();
				}

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

	}
/**
 * Diese Methode liest die URL aus dem Sessionstorage aus und gibt diese zur�ck
 * @return
 */
	public static String getValue_URL() {
		return stockStore.getItem(stockStore.key(0));

	}
/**
 * diese Methode l�dt die LoginView
 */
	public static void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("content").add(loginPanel);
	}
/**
 * Diese Methode gibt den aktuell angemeldeten Nutzer zur�ck
 * @return
 */
	public static User getCurrentUser() {
		logger.log(Level.SEVERE, "NUTZER WIRD GEHOLT" + currentUser.getMail());
		return currentUser;
	}

	public static boolean isNew() {
		return isNew;
	}
/**
 * Diese Methode l�scht den beschriebenen Sessionstorage
 */
	public static void deleteStorage() {
		stockStore.clear();
	}
}
