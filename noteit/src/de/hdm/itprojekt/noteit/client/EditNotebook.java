package de.hdm.itprojekt.noteit.client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tools.ant.taskdefs.Delete;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.*;

public class EditNotebook extends VerticalPanel {

	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	static Notebook currentNotebook;
	static ArrayList<User> userList = new ArrayList<User>();
	private static Logger rootLogger = Logger.getLogger("");

	static HorizontalPanel hpHeader = new HorizontalPanel();
	static HorizontalPanel hpEditNotebook = new HorizontalPanel();
	static HorizontalPanel hpButtons = new HorizontalPanel();
	static HorizontalPanel hpAddPermission = new HorizontalPanel();
	HorizontalPanel hDialog = new HorizontalPanel();

	static VerticalPanel vpLeft = new VerticalPanel();
	static VerticalPanel vpRight = new VerticalPanel();
	static VerticalPanel vpTitle = new VerticalPanel();
	static VerticalPanel vpNotebookShare = new VerticalPanel();
	static VerticalPanel vpNotebookPermission = new VerticalPanel();
	static VerticalPanel vpBackButton = new VerticalPanel();
	VerticalPanel vDialog = new VerticalPanel();

	static Label lblHeaderTitel = new Label();
	static Label lblNotebookTitel = new Label("Titel");
	static Label lblNotebookPermission = new Label("Freigegeben an:");
	static Label lblNotebookShare = new Label("Notizbuch Teilen mit:");
	static Label lblNotebookShareRB = new Label("Berechtigung festlegen:");
	static Label lblNotebookDate = new Label();
	static Label lblPermissionInformationRead = new Label(
			"Deine Berechtigung für dieses Notizbuch beschränkt sich auf nur auf das Lesen.");
	static Label lblPermissionInformationWrite = new Label(
			"Deine Berechtigung für dieses Notizbuch beschränkt sich auf das Bearbeiten");
	static Label lblPermissionInformationDelete = new Label("Du hast volle Berechtigung für dieses Notizbuch");

	static TextBox tbNotebookTitel = new TextBox();
	static TextBox tbNotebookShareMail = new TextBox();

	static Button btnNotebookSave = new Button("Speichern");
	static Button btnUnsubcribe = new Button("Deabonnieren");
	static Button btnNotebookDelete = new Button("Löschen");
	static Button btnAddPermission = new Button("<img src='Images/check.png'/ width=\"10\" height=\"10\">");
	static Button btnDeletePermission = new Button("<img src='Images/cancle.png'/ width=\"10\" height=\"10\">");
	static Button btnNo = new Button("Nein");
	static Button btnYes = new Button("Ja");

	static CellList<User> clUser = new UserCellList().createUserCellList();

	static RadioButton rbRead = new RadioButton("permission", "lesen");
	static RadioButton rbWrite = new RadioButton("permission", "bearbeiten");
	static RadioButton rbDelete = new RadioButton("permission", "bearbeiten & löschen");

	// Timestamp maturity = new Timestamp();

	// Date maturity = new Date();

	// modificationdate

	protected void run() {
		rootLogger.log(Level.SEVERE, "EditNotebook KLASSE");

		hpEditNotebook.setWidth("600px");
		hpButtons.setWidth("300px");
		vpLeft.setWidth("300px");
		hpAddPermission.setWidth("300px");
		vpTitle.setWidth("300px");
		vpRight.setWidth("300px");
		vpNotebookPermission.setWidth("300px");

		hpAddPermission.setStyleName("vpAddPermissionNotebook");
		hpHeader.setStyleName("headerDetailView");
		lblHeaderTitel.setStyleName("lblHeaderTitel");
		hpEditNotebook.setStyleName("showDetailContent");
		lblNotebookPermission.setStyleName("lblPermissionText");
		lblNotebookDate.setStyleName("lblNoteDate");
		vpLeft.setStyleName("vpLeft");
		vpRight.setStyleName("vpRight");

		hpHeader.add(lblHeaderTitel);
		hpHeader.add(lblNotebookDate);

		rbRead.setValue(true);
		hpAddPermission.add(rbRead);
		hpAddPermission.add(rbWrite);
		hpAddPermission.add(rbDelete);
		/**
		 * Create the Panel, Label and TextBox
		 */

		vpTitle.add(lblNotebookTitel);
		vpTitle.add(tbNotebookTitel);

		hpAddPermission.add(lblNotebookShare);
		hpAddPermission.add(tbNotebookShareMail);
		hpAddPermission.add(btnAddPermission);
		hpAddPermission.add(btnDeletePermission);
		hpAddPermission.setSpacing(0);

		vpNotebookPermission.add(lblNotebookPermission);
		vpNotebookPermission.add(clUser);

		lblPermissionInformationWrite.setVisible(false);
		lblPermissionInformationDelete.setVisible(false);
		lblPermissionInformationRead.setVisible(false);

		vpLeft.add(lblNotebookTitel);
		vpLeft.add(tbNotebookTitel);
		vpLeft.add(hpButtons);
		vpLeft.add(lblPermissionInformationRead);
		vpLeft.add(lblPermissionInformationWrite);
		vpLeft.add(lblPermissionInformationDelete);

		vpRight.add(lblNotebookShare);
		vpRight.add(hpAddPermission);
		vpRight.add(lblNotebookShareRB);
		vpRight.add(rbRead);
		vpRight.add(rbWrite);
		vpRight.add(rbDelete);
		vpRight.add(vpNotebookPermission);

		vDialog.setSpacing(10);
		vDialog.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vDialog.add(hDialog);
		hDialog.add(btnYes);
		hDialog.add(btnNo);

		/**
		 * Create the Panel, Label and TextBox
		 */

		// hpNoteSubTitel.add(lblNotebookSubTitel);
		// hpNoteSubTitel.add(tbNotebookSubTitel);

		/**
		 * Create the Panel, Label and TextBox
		 */

		hpEditNotebook.add(vpLeft);
		hpEditNotebook.add(vpRight);
		// hpEditNotebook.add(vpNotebookPermission);
		// hpEditNotebook.add(hpButtons);

		this.add(hpHeader);
		this.add(hpEditNotebook);

		/**
		 * Erstellen oder bearbeiten von Freigaben RPC
		 */

		btnAddPermission.addClickHandler(new ClickHandler() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(ClickEvent event) {
				int permissionID;
				if (rbRead.isChecked()) {
					permissionID = 1;
				} else if (rbWrite.isChecked()) {
					permissionID = 2;
				} else {
					permissionID = 3;
				}
				if (tbNotebookShareMail.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
					if (Homepage.getCurrentUser().getMail() != tbNotebookShareMail.getText()) {
						notesAdmin.setUserNotebookPermission(tbNotebookShareMail.getText(), permissionID,
								currentNotebook.getId(), new AsyncCallback<Boolean>() {

									@Override
									public void onSuccess(Boolean result) {
										if (result == true) {
											tbNotebookShareMail.setText("");
											tbNotebookShareMail.getElement().setPropertyString("placeholder",
													"nutzer@noteit.de");
											rbRead.setValue(true);
											getAllPermittedUsersbyNotebookID(currentNotebook.getId());
										} else {
											Window.alert("Der Nutzer mit der E-Mail `" + tbNotebookShareMail.getText()
													+ "` wurde nicht gefunden");
										}

									}

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub

									}
								});
					} else {
						Window.alert("Sie können sich nicht selbst freigeben!");
					}
				} else {
					Window.alert("Bitte gebe eine E-Mail-Adresse an!");
					clUser.setRowData(userList);
				}

			}
		});

		btnDeletePermission.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (tbNotebookShareMail.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
					Window.alert("Abfrage ob die Freigabe wirklich gelöscht werden soll");
					notesAdmin.deleteUserNotebookPermission(tbNotebookShareMail.getText(),
							Homepage.getCurrentUser().getPermissionID(), currentNotebook.getId(),
							new AsyncCallback<Void>() {

								@Override
								public void onSuccess(Void result) {
									Window.alert("Nuter wurde gelöscht");
									getAllPermittedUsersbyNotebookID(currentNotebook.getId());
								}

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}
							});
				} else {
					Window.alert("Bitte wähle eine bestehende Freigabe aus die du löschen möchtest!");
				}

			}
		});

		btnNotebookSave.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				rootLogger.log(Level.SEVERE, "Button Event");

				if (tbNotebookTitel.getText().length() > 0) {
					rootLogger.log(Level.SEVERE, "Button Event11");
					if (currentNotebook.getId() == 0) {
						notesAdmin.createNotebook(tbNotebookTitel.getText(), Homepage.getCurrentUser(),
								new AsyncCallback<Notebook>() {

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onSuccess(Notebook result) {
										// TODO get all notebooks by user ID

										rootLogger.log(Level.SEVERE, "NB Speicher Button");
										NoteitCellBrowser.getNotebookList(result);

									}
								});

						tbNotebookTitel.setText("");
						tbNotebookTitel.getElement().setPropertyString("placeholder", "Dein Titel");
					} else {
						notesAdmin.updateNotebook(tbNotebookTitel.getText(), currentNotebook,
								Homepage.getCurrentUser().getId(), new AsyncCallback<Void>() {

									@Override
									public void onSuccess(Void result) {
										Timestamp ts = new Timestamp(System.currentTimeMillis());
										Date date = new Date(ts.getTime());
										DateTimeFormat sdfmt = DateTimeFormat.getFormat("dd.MM.yyyy");
										lblNotebookDate.setText("Zuletzt bearbeitet am: " + sdfmt.format(date));
										NoteitCellBrowser.updateNotebooks();
									}

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub

									}
								});
					}
				} else {
					Window.alert("Bitte vergebe einen Titel für dien Notizbuch");
				}
			}
		});

		btnUnsubcribe.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				final DialogBox dlbQuestion = new DialogBox();

				dlbQuestion.setAnimationEnabled(true);
				dlbQuestion.setText("Sind sie sicher, dass Sie das ausgewählte Notizbuch deabonnieren möchten?");
				dlbQuestion.setWidth("300px");
				dlbQuestion.setWidget(vDialog);
				dlbQuestion.setModal(true);
				dlbQuestion.setGlassEnabled(true);
				dlbQuestion.center();

				int width = Window.getClientWidth() / 2;
				int height = Window.getClientHeight() / 2;
				dlbQuestion.setPopupPosition(width, height);
				dlbQuestion.show();

				btnYes.addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent event) {

						// Methode zum löschen der Note aufrufen
						notesAdmin.deleteUserNotebookPermission(Homepage.getCurrentUser().getMail(),
								currentNotebook.getPermissionID(), currentNotebook.getId(), new AsyncCallback<Void>() {

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onSuccess(Void result) {
										// TODO Auto-generated method stub
										NoteitCellBrowser.updateNotebooks();
									}
								});
						// DialogBox ausblenden
						dlbQuestion.hide();

					}
				});

				btnNo.addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent event) {

						// DialogBox ausblenden
						dlbQuestion.hide();

					}
				});

			}
		});

		btnNotebookDelete.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				final DialogBox dlbQuestion = new DialogBox();

				dlbQuestion.setAnimationEnabled(true);
				dlbQuestion.setText("Sind sie sicher, dass Sie das ausgewählte Notizbuch löschen möchten?");
				dlbQuestion.setWidth("300px");
				dlbQuestion.setWidget(vDialog);
				dlbQuestion.setModal(true);
				dlbQuestion.setGlassEnabled(true);
				dlbQuestion.show();
				dlbQuestion.center();

				int width = Window.getClientWidth() / 2;
				int height = Window.getClientHeight() / 2;
				dlbQuestion.setPopupPosition(width, height);
				dlbQuestion.show();

				btnYes.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {

						notesAdmin.deleteNotebook(currentNotebook.getId(), Homepage.currentUser.getId(),
								new AsyncCallback<Void>() {

									@Override
									public void onSuccess(Void result) {
										// Notebook Liste aktualisieren
										NoteitCellBrowser.deleteNotebook();

										// DialogBox ausblenden
										dlbQuestion.hide();

									}

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub

									}
								});

					}

				});

				btnNo.addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent event) {

						// DialogBox ausblenden
						dlbQuestion.hide();

					}
				});

			}

		});

		tbNotebookShareMail.getElement().setPropertyString("placeholder", "nutzer@noteit.de");
		tbNotebookTitel.getElement().setPropertyString("placeholder", "Dein Titel");
		// btnAddPermission.addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		// setUserPermission(tbNotebookShareMail.getText());
		//
		// }
		// });

	}

	public static void setNotebook(Notebook notebook) {
		currentNotebook = notebook;
		if (currentNotebook.getPermissionID() > 0 && currentNotebook.getId() != 0) {
			hpButtons.add(btnNotebookSave);
			hpButtons.add(btnUnsubcribe);
			hpButtons.add(btnNotebookDelete);

		} else {
			hpButtons.clear();
			hpButtons.add(btnNotebookSave);
			hpButtons.add(btnNotebookDelete);
		}

		tbNotebookTitel.setText(notebook.getTitle());
		if (currentNotebook.getId() == 0) {
			vpNotebookShare.setVisible(false);
			vpNotebookPermission.setVisible(false);
			hpAddPermission.setVisible(false);
			rbDelete.setVisible(false);
			rbRead.setVisible(false);
			rbWrite.setVisible(false);
			lblNotebookPermission.setVisible(false);
			lblNotebookShare.setVisible(false);
			lblNotebookShareRB.setVisible(false);
			lblNotebookDate.setVisible(false);
			btnNotebookDelete.setVisible(false);
			btnNotebookSave.setVisible(true);
			lblHeaderTitel.setText("Neues Notizbuch");
			tbNotebookTitel.getElement().setPropertyString("placeholder", "Dein Titel");
		} else if (currentNotebook.getId() == -1) {
			vpNotebookShare.setVisible(false);
			vpNotebookPermission.setVisible(false);
			hpAddPermission.setVisible(false);
			rbDelete.setVisible(false);
			rbRead.setVisible(false);
			rbWrite.setVisible(false);
			lblNotebookPermission.setVisible(false);
			lblNotebookShare.setVisible(false);
			lblNotebookShareRB.setVisible(false);
			lblNotebookDate.setVisible(false);
			btnNotebookDelete.setVisible(false);
			btnNotebookSave.setVisible(false);
		} else {
			vpNotebookShare.setVisible(true);
			vpNotebookPermission.setVisible(true);
			hpAddPermission.setVisible(true);
			lblNotebookShareRB.setVisible(true);
			rbDelete.setVisible(true);
			rbRead.setVisible(true);
			rbWrite.setVisible(true);
			lblNotebookShare.setVisible(true);
			lblNotebookPermission.setVisible(true);
			lblNotebookPermission.setVisible(true);
			lblNotebookDate.setVisible(true);
			btnNotebookDelete.setVisible(true);
			btnNotebookSave.setVisible(true);
		}

		if (currentNotebook.getModificationDate() == null) {
			Timestamp ts = currentNotebook.getCreationDate();
			Date date = new Date(ts.getTime());
			DateTimeFormat sdfmt = DateTimeFormat.getFormat("dd.MM.yyyy");
			lblNotebookDate.setText("Erstellt am: " + sdfmt.format(date));
			lblHeaderTitel.setText(notebook.getTitle());

		} else {
			Timestamp ts = currentNotebook.getModificationDate();
			Date date = new Date(ts.getTime());
			DateTimeFormat sdfmt = DateTimeFormat.getFormat("dd.MM.yyyy");
			lblNotebookDate.setText("Zuletzt bearbeitet am: " + sdfmt.format(date));
			lblHeaderTitel.setText(notebook.getTitle());
		}

		if (currentNotebook.getPermissionID() == 1) {

			btnNotebookSave.setEnabled(false);
			btnNotebookDelete.setEnabled(false);
			btnAddPermission.setEnabled(false);
			btnDeletePermission.setEnabled(false);
			lblPermissionInformationWrite.setVisible(false);
			lblPermissionInformationDelete.setVisible(false);
			lblPermissionInformationRead.setVisible(true);

			btnAddPermission.setHTML("<img src='Images/check_grey.png'/ width=\"10\" height=\"10\">");
			btnDeletePermission.setHTML("<img src='Images/cancle_grey.png'/ width=\"10\" height=\"10\">");
		} else if (currentNotebook.getPermissionID() == 2) {
			btnNotebookSave.setEnabled(true);
			btnAddPermission.setEnabled(true);
			btnDeletePermission.setEnabled(true);
			btnNotebookDelete.setEnabled(false);
			lblPermissionInformationWrite.setVisible(true);
			lblPermissionInformationDelete.setVisible(false);
			lblPermissionInformationRead.setVisible(false);

			btnAddPermission.setHTML("<img src='Images/check.png'/ width=\"10\" height=\"10\">");
			btnDeletePermission.setHTML("<img src='Images/cancle.png'/ width=\"10\" height=\"10\">");
		} else {
			btnNotebookSave.setEnabled(true);
			btnNotebookDelete.setEnabled(true);
			btnAddPermission.setEnabled(true);
			btnDeletePermission.setEnabled(true);
			btnAddPermission.setHTML("<img src='Images/check.png'/ width=\"10\" height=\"10\">");
			btnDeletePermission.setHTML("<img src='Images/cancle.png'/ width=\"10\" height=\"10\">");
			if (currentNotebook.getPermissionID() == 3) {
				lblPermissionInformationWrite.setVisible(false);
				lblPermissionInformationDelete.setVisible(true);
				lblPermissionInformationRead.setVisible(false);
			} else {
				lblPermissionInformationWrite.setVisible(false);
				lblPermissionInformationDelete.setVisible(false);
				lblPermissionInformationRead.setVisible(false);
			}
		}

	}

	public static void getAllPermittedUsersbyNotebookID(int notebookID) {

		notesAdmin.getAllPermittedUsersByNotebookID(notebookID, new AsyncCallback<ArrayList<User>>() {

			@Override
			public void onSuccess(ArrayList<User> result) {
				// TODO Auto-generated method stub
				userList = result;

				clUser.setRowData(result);

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void setUserPermission(String mail) {
		notesAdmin.findUserByMail(mail, new AsyncCallback<User>() {

			@Override
			public void onSuccess(User result) {
				userList.add(result);
				clUser.setRowData(userList);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Dieser Nutzer wurde nicht gefunden");

			}
		});
	}

	public static void setSelectedUserPermissionInTextbox(User user) {
		tbNotebookShareMail.setText(user.getMail());
		tbNotebookShareMail.getElement().setPropertyString("placeholder", "");
		if (user.getPermissionID() == 1) {
			rbRead.setValue(true);
		} else if (user.getPermissionID() == 2) {
			rbWrite.setValue(true);
		} else {
			rbDelete.setValue(true);
		}

	}

}
