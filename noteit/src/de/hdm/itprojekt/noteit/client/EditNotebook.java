package de.hdm.itprojekt.noteit.client;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tools.ant.taskdefs.Delete;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
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

	static VerticalPanel vpLeft = new VerticalPanel();
	static VerticalPanel vpRight = new VerticalPanel();
	static VerticalPanel vpTitle = new VerticalPanel();
	static VerticalPanel vpNotebookShare = new VerticalPanel();
	static VerticalPanel vpNotebookPermission = new VerticalPanel();
	static VerticalPanel vpBackButton = new VerticalPanel();

	static Label lblHeaderTitel = new Label();
	static Label lblNotebookTitel = new Label("Titel");
	static Label lblNotebookPermission = new Label("Freigegeben an:");
	static Label lblNotebookShare = new Label("Notizbuch Teilen mit:");

	static TextBox tbNotebookTitel = new TextBox();
	static TextBox tbNotebookShareMail = new TextBox();

	static Button btnNotebookSave = new Button("Speichern");
	static Button btnAddPermission = new Button("+");
	static Button btnDeletePermission = new Button("x");
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
		
		hpHeader.add(lblHeaderTitel);
		
	    rbRead.setValue(true);
	    hpAddPermission.add(rbRead);
	    hpAddPermission.add(rbWrite);
	    hpAddPermission.add(rbDelete);
		/**
		 * Create the Panel, Label and TextBox
		 */

		vpTitle.add(lblNotebookTitel);
		vpTitle.add(tbNotebookTitel);
		
		
		hpAddPermission.add(tbNotebookShareMail);
		hpAddPermission.add(btnAddPermission);
		hpAddPermission.add(btnDeletePermission);
		hpAddPermission.setSpacing(0);
		
		vpNotebookPermission.add(lblNotebookPermission);
		vpNotebookPermission.add(clUser);
		
		vpLeft.add(lblNotebookTitel);
		vpLeft.add(tbNotebookTitel);
		vpLeft.add(lblNotebookShare);
		vpLeft.add(hpAddPermission);
		vpLeft.add(rbRead);
		vpLeft.add(rbWrite);
		vpLeft.add(rbDelete);
		vpLeft.add(hpButtons);
		vpRight.add(vpNotebookPermission);

		/**
		 * Create the Panel, Label and TextBox
		 */

//		hpNoteSubTitel.add(lblNotebookSubTitel);
//		hpNoteSubTitel.add(tbNotebookSubTitel);

		/**
		 * Create the Panel, Label and TextBox
		 */


		hpButtons.add(btnNotebookSave);

		hpEditNotebook.add(vpLeft);
		hpEditNotebook.add(vpRight);
		//hpEditNotebook.add(vpNotebookPermission);
		//hpEditNotebook.add(hpButtons);

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
					notesAdmin.setUserNotebookPermission(tbNotebookShareMail.getText(), permissionID,currentNotebook.getId(),
							new AsyncCallback<Boolean>() {

								@Override
								public void onSuccess(Boolean result) {
									if(result == true){
									tbNotebookShareMail.setText("");
									tbNotebookShareMail.getElement().setPropertyString("placeholder", "nutzer@noteit.de");
									rbRead.setValue(true);
									getAllPermittedUsersbyNotebookID(currentNotebook.getId());
									}else{
										Window.alert("Der Nutzer mit der E-Mail `"+ tbNotebookShareMail.getText()+"` wurde nicht gefunden" );
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}
							});
				}else{
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
					notesAdmin.deleteUserNotebookPermission(tbNotebookShareMail.getText(), Homepage.getCurrentUser().getPermissionID(), currentNotebook.getId(), new AsyncCallback<Void>() {
						
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
				}else{
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
					notesAdmin.createNotebook(tbNotebookTitel.getText(), Homepage.getCurrentUser(),
							new AsyncCallback<Notebook>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onSuccess(Notebook result) {
									// TODO get all notebooks by user ID
									lblHeaderTitel.setText(tbNotebookTitel.getText());
									rootLogger.log(Level.SEVERE, "NB Speicher Button");
									NoteitCellBrowser.getNotebookList(result);
									
								}
							});
				}else{
					Window.alert("Bitte vergebe einen Titel für dien Notizbuch");
				}
			}
		});
		
		tbNotebookShareMail.getElement().setPropertyString("placeholder", "nutzer@noteit.de");
		btnAddPermission.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				setUserPermission(tbNotebookShareMail.getText());
				
			}
		});
	}

	public static void setNotebook(Notebook notebook) {
		currentNotebook = notebook;
		lblHeaderTitel.setText(notebook.getTitle());
		tbNotebookTitel.setText(notebook.getTitle());
	//	tbMaturity.setText(notebook.get());

	}
	
	public static void getAllPermittedUsersbyNotebookID(int notebookID){
		
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
	
	public void setUserPermission(String mail){
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
	
	public static void setSelectedUserPermissionInTextbox(User user){
		tbNotebookShareMail.setText(user.getMail());
		tbNotebookShareMail.getElement().setPropertyString("placeholder", "");
		if(user.getPermissionID() == 1){
			rbRead.setValue(true);
		}else if(user.getPermissionID() == 2){
			rbWrite.setValue(true);
		}else{
			rbDelete.setValue(true);
		}
		
	}
	
}
