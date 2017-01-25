package de.hdm.itprojekt.noteit.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.User;

/**
 * Diese Klasse verwaltet sämtliche View-Zugriffe und instanziiert nach erfolgreichem Login sämtliche Views
 * @author Tobias Dahms
 *
 */
public class Homepage extends VerticalPanel {

	private final static NotesAdministrationAsync notesAdmin = ClientsideSettings.getAdministrationService();

	// --------- Horizontal Panel -----------//
	HorizontalPanel headlinePanel = new HorizontalPanel();
	HorizontalPanel navPanel = new HorizontalPanel();
	HorizontalPanel navLeftPanel = new HorizontalPanel();
	HorizontalPanel navLeft2Panel = new HorizontalPanel();
	HorizontalPanel navLeft3Panel = new HorizontalPanel();
	HorizontalPanel navRightPanel = new HorizontalPanel();
	HorizontalPanel footerPanel = new HorizontalPanel();
	static HorizontalPanel contentPanel = new HorizontalPanel();
	final static VerticalPanel showNote = new ShowNote();
	VerticalPanel impressum = new Impressum();

	static VerticalPanel welcome = new Welcome();
	static VerticalPanel editNotebook = new EditNotebook();
	static Settings settings = new Settings();

	final HorizontalPanel contentNotebookPanel = new HorizontalPanel();
	final static HorizontalPanel contentNotesPanel = new HorizontalPanel();

	// --------- Label -----------//
	Label lbheadlineNotebookLabel = new Label("Notizbücher");
	Label lbheadlineNotesLabel = new Label("Notizen");
	Label lbheadlineNoteit = new Label("Noteit");
	Label lblRefreshNotebooks = new Label("Notizbücher aktualiseren: ");
	Label lblSearchTextIN = new Label(" in ");
	Label lbSortNotes = new Label("Notizen sortieren nach:");
	Label copyright = new Label("Copyright © 2017 Noteit. All rights reserved.");
	Button lblImpressum = new Button("Impressum");
	Button btnReportGen = new Button("Report Generator");
	PushButton btnRefresh = new PushButton(new Image("Images/Refresh.png"));
	// --------- Button -----------//
	// Button btnAddNewNotebookOrNoteButton = new Button("<img
	// src='Images/plus.png'/ width=\"15\" height=\"15\">");

	// --------- Text Box -----------//
	final TextBox tbSearch = new TextBox();

	// --------- Noteit Class -----------//
	static User currentUser = new User();
	final Notebooks notebooks = new Notebooks();
	final NotebookCellList notebookCellList = new NotebookCellList();
	static Notebook selectedNotebook = new Notebook();
	static Note selectedNote = new Note();

	// --------- Cell List -----------//
	final static CellList<Notebook> clNotebook = new NotebookCellList().createNotebookCellList();
	final static CellList<Note> clNote = new NoteCellList().createNoteCellList();

	public Homepage(User cU) {
		currentUser = cU;
	}

	@SuppressWarnings("deprecation")
	public void onLoad() {

		String url = Noteit.getValue_URL();

		((EditNotebook) editNotebook).run();
		((ShowNote) showNote).run();
		((Settings) settings).run();
		((Impressum) impressum).run();
		((Welcome) welcome).run();

		// CellBrowser
		TreeViewModel model = new NoteitCellBrowser();
		CellBrowser cellBrowser = new CellBrowser(model, null);

		cellBrowser.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		cellBrowser.setHeight("500px");
		cellBrowser.setWidth("430px");

		contentPanel.add(cellBrowser);
		contentPanel.add(editNotebook);

		if (Noteit.isNew() == true) {
			settingsView();
		}

		if (currentUser.getId() != 0 && Noteit.isNew() == false) {
			WelcomeView();
		}

		final ListBox listBox1 = new ListBox() {
			@Override
			public void setSelectedIndex(int index) {
				super.setSelectedIndex(index);
			}
		};
		listBox1.addItem("Notizbuch");
		listBox1.addItem("Notiz");

		final ListBox lbSort = new ListBox() {
			@Override
			public void setSelectedIndex(int index) {
				super.setSelectedIndex(index);
			}
		};
		lbSort.addItem("Erstelldatum: Absteigend");
		lbSort.addItem("Erstelldatum: Aufsteigend");
		lbSort.addItem("Änderungsdatum: Absteigend");
		lbSort.addItem("Änderungsdatum: Aufsteigend");
		lbSort.addItem("Fälligkeitsdatum: Absteigend");
		lbSort.addItem("Fälligkeitsdatum: Aufsteigend");

		Command settingDialog = new Command() {
			public void execute() {

				contentPanel.remove(1);
				contentPanel.add(settings);
			}
		};

		Command logout = new Command() {
			public void execute() {
				Noteit.loginInfo.getLogoutUrl();
				Window.open(Noteit.loginInfo.getLogoutUrl(), "_self", "");
				Noteit.loadLogin();
			}
		};

		Command showHTML = new Command() {
			public void execute() {
				showHTML();
			}
		};

		btnRefresh.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// updateNotebookCellList(currentUser.getId());
				NoteitCellBrowser.updateNotebooks();
			}
		});

		MenuBar settings = new MenuBar(true);
		settings.addItem("Profil", settingDialog);
		settings.addItem("Entwickler", showHTML);
		settings.addItem("Abmelden", logout);
		MenuBar menu = new MenuBar();
		final String image = "<img src='Images/user.png' height='20px' width='20px'/>";
		SafeHtml addActivityImagePath = new SafeHtml() {
		
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return image;
			}
		};

		menu.addItem(addActivityImagePath, settings);

		lbheadlineNotebookLabel.setStylePrimaryName("headlineNotebookLabel");
		lbheadlineNotesLabel.setStylePrimaryName("headlineNotesLabel");
		lbheadlineNoteit.setStyleName("lbheadlineNoteit");
		copyright.setStyleName("lblCopyright");
		/**
		 *  Style Names
		 */
		headlinePanel.setStyleName("headlinePanel");
		navLeftPanel.setStylePrimaryName("navLeftPanel");
		navLeft2Panel.setStylePrimaryName("navLeftPanel");
		navLeft3Panel.setStylePrimaryName("navLeftPanel");
		contentNotebookPanel.setStylePrimaryName("contentNotebookPanel");
		contentNotesPanel.setStylePrimaryName("contentNotesPanel");
		navPanel.setStylePrimaryName("navPanel");
		navRightPanel.setStyleName("menu");
		contentPanel.setStylePrimaryName("contentPanel");
		tbSearch.setStyleName("textbox");
		footerPanel.setStylePrimaryName("footerPanel");
		lblImpressum.setStylePrimaryName("lblImpressum");
		btnReportGen.setStylePrimaryName("lblImpressum");

		/**
		 *  Alignment der Panels
		 */
		contentNotebookPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		contentNotesPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		navPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		navPanel.setWidth("100%");
		contentPanel.setWidth("100%");
		headlinePanel.setWidth("100%");
		headlinePanel.add(lbheadlineNoteit);
		navLeftPanel.add(tbSearch);
		navLeftPanel.add(lblSearchTextIN);
		navLeftPanel.add(listBox1);
		navLeft2Panel.add(lbSortNotes);


		navLeft2Panel.add(lbSort);
		navLeft3Panel.add(lblRefreshNotebooks);
		navLeft3Panel.add(btnRefresh);

		footerPanel.add(lblImpressum);
		footerPanel.add(btnReportGen);
		footerPanel.add(copyright);

		navRightPanel.add(menu);

		navPanel.add(navLeftPanel);
		navPanel.add(navLeft2Panel);
		navPanel.add(navLeft3Panel);
		navPanel.add(navRightPanel);

		/**
		 * create the TextBox for Notebook Search, and include it to the Panel
		 */

		tbSearch.getElement().setPropertyString("placeholder", "Suchen...");
		tbSearch.setStylePrimaryName("tbSearchNotebook");

		/**
		 * Add all notebooks at start to the panel
		 */
		notesAdmin.getAllNotebooksByUserID(currentUser.getId(), new AsyncCallback<ArrayList<Notebook>>() {

			@Override
			public void onSuccess(ArrayList<Notebook> result) {
				System.out.println("result" + result);
				clNotebook.setRowData(result);
				contentNotebookPanel.add(clNotebook);
				notesAdmin.getAllNotesByNotebookID(result.get(0).getId(), currentUser.getId(),
						new AsyncCallback<ArrayList<Note>>() {

							@Override
							public void onSuccess(ArrayList<Note> result) {

								clNote.setRowData(result);
								contentNotesPanel.add(clNote);

							}

							@Override
							public void onFailure(Throwable caught) {

							}
						});
			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Error" + caught);
			}
		});
		tbSearch.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if (listBox1.getSelectedItemText() == "Notiz") {
						/**
						 * Search Note Function.
						 */
						NoteitCellBrowser.searchNoteByKeyword(currentUser.getId(), tbSearch.getValue());

					} else if (listBox1.getSelectedItemText() == "Notizbuch") {
						/**
						 * Search Notebook Function.
						 */
						NoteitCellBrowser.searchNotebookByKeyword(currentUser.getId(), tbSearch.getValue());

					}
				}

			}
		});

		lbSort.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {

				if (lbSort.getSelectedItemText() == "Fälligkeitsdatum: Absteigend") {
					sortNotesMaturityDesc();
				}

			}
		});

		lbSort.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {

				if (lbSort.getSelectedItemText() == "Fälligkeitsdatum: Aufsteigend") {
					sortNotesMaturityAsc();
				}

			}
		});

		lbSort.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {

				if (lbSort.getSelectedItemText() == "Änderungsdatum: Absteigend") {
					sortNotesModificationDateDesc();
				}

			}
		});

		lbSort.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {

				if (lbSort.getSelectedItemText() == "Änderungsdatum: Aufsteigend") {
					sortNotesModificationDateAsc();
				}

			}
		});

		lbSort.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {

				if (lbSort.getSelectedItemText() == "Erstelldatum: Absteigend") {
					sortNotesCreationDesc();
				}

			}
		});

		lbSort.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {

				if (lbSort.getSelectedItemText() == "Erstelldatum: Aufsteigend") {
					sortNotesCreationDateAsc();
				}

			}
		});
		lblImpressum.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				contentPanel.remove(1);
				// EditNotebook editNotebookView = new EditNotebook();
				contentPanel.add(impressum);

			}
		});

		btnReportGen.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.open("http://1-dot-noteit-id.appspot.com/NoteitReport.html", "_blank", "");

			}
		});

		this.add(headlinePanel);
		this.add(navPanel);
		this.add(contentPanel);
		this.add(footerPanel);

		if (url != null) {
			UrlView dialogBox = new UrlView(currentUser);
			dialogBox.show();
		}

		if (currentUser.getFirstName() == "null" || currentUser.getLastName() == "null"
				|| currentUser.getFirstName() == "" || currentUser.getLastName() == "") {
			settingsView();
		}
		;

	}

	/**
	 * set all notes from selected notebook
	 * 
	 * @param notebook Notizbuch das ausgwählt ist
	 */
	public static void setNotesWhenNotebookSelected(Notebook notebook) {
		selectedNotebook = notebook;

		notesAdmin.getAllNotesByNotebookID(notebook.getId(), getCurrentUser().getId(),
				new AsyncCallback<ArrayList<Note>>() {

					@Override
					public void onSuccess(ArrayList<Note> result) {
						System.out.println("result" + result);

						clNote.setRowData(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error" + caught);
					}
				});
	};

	public static void setSelectedNote(Note note) {
		selectedNote = note;

	}

	public void searchNotebookByKeyword(int userID, String keyword) {
		notesAdmin.findNotebooksByKeyword(userID, keyword, new AsyncCallback<ArrayList<Notebook>>() {

			@Override
			public void onSuccess(ArrayList<Notebook> result) {
				clNotebook.setRowData(result);

			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Error find notebook " + caught);

			}
		});
	}

	public void searchNoteByKeyword(int userID, String keyword, int notebookID) {
		notesAdmin.findNoteByKeyword(userID, keyword, notebookID, new AsyncCallback<ArrayList<Note>>() {

			@Override
			public void onSuccess(ArrayList<Note> result) {
				clNote.setRowData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	public static void updateNotebookCellList(int userID) {
		notesAdmin.getAllNotebooksByUserID(userID, new AsyncCallback<ArrayList<Notebook>>() {

			@Override
			public void onSuccess(ArrayList<Notebook> result) {
				clNotebook.setRowData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	public static void updateNotesCellList(int notebookId) {

		notesAdmin.getAllNotesByNotebookID(notebookId, getCurrentUser().getId(), new AsyncCallback<ArrayList<Note>>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(ArrayList<Note> result) {
				clNote.setRowCount(0);
				clNote.setRowData(result);

			}
		});
	}

	public Notebook getSelectedNotebook() {
		return selectedNotebook;
	}

	public static void setSelectedNotebook(Notebook notebook) {
		selectedNotebook = notebook;
	}

	public static User getCurrentUser() {

		return currentUser;
	}

	public static void editNotebookView() {

		if (contentPanel.getWidgetCount() == 2) {
			contentPanel.remove(1);
		}
		// EditNotebook editNotebookView = new EditNotebook();
		contentPanel.add(editNotebook);

	}

	public static void settingsView() {
		if (contentPanel.getWidgetCount() == 2) {
			contentPanel.remove(1);
		}
		contentPanel.add(settings);
	}

	public static void showNoteView() {
		if (contentPanel.getWidgetCount() == 2) {
			contentPanel.remove(1);
		}
		contentPanel.add(showNote);

	}

	public static void WelcomeView() {
		contentPanel.remove(1);
		contentPanel.add(welcome);

	}

	public static void showHTML() {
		Window.alert("Fügen Sie den nachfolgenden HTML Code in den <Body> Bereich Ihrer gewünschten Website ein\n\n  "
				+ "<form action=\"input_button.htm\">\n<p>\n<input type=\"button\" name=\"Verweis\" value=\"NoteIt\"\n onClick=\"self.location.href='http://1-dot-noteit-id.appspot.com?url=' + self.location\">\n</p>\n</form>");
	}

	/**
	 *  absteigend sortieren nach Fälligkeitsdatum
	 */
	public void sortNotesMaturityDesc() {
		NoteitCellBrowser.sortNotesMaturityDesc(selectedNotebook.getId());
	}

	/**
	 *  aufsteigend sortieren nach Fälligkeitsdatum
	 */
	public void sortNotesMaturityAsc() {
		NoteitCellBrowser.sortNotesByMaturityAsc(selectedNotebook.getId());

	}

	/**
	 *  absteigend sortieren nach Erstelldatum
	 */
	public void sortNotesCreationDesc() {
		NoteitCellBrowser.sortNotesCreationDateDesc(selectedNotebook.getId());

	}

	/**
	 *  aufsteigend sortieren nach Erstelldatum
	 */
	public void sortNotesCreationDateAsc() {
		NoteitCellBrowser.sortNotesCreationDateAsc(selectedNotebook.getId());

	}

	/**
	 *  absteigend sortieren nach Änderungsdatum
	 */
	public void sortNotesModificationDateDesc() {
		NoteitCellBrowser.sortNotesModificationDateDesc(selectedNotebook.getId());

	}

	/**
	 *  aufsteigend sortieren nach Änderungsdatum
	 */
	public void sortNotesModificationDateAsc() {
		NoteitCellBrowser.sortNotesModificationDateAsc(selectedNotebook.getId());

	}
/**
 * entfernt das rechte Widget (Settings, Note oder Notebook View) aus dem contentPanel
 */
	public static void hideView() {
		contentPanel.remove(1);
	}

}
