package de.hdm.itprojekt.noteit.client;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.AsyncBoxView;

import com.google.appengine.api.search.query.QueryParser.text_return;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class ShowNote extends VerticalPanel {

	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);
	static ArrayList<User> userList = new ArrayList<User>();
	static CellList<User> clUser = new UserCellList().createUserCellList();
	private static Logger rootLogger = Logger.getLogger("");

	static HorizontalPanel hpShowNote = new HorizontalPanel();
	static HorizontalPanel hpHeader = new HorizontalPanel();
	static HorizontalPanel hpAddPermission = new HorizontalPanel();

	static VerticalPanel vpLeft = new VerticalPanel();
	static VerticalPanel vpRight = new VerticalPanel();
	static VerticalPanel vpTitel = new VerticalPanel();
	static VerticalPanel hpNoteSubTitel = new VerticalPanel();
	static VerticalPanel hpNoteText = new VerticalPanel();
	static VerticalPanel vpNotePermission = new VerticalPanel();
	static VerticalPanel hpBackButton = new VerticalPanel();
	static VerticalPanel hpNoteMaturity = new VerticalPanel();

	static Label lblHeaderTitel = new Label();
	static Label lblNoteTitel = new Label("Titel");
	static Label lblNoteSubTitel = new Label("Subtitel");
	static Label lblNoteText = new Label("Deine Notiz");
	static Label lblNoteMaturity = new Label("Fälligkeitsdatum");

	static Label lblNotePermission = new Label();
	static Label lblNoteShare = new Label("Notiz Teilen mit:");
	static Label lblNoteShareRB = new Label("Berechtigung festlegen:");

	static TextBox tbNoteSubTitel = new TextBox();
	static TextBox tbNoteTitel = new TextBox();
	static TextBox tbNoteShareMail = new TextBox();
	static TextBox tbMaturity = new TextBox();

	static Button btnSaveNote = new Button("Speichern");
	static Button btnAddNotePermission = new Button("+");
	static Button btnDeletePermission = new Button("x");

	static RichTextArea content = new RichTextArea();

	static Note currentNote = new Note();

	static RadioButton rbRead = new RadioButton("permission", "lesen");
	static RadioButton rbWrite = new RadioButton("permission", "bearbeiten");
	static RadioButton rbDelete = new RadioButton("permission", "bearbeiten & löschen");

	// Timestamp maturity = new Timestamp();

	// Date maturity = new Date();

	// modificationdate

	@Override
	protected void onLoad() {
		vpLeft.setWidth("300px");
		vpRight.setWidth("300px");
		hpAddPermission.setWidth("300px");
		vpNotePermission.setWidth("300px");

		hpHeader.setStyleName("headerDetailView");
		lblHeaderTitel.setStyleName("lblHeaderTitel");
		hpShowNote.setStyleName("showDetailContent");
		vpRight.setStyleName("vpRightDetailContent");
		hpAddPermission.setStyleName("vpAddPermissionNotebook");

		rbRead.setValue(true);
		hpAddPermission.add(tbNoteShareMail);
		hpAddPermission.add(btnAddNotePermission);
		hpAddPermission.add(btnDeletePermission);
		hpAddPermission.setSpacing(0);

		hpHeader.add(lblHeaderTitel);
		hpShowNote.setWidth("600px");
		/**
		 * Create the Panel, Label and TextBox
		 */

		vpTitel.add(lblNoteTitel);
		vpTitel.add(tbNoteTitel);
		vpTitel.setWidth("300px");

		/**
		 * Create the Panel, Label and TextBox
		 */

		hpNoteSubTitel.add(lblNoteSubTitel);
		hpNoteSubTitel.add(tbNoteSubTitel);
		hpNoteSubTitel.setWidth("300px");

		/**
		 * Create the Panel, Label and TextBox
		 */

		hpNoteText.add(lblNoteText);
		hpNoteText.add(content);
		hpNoteText.setWidth("300px");

		hpNoteMaturity.add(lblNoteMaturity);
		hpNoteMaturity.add(tbMaturity);
		hpNoteMaturity.setWidth("300px");

		hpBackButton.add(btnSaveNote);
		hpBackButton.setWidth("300px");

		vpNotePermission.add(lblNotePermission);
		vpNotePermission.add(clUser);

		vpLeft.add(vpTitel);
		vpLeft.add(hpNoteSubTitel);
		vpLeft.add(hpNoteText);
		vpLeft.add(hpNoteMaturity);
		vpLeft.add(hpBackButton);
		vpRight.add(lblNoteShare);
		vpRight.add(hpAddPermission);
		vpRight.add(lblNoteShareRB);
		vpRight.add(rbRead);
		vpRight.add(rbWrite);
		vpRight.add(rbDelete);
		vpRight.add(vpNotePermission);

		hpShowNote.add(vpLeft);
		hpShowNote.add(vpRight);

		this.add(hpHeader);
		this.add(hpShowNote);

		/**
		 * Erstellen oder bearbeiten von Freigaben RPC
		 */
		btnAddNotePermission.addClickHandler(new ClickHandler() {

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
				if (tbNoteShareMail.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
					notesAdmin.setUserNotePermission(tbNoteShareMail.getText(), permissionID, currentNote.getId(),
							new AsyncCallback<Boolean>() {

								@Override
								public void onSuccess(Boolean result) {
									if (result == true) {
										tbNoteShareMail.setText("");
										tbNoteShareMail.getElement().setPropertyString("placeholder",
												"nutzer@noteit.de");
										rbRead.setValue(true);
										getAllPermittedUsersbyNoteID(currentNote.getId());
									} else {
										Window.alert("Der Nutzer mit der E-Mail `" + tbNoteShareMail.getText()
												+ "` wurde nicht gefunden");
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}
							});
				} else {
					Window.alert("Bitte gebe eine E-Mail-Adresse an!");
					// clUser.setRowData(userList);
				}

			}
		});

	}

	public static void showNote(Note note) {
		currentNote = note;
		rootLogger.log(Level.SEVERE, "objekt: " + note.getTitle());
	
		
		if(note.getModificationDate()== null){
			lblHeaderTitel.setText(note.getTitle() +"    "+ note.getCreationDate().toString());
			
		}else{
			lblHeaderTitel.setText(note.getTitle() +"    "+ note.getModificationDate().toString());
		}
		tbNoteTitel.setText(note.getTitle());
		tbNoteSubTitel.setText(note.getSubTitle());
		content.setText(note.getText());
		content.setText(note.getText());
		tbMaturity.setText(note.getMaturityDate().toString());

		btnSaveNote.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				notesAdmin.createNote(tbNoteTitel.getText(), tbNoteSubTitel.getText(), content.getText(), null,
						Homepage.getCurrentUser(), null, currentNote.getNotebookId(), new AsyncCallback<Note>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Note result) {
								// TODO Auto-generated method stub

							}
						});

			}
		});

	}

	public static void setNote(Note newNote) {
		currentNote = newNote;
	}

	public static void getAllPermittedUsersbyNoteID(int noteID) {

		notesAdmin.getAllPermittedUsersByNoteID(noteID, new AsyncCallback<ArrayList<User>>() {

			@Override
			public void onSuccess(ArrayList<User> result) {
				// TODO Auto-generated method stub
				userList = result;
				clUser.setRowData(result);
				if(result.size() <1){
					lblNotePermission.setText("Diese Notiz wurde niemandem freigegeben");
				}else if(result.size() == 1){
					lblNotePermission.setText("Freigegeben an folgenden Nutzer:");
				}else{
					lblNotePermission.setText("Freigegeben an folgende "+result.size()+ " Nutzer:");
				}

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	public static void setSelectedUserPermissionInTextbox(User user){
		tbNoteShareMail.setText(user.getMail());
		tbNoteShareMail.getElement().setPropertyString("placeholder", "");
		if(user.getPermissionID() == 1){
			rbRead.setValue(true);
		}else if(user.getPermissionID() == 2){
			rbWrite.setValue(true);
		}else{
			rbDelete.setValue(true);
		}
		
	}

}
